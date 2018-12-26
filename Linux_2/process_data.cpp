#include <stdio.h>
#define LOCAL
int main(){
#ifdef LOCAL
    freopen("demo_Realtime.txt", "r", stdin);
    freopen("data_out.txt", "w", stdout);
#endif
    float a[100];
    int n = 1;
    while(n <= 96 && scanf("%f", &a[n]) == 1){
        if (n%3 == 0){
            float average = (a[n-2] + a[n-1] + a[n])/3;
            printf("%.2f\n", average);
        }
        n++;
    }
    return 0;
}
