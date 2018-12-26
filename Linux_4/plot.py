#-*- coding: utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt

#y4 = [0.13, 13.25, 255.82, 1044.61, 5454.00]
#y5 = [0.00, 0.08, 0.46, 0.94, 1.54]
#y6 = [0.00, 0.06, 0.39, 0.77, 1.17]
#y7 = [0.210, 20.77, 467.24, 1467.08, 10031.41]
#y8 = [0.10, 12.36, 284.74, 637.93, 4312.36]
plt.figure(figsize=(16, 8))
#plt.xlim(0, 70)
plt.subplot(211)
plt.title("Seq & Ran Analysis")
x1 = [8, 16, 32, 64]
y11 = [52.80, 57.62, 56.11, 57.60]
y12 = [56.76, 56.41, 46.38, 49.16]
y13 = [42.17, 41.30, 56.26, 57.68]
plt.ylim(0, 60)
plt.xlabel("Block size")
plt.ylabel("I/O Efficiency (MB/s)")
plt.plot(x1, y11, label="$Sequential Read$", color="red", linewidth=2)
plt.plot(x1, y12, label="$Random Sequential Read$", color="orange", linewidth=2)
plt.plot(x1, y13, label="$Sequential Read (FileChannel)$", color="yellow", linewidth=2)
group_labels = ['8K', '16K', '32K', '64K']
plt.xticks(x1, group_labels, rotation=0)
plt.legend()    #显示图示

plt.subplot(223)
plt.title("Sequentially Skipping Read")
x2 = [1, 2, 3, 4, 5, 6, 7]
y21 = [57.19, 77.84, 18.12, 7.44, 3.79, 2.05, 1.82]
y22 = [56.41, 59.32, 16.70, 7.60, 4.04, 2.87, 3.67]
y23 = [53.74, 57.48, 17.95, 7.79, 4.34, 7.46, 3.38]
y24 = [54.55, 57.50, 18.69, 8.36, 10.21, 6.66, 8.83]
plt.ylim(0, 80)
plt.xlabel("Skipping blocks")
plt.ylabel("I/O Efficiency (MB/s)")
group_labels = ['1', '2', '4', '8', '16', '32', '64']
plt.xticks(x2, group_labels, rotation=0)
plt.plot(x2, y21, label="$block=8K$", color="green", linewidth=2)
plt.plot(x2, y22, label="$block=16K$", color="skyblue", linewidth=2)
plt.plot(x2, y23, label="$block=32K$", color="blue", linewidth=2)
plt.plot(x2, y24, label="$block=64K$", color="purple", linewidth=2)
plt.legend()    #显示图示

plt.subplot(224)
plt.title("Randomly Skipping Read")
x3 = [1, 2, 3, 4, 5, 6, 7]
y31 = [58.31, 32.43 ,17.64 ,10.11 ,4.32, 1.90 ,1.19]
y32 = [57.90, 32.47, 23.36, 10.34, 4.53, 2.40, 2.97]
y33 = [62.52, 30.87, 18.88, 9.29, 4.72, 6.63, 3.90]
y34 = [54.29, 31.16, 15.69, 10.23, 11.32, 5.76, 8.27]
plt.ylim(0, 80)
plt.xlabel("Skipping blocks")
plt.ylabel("I/O Efficiency (MB/s)")
group_labels = ['1', '2', '4', '8', '16', '32', '64']
plt.xticks(x3, group_labels, rotation=0)
plt.plot(x3, y31, label="$block=8K$", color="green", linewidth=2)
plt.plot(x3, y32, label="$block=16K$", color="skyblue", linewidth=2)
plt.plot(x3, y33, label="$block=32K$", color="blue", linewidth=2)
plt.plot(x3, y34, label="$block=64K$", color="purple", linewidth=2)
plt.legend()    #显示图示



plt.savefig("plot.jpg")
plt.show()
