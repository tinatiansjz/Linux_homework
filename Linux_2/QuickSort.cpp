#include <stdio.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void QuickSort(int*, int, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_QuickSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    QuickSort(a, 0, n-1);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void QuickSort (int R[], int s, int t){//快速排序
    int i = s, j = t;
    int tmp;
    if (s < t){
        tmp = R[s];
        while (i != j){
            while(j > i && R[j] >= tmp)
                j--;
            R[i] = R[j];
            while(i < j && R[i] <= tmp)
                i++;
            R[j] = R[i];
        }
        R[i] = tmp;
        QuickSort(R, s, i-1);
        QuickSort(R, i+1, t);
    }
}
