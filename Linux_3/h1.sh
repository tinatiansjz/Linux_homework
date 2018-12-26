#!/bin/bash
#program:
# calculate the sum of square from 1 to 100.
#History:
#2018/04/06     Tina    First release
PATH=/home/tina/anaconda2/bin:/home/tina/bin:/home/tina/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin
export PATH

echo -e "Calculate the sum of square from 1 to 100."
s=0     #这是累加的数值变量
i=0     #这是累加的值，亦及1,2,3...
total=$((i*i))
while [ "$i" != 100 ]
do
    i=$(($i+1))
    s=$(($s+$i*$i))
done
echo "The result is $s"
