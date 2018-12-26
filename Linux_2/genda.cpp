#include <iostream>
#include <stdlib.h>
#include <time.h>
using namespace std;
int main(int argc, char *argv[]){
	srand((unsigned)time(NULL));
    int n = atoi(argv[1]);
	for (int i = 0; i < n; i++)
		cout << rand() << '\n';
	cout << endl;
	return 0;
}
