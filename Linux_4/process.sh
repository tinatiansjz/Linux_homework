#!/bin/bash
#program:
#   read a file with different reading mode
#History:
#2018/06/16     Tina    First release
PATH=/home/tina/anaconda2/bin:/home/tina/bin:/home/tina/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin
export PATH

cd ~/Linux_homework/Linux_4
javac StrictRead.java
#create a file: 8G, block size: 64K
java StrictRead 0 65535 8589934592 1 FALSE
#Experiment_1: compare among Sequential read(1), Random Sequential read(2), Sequential read(FileChannel)(3)
for i in $(seq 1 3)
do
    for j in 8192 16384 32768 65536 #block size
    do
        java StrictRead $i $j 8589934592 0 FALSE
    done
done

#Experiment_2: compare among Sequentially fixed-size skipping read(4) and Randomly fixed-size skipping read. 
#independent valuables
#i: opst (the mode)
#k: gran (gradient, the block size)
#j: rang (the skipping fixed size)
for i in $(seq 4 5)
do
    for j in 0 1 2 4 8 16 32 64
    do
        for k in 8192 16384 32768 65536
        do
            java StrictRead $i $k 8589934592 $j FALSE
        done
    done
done
