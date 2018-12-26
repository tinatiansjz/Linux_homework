#include <stdio.h>
#include <malloc.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void BubbleSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_BubbleSort.txt", "w", stdout);
#endif
//    int* a;
//    a = (int*)malloc(sizeof(int)*maxn);
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    BubbleSort(a, n);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void BubbleSort (int R[], int n){//冒泡排序
    bool exchange;
    int tmp;
    for (int i = 0; i < n-1; i++){
        exchange = false;
        for (int j = n-1; j > i; j--){
             if (R[j] < R[j-1]){
                tmp = R[j];
                R[j] = R[j-1];
                R[j-1] = tmp;
                exchange = true;
            }
        }
        if(!exchange)
            return;
    }
}
