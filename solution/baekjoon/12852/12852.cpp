/*
* 1. i=1~n까지 탐색을 시작하고 memo 배열에는 { 개수, 체인 } 형태로 저장한다.
* 2. 탐색을 진행하면서 i*3, i*2, i+1 를 만들 수 있는 최소 횟수를 비교하고 최소가 될 경우 체인값을 변경한다.
* 3. 탐색이 종료된 후 n에 대한 개수와(memo[n].first) 1까지 체인을 돌면서 저장된 체인값을 출력한다.
*
* 시간복잡도 = O(n)
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

	// { 개수, 체인 }
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