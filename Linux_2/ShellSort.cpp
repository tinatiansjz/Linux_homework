#include <stdio.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void ShellSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_ShellSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    ShellSort(a, n);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void ShellSort (int R[], int n){//希尔排序
    int gap, tmp;
    gap = n/2;
    while(gap >= 1){
        for (int i = gap; i < n; i += gap){
            tmp = R[i];
            int j = i-gap;
            while(j >= 0 && tmp < R[j]){
                R[j+gap] = R[j];
                j = j-gap;
            }
            R[j+gap] = tmp;
        }
        gap = gap/2;
    }
}
