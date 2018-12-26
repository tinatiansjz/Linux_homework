#!/bin/bash
#program:
#   monitor the cpu utilization. If a process goes beyond 50%, then kill it.
#History:
#2018/04/07     Tina    First release
PATH=/home/tina/anaconda2/bin:/home/tina/bin:/home/tina/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin
export PATH
#$3为cpu%，$2为PID,此命令筛选出CPU占用率>50%的进程，并将PID存入$totalCPU
totalCPU=$(ps -aux | awk 'NR>1{if($3 > 50.0) print $2}')
for cpu in $totalCPU
do
    kill -9 $cpu    #杀死进程
done
