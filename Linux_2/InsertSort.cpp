#include <stdio.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void InsertSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_InsertSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    InsertSort(a, n);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void InsertSort (int R[], int n){//插入排序
	int i, j, tmp;
	for (i = 1; i < n; i++){
		tmp = R[i];
		j = i-1;
		while(j >= 0 && tmp < R[j]){
            R[j+1] = R[j];
            j--;
        }
        R[j+1] = tmp;
	}
}
