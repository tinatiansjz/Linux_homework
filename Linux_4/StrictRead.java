/*
 * Copyright 2007-2015 by The Regents of the Wuhan University of China.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

/**
 * @author Administrator
 *
 */
public class StrictRead {
    private static int opst;
    private static int gran;
    private static long size;
    private static int rang;
    private static final String PATH = "tmp_sf.raw";
    private static BitSet bs;
    private static byte[] cache;
    private static long[][] offsets;
    private static boolean totalOrder;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        opst = Integer.parseInt(args[0]);
        gran = Integer.parseInt(args[1]);
        size = Long.parseLong(args[2]);
        rang = Integer.parseInt(args[3]);
        totalOrder = Boolean.parseBoolean(args[4]);
        System.out.println(
                "type: " + opst + " gran: " + gran + " size: " + size + " rang: " + rang + " order: " + totalOrder);
        switch (opst) {
            case 0: {
                // Initialize a file.
                long off = 0;
                cache = new byte[gran];
                FileOutputStream os = new FileOutputStream(new File(PATH));
                int count = 0;
                while (off < size) {
                    Arrays.fill(cache, (byte) (count % 0x7f));
                    os.write(cache);
                    off += cache.length;
                    count++;
                }
                System.out.println("Write count: " + count);
                os.close();
                break;
            }
            case 1: {
                // Sequential read;
                FileInputStream is = new FileInputStream(new File(PATH));
                long len = new File(PATH).length();
                long off = 0;
                long round = 0;
                int read = 0;
                int count = 0;
                cache = new byte[gran];
                long begin = System.currentTimeMillis();
                while (off < size) {
                    if (round + gran > len) {
                        System.out.println("\t " + is.markSupported() + "\t" + cache[0] + " now at: " + off);
                        if (is.markSupported()) {
                            is.reset();
                        } else {
                            is.close();
                            is = new FileInputStream(new File(PATH));
                        }
                        round = 0;
                    }
                    read = is.read(cache);
                    off += read;
                    round += read;
                    count++;
                }
                is.close();
                System.out.println("\n" + "Read count: " + count + " Time: " + (System.currentTimeMillis() - begin));
                break;
            }
            case 2: {
                // Random sequential read;
                RandomAccessFile raf = new RandomAccessFile(PATH, "r");
                long len = new File(PATH).length();
                long off = 0;
                long round = 0;
                int read = 0;
                int count = 0;
                cache = new byte[gran];
                long begin = System.currentTimeMillis();
                while (off < size) {
                    if (round + gran > len) {
                        System.out.println("\t " + raf.getFilePointer() + "\t" + cache[0] + " now at: " + off);
                        round = 0;
                    }
                    raf.seek(round);
                    read = raf.read(cache);
                    off += read;
                    round += read;
                    count++;
                }
                raf.close();
                System.out.println("\n" + "Read count: " + count + " Time: " + (System.currentTimeMillis() - begin));
                break;
            }
            case 3: {
                // Sequential read;
                FileInputStream is = new FileInputStream(new File(PATH));
                long len = new File(PATH).length();
                long off = 0;
                long round = 0;
                int read = 0;
                int count = 0;
                cache = new byte[gran];
                FileChannel chan = is.getChannel();
                ByteBuffer buf = ByteBuffer.wrap(cache, 0, cache.length);
                long begin = System.currentTimeMillis();
                while (off < size) {
                    buf.rewind();
                    if (round + gran > len) {
                        System.out.println("\t " + is.markSupported() + "\t" + cache[0] + " now at: " + off);
                        if (is.markSupported()) {
                            is.reset();
                        } else {
                            is.close();
                            is = new FileInputStream(new File(PATH));
                        }
                        round = 0;
                    }
                    read = chan.read(buf, off);
                    off += read;
                    round += read;
                    count++;
                }
                chan.close();
                is.close();
                System.out.println("\n" + "Read count: " + count + " Time: " + (System.currentTimeMillis() - begin));
                break;
            }
            case 4: {
                // Sequentially fixed-size skipping read;
                // There exists bug to skip onto the non-touched blocks.
                FileInputStream is = new FileInputStream(new File(PATH));
                bs = new BitSet((int) (size / gran));
                long off = 0;
                long step = gran * rang;
                int count = 0;
                cache = new byte[gran];
                offsets = new long[(int) (rang)][];
                for (int i = 0; i < rang; i++) {
                    offsets[i] = new long[(int) (size / step)];
                    if (!totalOrder) {
                        for (int j = 0; j < size / step; j++) {
                            offsets[i][j] = (rang - 1 - i) * gran + j * step;
                        }
                    } else {
                        for (int j = 0; j < size / step; j++) {
                            offsets[i][j] = i * gran + j * step;
                        }
                    }
                }
                long begin = System.currentTimeMillis();
                for (int i = 0; i < rang; i++) {
                    is.close();
                    is = new FileInputStream(new File(PATH));
                    long oldpos = 0;
                    for (int j = 0; j < size / step; j++) {
                        off = offsets[i][j] - oldpos - gran;
                        if (off > 0) {
                            is.skip(off);
                        }
                        is.read(cache);
                        oldpos += off;
                        count++;
                    }
                    if (i % (rang / 10 + 1) == 0) {
                       // System.out.println("Read count: " + count + " Time: " + (System.currentTimeMillis() - begin) + " round: " + i);
                    }
                }
                is.close();
                System.out.println(
                        "Read count: " + count + " Time: " + (System.currentTimeMillis() - begin) + " round: " + rang);
                break;
            }
            case 5: {
                RandomAccessFile raf = new RandomAccessFile(PATH, "r");
                bs = new BitSet((int) (size / gran));
                long step = gran * rang;
                int count = 0;
                cache = new byte[gran];
                offsets = new long[(int) (rang)][];
                for (int i = 0; i < rang; i++) {
                    offsets[i] = new long[(int) (size / step)];
                    if (!totalOrder) {
                        for (int j = 0; j < size / step; j++) {
                            offsets[i][j] = (rang - 1 - i) * gran + j * step;
                        }
                    } else {
                        for (int j = 0; j < size / step; j++) {
                            offsets[i][j] = i * gran + j * step;
                        }
                    }
                }
                long begin = System.currentTimeMillis();
                for (int i = 0; i < rang; i++) {
                    for (int j = 0; j < size / step; j++) {
                        raf.seek(offsets[i][j]);
                        raf.read(cache);
                        count++;
                    }
                    if (i % (rang / 10 + 1) == 0) {
                     //System.out.println("Read count: " + count + " Time: " + (System.currentTimeMillis() - begin) + " round: " + i);
                    }
                }
                raf.close();
                System.out.println(
                        "Read count: " + count + " Time: " + (System.currentTimeMillis() - begin) + " round: " + rang);
                break;
            }
            case 6: {
                FileInputStream is = new FileInputStream(new File(PATH));
                bs = new BitSet((int) (size / gran));
                long step = gran * rang;
                int count = 0;
                cache = new byte[gran];
                offsets = new long[(int) (rang)][];
                ByteBuffer buf = ByteBuffer.wrap(cache, 0, cache.length);
                for (int i = 0; i < rang; i++) {
                    offsets[i] = new long[(int) (size / step)];
                    if (!totalOrder) {
                        for (int j = 0; j < size / step; j++) {
                            offsets[i][j] = (rang - 1 - i) * gran + j * step;
                        }
                    } else {
                        for (int j = 0; j < size / step; j++) {
                            offsets[i][j] = i * gran + j * step;
                        }
                    }
                }
                FileChannel chan = is.getChannel();
                long begin = System.currentTimeMillis();
                for (int i = 0; i < rang; i++) {
                    for (int j = 0; j < size / step; j++) {
                        buf.rewind();
                        chan.read(buf, offsets[i][j]);
                        count++;
                    }
                    if (i % (rang / 10 + 1) == 0) {
                        System.out.println("Read count: " + count + " Time: " + (System.currentTimeMillis() - begin)
                                + " round: " + i);
                    }
                }
                chan.close();
                is.close();
                System.out.println(
                        "Read count: " + count + " Time: " + (System.currentTimeMillis() - begin) + " round: " + rang);
                break;
            }
            case 7: {
                // Sequentially random skipping read;
                // There exists bug to skip onto the non-touched blocks.
                FileInputStream is = new FileInputStream(new File(PATH));
                Random rnd = new Random();
                long len = new File(PATH).length();
                bs = new BitSet((int) (size / gran));
                long off = 0;
                long round = 0;
                long step = 0;
                int count = 0;
                cache = new byte[gran];
                offsets = new long[(int) (1)][];
                offsets[0] = new long[(int) (size / gran)];
                int totalClearing = 0;
                while (off < size) {
                    step = Math.abs((rnd.nextInt() % (rang * 2)) * gran) + gran;
                    int desiredBlockId = (int) ((round + step) / gran);
                    round += step;
                    if (round + gran > size) {
                        if (is.markSupported()) {
                            is.reset();
                        } else {
                            is.close();
                            is = new FileInputStream(new File(PATH));
                        }
                        round = 0;
                        desiredBlockId = 0;
                        totalClearing++;
                    }
                    // Look for an unused block;
                    while (bs.get(desiredBlockId)) {
                        if (round + gran > len) {
                            System.out.println("\t " + is.markSupported() + "\t" + cache[0] + " now at: "
                                    + (round + step) + " for: " + off);
                            System.exit(-1);
                        }
                        round += gran;
                        desiredBlockId = (int) ((round) / gran);
                    }
                    offsets[0][count++] = round;
                    bs.set(desiredBlockId);
                    // Suppose we read here, and the round will be forwarded by 1 block.
                    // Enable this accumulation using a minimal step 1 in line 280, denoting at least we will skip one block.
                    round += gran;
                    off += gran;
                }
                long begin = System.currentTimeMillis();
                long oldpos = 0;
                step = 0;
                int cleared = 0;
                count = 0;
                for (int i = 0; i < offsets[0].length; i++) {
                    if (offsets[0][i] < oldpos) {
                        if (cleared % (totalClearing / 10 + 1) == 0) {
                            System.out.println("Read count: " + count + " Time: " + (System.currentTimeMillis() - begin)
                                    + " round: " + cleared);
                        }
                        is.close();
                        is = new FileInputStream(new File(PATH));
                        oldpos = 0;
                        cleared++;
                    }
                    step = offsets[0][i] - oldpos - gran;
                    if (step > 0) {
                        is.skip(step);
                    }
                    is.read(cache);
                    oldpos = offsets[0][i];
                    count++;
                }
                is.close();
                System.out.println("Read count: " + count + " real count: " + bs.cardinality() + " Time: "
                        + (System.currentTimeMillis() - begin) + " round: " + cleared);
                break;
            }
            case 8: {
                // Randomly random skipping read;
                RandomAccessFile raf = new RandomAccessFile(PATH, "r");
                Random rnd = new Random();
                long len = new File(PATH).length();
                bs = new BitSet((int) (size / gran));
                long off = 0;
                long round = 0;
                long step = 0;
                int count = 0;
                int totalClearing = 0;
                cache = new byte[gran];
                offsets = new long[(int) (1)][];
                offsets[0] = new long[(int) (size / gran)];
                while (off < size) {
                    step = Math.abs((rnd.nextInt() % (rang * 2)) * gran) + gran;
                    int desiredBlockId = (int) ((round + step) / gran);
                    round += step;
                    if (round + gran > size) {
                        round = 0;
                        desiredBlockId = 0;
                        totalClearing++;
                    }
                    // Look for an unused block;
                    while (bs.get(desiredBlockId)) {
                        if (round + gran > len) {
                            System.out.println("\t " + raf.getFilePointer() + "\t" + cache[0] + " now at: "
                                    + (round + step) + " for: " + off);
                            System.exit(-1);
                        }
                        round += gran;
                        desiredBlockId = (int) ((round) / gran);
                    }
                    offsets[0][count++] = round;
                    bs.set(desiredBlockId);
                    // Suppose we read here, and the round will be forwarded by 1 block.
                    // Enable this accumulation using a minimal step 1 in line 280, denoting at least we will skip one block.
                    round += gran;
                    off += gran;
                }
                long oldpos = 0;
                int cleared = 0;
                count = 0;
                long begin = System.currentTimeMillis();
                for (int i = 0; i < offsets[0].length; i++) {
                    if (offsets[0][i] < oldpos) {
                        if (cleared++ % (totalClearing / 10 + 1) == 0) {
                            System.out.println("Read count: " + count + " Time: " + (System.currentTimeMillis() - begin)
                                    + " round: " + cleared);
                        }
                    }
                    raf.seek(offsets[0][i]);
                    raf.read(cache);
                    oldpos = offsets[0][i];
                    count++;
                }
                raf.close();
                System.out.println("Read count: " + count + " real count: " + bs.cardinality() + " Time: "
                        + (System.currentTimeMillis() - begin) + " round: " + cleared);
                break;
            }
            case 9: {
                // Sequential read;
                FileInputStream is = new FileInputStream(new File(PATH));
                Random rnd = new Random();
                long len = new File(PATH).length();
                bs = new BitSet((int) (size / gran));
                long off = 0;
                long round = 0;
                int count = 0;
                long step = 0;
                cache = new byte[gran];
                int totalClearing = 0;
                FileChannel chan = is.getChannel();
                ByteBuffer buf = ByteBuffer.wrap(cache, 0, cache.length);
                offsets = new long[(int) (1)][];
                offsets[0] = new long[(int) (size / gran)];
                while (off < size) {
                    step = Math.abs((rnd.nextInt() % (rang * 2)) * gran) + gran;
                    int desiredBlockId = (int) ((round + step) / gran);
                    round += step;
                    if (round + gran > size) {
                        round = 0;
                        desiredBlockId = 0;
                        totalClearing++;
                    }
                    // Look for an unused block;
                    while (bs.get(desiredBlockId)) {
                        if (round + gran > len) {
                            System.out.println("\t " + chan.position() + "\t" + cache[0] + " now at: " + (round + step)
                                    + " for: " + off);
                            System.exit(-1);
                        }
                        round += gran;
                        desiredBlockId = (int) ((round) / gran);
                    }
                    offsets[0][count++] = round;
                    bs.set(desiredBlockId);
                    // Suppose we read here, and the round will be forwarded by 1 block.
                    // Enable this accumulation using a minimal step 1 in line 280, denoting at least we will skip one block.
                    round += gran;
                    off += gran;
                }
                int cleared = 0;
                long oldpos = 0;
                count = 0;
                long begin = System.currentTimeMillis();
                for (int i = 0; i < offsets[0].length; i++) {
                    if (offsets[0][i] < oldpos) {
                        if (cleared++ % (totalClearing / 10 + 1) == 0) {
                            System.out.println("Read count: " + count + " Time: " + (System.currentTimeMillis() - begin)
                                    + " round: " + cleared);
                        }
                    }
                    buf.rewind();
                    chan.read(buf, offsets[0][i]);
                    oldpos = offsets[0][i];
                    count++;
                }
                chan.close();
                is.close();
                System.out.println("Read count: " + count + " real count: " + bs.cardinality() + " Time: "
                        + (System.currentTimeMillis() - begin) + " round: " + cleared);
                break;
            }
            default:
                break;
        }
    }
}
