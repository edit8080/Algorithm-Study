/*
* 1. 곱하려는 수의 일의 자리가 처음으로 0이 아닌 수가 등장할 때까지 0을 제거한다.
* 2. 곱하고난 이후 결과값도 처음으로 0이 아닌 수가 등장할 때까지 수정
* 3. 이후 일정 결과값 이후의 값은 불필요하므로 제거한다.
*
* 시간복잡도 = O(n) (n: 구하고자하는 팩토리얼 값 <= 20000)
*/

#include <iostream>

using namespace std;

int main() {
	int n;
	long long mul = 1;

	cin >> n;

	for (int i = 1; i <= n; i++) {
		int num = i;
		// 곱하려는 수의 일의 자리가 처음으로 0이 아닌 수로 변경
		while (num % 10 == 0)
			num /= 10;

		mul *= num;
		// 곱한 결과값도 0이 아닌 수가 등장할 때까지 수정
		while (mul % 10 == 0)
			mul /= 10;

		mul = mul % 1000000000;
	}
	cout << mul % 10;
}
