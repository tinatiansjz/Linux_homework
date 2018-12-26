#!/bin/bash
#program:
#   handling the sorting-alogrithm programs
#History:
#2018/04/04     Tina    First release
PATH=/home/tina/anaconda2/bin:/home/tina/bin:/home/tina/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin
export PATH

cd ~/sorting_algorithm
g++ genda.cpp -o genda
g++ BinaryInsertSort.cpp -o BinaryInsertSort
g++ BubbleSort.cpp -o BubbleSort
g++ HeapSort.cpp -o HeapSort  
g++ InsertSort.cpp -o InsertSort
g++ MergeSort.cpp -o MergeSort
g++ QuickSort.cpp -o QuickSort
g++ SelectSort.cpp -o SelectSort 
g++ ShellSort.cpp -o ShellSort 
rm -f real_time.txt &>/dev/null
touch real_time.txt
for data_size in 10000 100000 500000 1000000 5000000 10000000    #data size
do
    ./genda $data_size > data.txt
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./BinaryInsertSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./BubbleSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./HeapSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./InsertSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./MergeSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./QuickSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./SelectSort
    done
    for j in $(seq 1 3)
    do
        /usr/bin/time -a -o real_time.txt -f "%e" ./ShellSort
    done
done
