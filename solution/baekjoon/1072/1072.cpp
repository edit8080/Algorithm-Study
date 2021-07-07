/*
* 1. 나눗셈 식을 통해 수학적으로 계산한다. (반복문은 시간 초과)
*
* 시간복잡도 = O(1)
*/

#include <iostream>
#include <cmath>

using namespace std;

long long winning(long long x, long long y) {
	return (y * 100) / x;
}

int main() {
	long long x, y;
	long long bef_winning;

	cin >> x >> y;

	bef_winning = winning(x, y);

	// 불가능한 경우(mod Error)
	if (bef_winning >= 99)
		cout << -1;
	else {
		// 최소가 될 수 있는 크기
		double min_val = (double)((bef_winning + 1) * x - 100 * y) / (double)(99 - bef_winning);
		cout << (int)ceil(min_val);
	}
}
