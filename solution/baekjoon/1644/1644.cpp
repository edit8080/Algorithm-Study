/*
* 1. 에라토스테네스의 체를 통해 n까지 가능한 모든 소수를 판별한다.
* 2. 완전 탐색을 통해 소수의 합으로 만들 수 있는 n까지의 수를 비교한다.
*
* 시간복잡도 = O(n log(log(n))) (에라토스테네스의 체 시간복잡도)
*/

#include <iostream>
#include <vector>

using namespace std;

vector<int> eratos(int n) {
	vector<bool> is_prime(n + 1, true);
	vector<int> prime;

	is_prime[0] = false;
	is_prime[1] = false;

	for (int i = 2; i * i <= n; i++) {
		if (is_prime[i]) {
			for (int j = i * i; j <= n; j += i)
				is_prime[j] = false;
		}
	}
	for (int i = 2; i <= n; i++)
		if (is_prime[i])
			prime.push_back(i);

	return prime;
}

int main() {
	int n;
	cin >> n;
	vector<int> prime = eratos(n);
	int cnt = 0;

	for (int i = 0; i < (int)prime.size(); i++) {
		int sum = 0;

		for (int j = i; j < (int)prime.size(); j++) {
			sum += prime[j];

			if (sum > n)
				break;
			else if (sum == n)
				cnt += 1;
		}
	}
	cout << cnt;
}
