/*
* 1. i=1~n���� Ž���� �����ϰ� memo �迭���� { ����, ü�� } ���·� �����Ѵ�.
* 2. Ž���� �����ϸ鼭 i*3, i*2, i+1 �� ���� �� �ִ� �ּ� Ƚ���� ���ϰ� �ּҰ� �� ��� ü�ΰ��� �����Ѵ�.
* 3. Ž���� ����� �� n�� ���� ������(memo[n].first) 1���� ü���� ���鼭 ����� ü�ΰ��� ����Ѵ�.
*
* �ð����⵵ = O(n)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

int main() {
	int n;
	cin >> n;

	vector<pair<int, int>> memo(n + 1, { INT_MAX, 0 });
	int chain_idx = n;

	// { ����, ü�� }
	memo[1] = { 0, 0 };

	for (int i = 1; i <= n; i++) {
		if (3 * i <= n && memo[3 * i].first > memo[i].first + 1) {
			memo[3 * i].first = memo[i].first + 1;
			memo[3 * i].second = i;
		}
		if (2 * i <= n && memo[2 * i].first > memo[i].first + 1) {
			memo[2 * i].first = memo[i].first + 1;
			memo[2 * i].second = i;
		}
		if (i + 1 <= n && memo[i + 1].first > memo[i].first + 1) {
			memo[i + 1].first = memo[i].first + 1;
			memo[i + 1].second = i;
		}
	}
	cout << memo[n].first << '\n';
	while (chain_idx > 0) {
		cout << chain_idx << ' ';
		chain_idx = memo[chain_idx].second;
	}
}