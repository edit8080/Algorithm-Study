/*
* 1. n==1 이면 주사위 모든 면의 합에서 가장 큰 값을 뺀 결과값이 최소
* 2. n > 1 일 때 최소가 되는 경우는 (a<b<c<d<e<f)
*	  윗면은 네 모서리가 c (4*c), 옆면과 붙어있는 모서리가 b (4*(n-2)*b), 내부가 a 인 경우가 최소 ((n-2)^2*a)
*    옆면은 다른 옆면과 붙어있는 한 모서리가 b (4*n*b), 나머지 면이 a 인 경우((4*n^2-4*n)*a)가 최소
* 3. 따라서 최소값은 4*c+(8*n-8)*b+(5*n^2-8*n+4)*a 이다.
*
* 시간복잡도 = O(1)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

long long n;

long long min_board(vector<long long>& value) {
	return (5 * n * n - 8 * n + 4) * value[0] + (8 * n - 8) * value[1] + 4 * value[2];
}

int main() {
	cin >> n;
	long long sum = 0;
	vector<long long> value;
	vector<long long> dice(6);

	for (int i = 0; i < 6; i++) {
		cin >> dice[i];
		sum += dice[i];
	}

	if (n == 1) {
		sort(dice.begin(), dice.end());
		cout << sum - dice[5];
	}
	else {
		for (int i = 0; i < 3; i++)
			value.push_back(min(dice[i], dice[5 - i]));

		sort(value.begin(), value.end());
		cout << min_board(value);
	}
}
