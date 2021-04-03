/*
*   1. 카드 3장을 겹치지 않게 선택한다.
*   2. (숫자 m - 선택한 카드 3장의 합)이 최소가 되는 경우를 탐색한다. 
*
*   시간복잡도 = O(n^3)
*/

#include <iostream>

#define MIN(x, y) (x)<(y)?(x):(y)

using namespace std;

int main(){
    int n, m;
    int card[100];

    cin >> n >> m;

    int min = m;

    for (int i = 0; i < n; i++)
        cin >> card[i];

    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                int difference = m - (card[i] + card[j] + card[k]);

                if (difference >= 0)
                    min = MIN(min, difference);
            }
        }
    }
    cout << m - min;
    return 0;
}
