/*
* 1. knapsack 알고리즘을 활용한다.
* 2. 목표 금액을 만드는 최소값 = (이전 동전으로 만드는 최소값) + ((목표 금액 - 현재 동전 금액)최소값)
* 3. (목표 금액 - 현재 동전 금액) = 0이면 최소값이 1이 되도록하기 위해 목표 금액=0 을 만드는 최소값을 1로 초기화한다.
*
* 시간복잡도 = O(n * m) (n:동전 종류, m:목표 금액)
*/

#include <iostream>
#include <vector>

using namespace std;

int main() {
	int t;
	cin >> t;

	while (t--) {
		int n, m;

		cin >> n;
		vector<int> coins(n + 1);
		for (int i = 1; i <= n; i++)
			cin >> coins[i];

		cin >> m;
		vector<vector<int>> cost(n + 1, vector<int>(m + 1, 0));

		for (int i = 1; i <= n; i++)
			cost[i][0] = 1;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (i >= coins[j])
					cost[j][i] = cost[j - 1][i] + cost[j][i - coins[j]];
				else
					cost[j][i] = cost[j - 1][i];
			}
		}
		cout << cost[n][m] << '\n';
	}
}