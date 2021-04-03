/*
*   1. 9명의 합에서 2명을 뺀 나머지의 합이 100인 경우를 찾는다.
*
*   시간복잡도 = O(n^2)
*/

#include <iostream>

#define CHILDREN 9

using namespace std;

void print(int *children, int sum) {
    for (int i = 0; i < CHILDREN; i++) {
        for (int j = i + 1; j < CHILDREN && j != i; j++) {
            if (sum - (children[i] + children[j]) == 100) {
                for (int l = 0; l < CHILDREN; l++)
                    if(l != i && l != j)
                        cout << children[l] << '\n';

                return;
            }
        }
    }
}

int main(){
    int children[CHILDREN];
    int sum = 0;

    for (int i = 0; i < CHILDREN; i++) {
        cin >> children[i];
        sum += children[i];
    }
    print(children, sum);                 
    
    return 0;
}
