#include <stdio.h>
#define LOCAL
#define maxn 100000001
int a[maxn];
void BinaryInsertSort(int*, int);
int main(){
#ifdef LOCAL
    freopen("data.txt", "r", stdin);
    freopen("out_BinaryInsertSort.txt", "w", stdout);
#endif
    int n = 0;
    while(scanf("%d", &a[n]) == 1){
        n++;
    }
    n++;//数据总个数
    BinaryInsertSort(a, n);
	for (int i = 0; i < n; i++)
        printf("%d\n", a[i]);
    return 0;
}
void BinaryInsertSort (int R[], int n){//插入排序
    int low, high, mid;
    int tmp;
    for (int i = 1; i < n; i++){
        tmp = R[i];
        low = 0; high = i-1;
        while(low <= high){
            mid = (low+high)/2;
            if(tmp < R[mid]){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        for(int j = i-1; j >= high+1; j--){
            R[j+1] = R[j];
        }
        R[high+1] = tmp;
    }
}
