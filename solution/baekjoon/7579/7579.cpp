/*
* 1. 문제에서 탐색 목적값이 m=10^7 이므로 dp[n][m]을 구성할 수 없음, dp[n][sum]으로 구성
* 2. dp 탐색의 열 기준을 memory가 아닌 cost로 설정
* 3. 행은 0~sum까지 설정한 후  knapsack 알고리즘 적용
* 4. dp 탐색 후 최초로 dp[i][j] >= m 일 때 j가 최소의 비용
*
* 주의) m대신 sum까지 탐색하는 방법에 유의한다.
*         dp 탐색 후 최초로 dp[i][j] >= m 일 때 j가 최소의 비용임을 활용한다.
*
* 시간복잡도 = O(n^3) (n: 앱의 수) (n= 앱 수(행) n^2 : 합의 최대(열))
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, m;
int sum = 0;
vector<int> memory;
vector<int> cost;
vector<vector<int>> memo;

// 최초로 크거나 같을 때 최소 비용
void ans() {
	for (int i = 0; i <= sum; i++) {
		for (int j = 1; j <= n; j++) {
			if (memo[j][i] >= m) {
				cout << i;
				return;
			}
		}
	}
}
void dp() {
	for (int i = 0; i <= sum; i++) {
		for (int j = 1; j <= n; j++) {
			// 못 넣으면 위의 값 계승
			if (cost[j] > i)
				memo[j][i] = memo[j - 1][i];

			// 넣을 수 있으면 위의 값과 (memo값+현재 메모리값) 중 최대값 비교
			else
				memo[j][i] = max(memo[j - 1][i], memo[j - 1][i - cost[j]] + memory[j]);
		}
	}
}

int main() {
	cin >> n >> m;

	memory.resize(n + 1);
	cost.resize(n + 1);
	memo.resize(n + 1, vector<int>(10001, 0));

	for (int i = 1; i <= n; i++)
		cin >> memory[i];

	for (int i = 1; i <= n; i++) {
		cin >> cost[i];
		sum += cost[i];
	}
	dp();
	ans();
}
