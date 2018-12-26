#include <stdio.h>
#include <malloc.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void Merge(int*, int, int, int);
void MergePass(int*, int, int);
void MergeSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_MergeSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    MergeSort(a, n);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void Merge (int R[], int low, int mid, int high){
    int *R1;
    int i = low, j = mid+1, k = 0;
    R1 = (int*)malloc((high-low+1) * sizeof(int));
    while (i <= mid && j <= high){
        if (R[i] <= R[j]){
            R1[k] = R[i];
            i++; k++;
        }else{
            R1[k] = R[j];
            j++; k++;
        }
    }
    while (i <= mid){
        R1[k] = R[i];
        i++; k++;
    }
    while (j <= high){
        R1[k] = R[j];
        k++; j++;
    }
    for (k = 0, i = low; i <= high; k++, i++){
        R[i] = R1[k];
    }
    free(R1);
}
void MergePass(int R[], int length, int n){//对整个表进行一趟归并
    int i;
    for (i = 0; i+2*length-1 < n; i = i+2*length)//归并length长的两相邻子表
        Merge(R, i, i+length-1, i+2*length-1);
    if (i+length-1 < n)//余下两个子表，后者长度小于length
        Merge(R, i, i+length-1, n-1);//归并这两个表
}
void MergeSort (int R[], int n){//自底向上的二路归并算法
    int length;
    for (length = 1; length < n; length = 2*length){
        MergePass(R, length, n);
    }
}
