/*
* 1. 물병 개수가 기준치(k)보다 작으면 추가로 구매할 필요가 없음
* 2. 만들 수 있는 물병의 수는 이진수로 표현했을 때 1의 개수와 같다
* 3. 물병을 1개씩 구매할 때마다 만들 수 있는 물병의 수를 체크한다.
*
* 시간복잡도 = O(n) (n: 자릿수)
*/
#include <iostream>

using namespace std;

int binary_cnt(int n) {
	int cnt = 0;
	while (n > 0) {
		if (n % 2 == 1)
			cnt++;
		n /= 2;
	}
	return cnt;
}
int main() {
	int n, k;
	cin >> n >> k;

	// 전체 물병 개수가 기준치보다 작으면 추가로 구매할 필요가 없음
	if (n <= k)
		cout << 0;

	else {
		int buy = 0;

		while (true) {
			// 만들 수 있는 물병의 수는 이진수로 표현했을 때 1의 개수와 같다.
			if (binary_cnt(n + buy) <= k)
				break;
			buy++;
		}
		cout << buy;
	}
}
