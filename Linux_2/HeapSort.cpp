#include <stdio.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void sift(int*, int, int);
void HeapSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_HeapSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    HeapSort(a, n-1);
	for (int i = 1; i < n+1; i++)
        printf("%d\n", a[i]);
    return 0;
}
void sift (int R[], int low, int high){
    int i = low, j = 2*i;//R[j]是R[i]的左孩子
    int tmp = R[i];
    while (j <= high){
        if (j < high && R[j] < R[j+1]){//若孩子较大，把j指向右孩子
            j++;
        }
        if (tmp < R[j]){
            R[i] = R[j];//将R[j]调整到双亲节点位置上
            i = j;//修改i和j值，以便继续往下筛选
            j = 2*i;
        }
        else break;//筛选结束
    }
    R[i] = tmp;//被筛选节点的值放入最终位置
}
void HeapSort (int R[], int n){//堆排序
    int i, tmp;
    for (i = n/2; i >= 1; i--){//循环建立初始堆
        sift(R, i, n);
    }
    for (i = n; i >= 2; i--){//进行n-1趟堆排序，每一趟堆排序的元素个数减1
        tmp = R[1];          //将最后一个元素同当前区间内R[1]对换
        R[1] = R[i];
        R[i] = tmp;
        sift(R, 1, i-1);     //筛选R[1]节点，得到i-1个节点堆
    }
}
