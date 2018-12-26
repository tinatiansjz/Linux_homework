#include <stdio.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void SelectSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_SelectSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    SelectSort(a, n);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void SelectSort (int R[], int n){//直接选择排序
    int tmp;
    for (int i = 0; i < n-1; i++){
        int k = i;
        for (int j = i+1; j < n; j++){
            if(R[j] < R[k])
                k = j;
        }
        if (k != i){
            tmp = R[i];
            R[i] = R[k];
            R[k] = tmp;
        }
    }
}
