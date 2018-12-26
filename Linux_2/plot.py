#-*- coding: utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt

#x = np.linspace(0, 5000000, 500)  
x = [10000, 100000, 500000, 1000000, 5000000]
y1 = [0.10, 9.76, 244.41, 920.48, 15969.26]
y2 = [0.47, 52.48, 1275.33, 4514.50, 25278.47]
y3 = [0.00, 0.08, 0.30, 0.96, 1.59]
y4 = [0.13, 13.25, 255.82, 1044.61, 5454.00]
y5 = [0.00, 0.08, 0.46, 0.94, 1.54]
y6 = [0.00, 0.06, 0.39, 0.77, 1.17]
y7 = [0.210, 20.77, 467.24, 1467.08, 10031.41]
y8 = [0.10, 12.36, 284.74, 637.93, 4312.36]
plt.figure(figsize=(8, 4))
plt.ylim(0, 26000)
plt.xlim(0, 5000000)
plt.xlabel("data size")
plt.ylabel("execution time(s)")
plt.title("Sorting Algorithm Analysis")
plt.plot(x, y1, label="$BinaryInsertSort$", color="red", linewidth=2)
plt.plot(x, y2, label="$BubbleSort$", color="orange", linewidth=2)
plt.plot(x, y3, label="$HeapSort$", color="yellow", linewidth=2)
plt.plot(x, y4, label="$InsertSort$", color="green", linewidth=2)
plt.plot(x, y5, label="$MergeSort$", color="skyblue", linewidth=2)
plt.plot(x, y6, label="$QuickSort$", color="blue", linewidth=2)
plt.plot(x, y7, label="$SelectSort$", color="purple", linewidth=2)
plt.plot(x, y8, label="$ShellSort$", color="black", linewidth=2)
plt.legend()    #显示图示
plt.savefig("plot.jpg")
plt.show()
