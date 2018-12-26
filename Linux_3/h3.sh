#!/bin/bash
#program:
#   Use ping command to check the network's PC state.
#History:
#2018/04/06     Tina    First release
PATH=/home/tina/anaconda2/bin:/home/tina/bin:/home/tina/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin
export PATH

touch connected.txt
touch disconnected.txt
network="198.168.1"             #先定义一个域的前半部分
for sitenu in $(seq 1 100)      #seq是sequence的缩写
do
    #下面的语句取得ping的回传值是正确的还是失败的
    ping -c 1 -w 1 ${network}.${sitenu} &> /dev/null && result=0 || result=1
    #开始显示结是正确的启动(UP) 还是错误的没有连通(DOWN)
    if [ "$result" == 0 ]; then
        echo "${network}.${sitenu}" >> connected.txt    #将连通的IP放入connected.txt中
    else
        echo "${network}.${sitenu}" >> disconnected.txt #将未连通的IP放入disconnected.txt中
    fi
done

