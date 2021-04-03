/*
*   1. -999 ~ 999까지 모든 경우를 방정식에 대입하여 해결한다.
*
*   시간복잡도 = O(n^2)
*/

#include <iostream>

using namespace std;

bool isAnswer(int *equation, int x, int y) {
    if (*equation * x + *(equation + 1) * y == *(equation + 2))
        return true;
    return false;
}


int main(){
    int equation[2][3];

    for(int i=0; i < 2; i++)
        for (int j = 0; j < 3; j++)
            cin >> equation[i][j];

    for (int x = -999; x <= 999; x++)
        for (int y = -999; y <= 999; y++)
            if (isAnswer(equation[0], x, y) && isAnswer(equation[1], x, y))
                cout << x << " " << y; 
    return 0;
}