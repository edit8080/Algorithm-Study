/*
* 1. 최소의 체력 소모로 최대의 기쁨을 구해야하므로 체력 소모를 기준으로 하는 knapsack 알고리즘을 사용한다.
* 2. 세준이의 체력이 0이 될 수 없으므로 99까지 탐색한다.
*
* 시간복잡도 = O(n*life) (n: 사람 수, life: 세준이 체력)
*/

#include <iostream>
#include <vector>
#include <algorithm>

#define LIFE 100
using namespace std;

int main() {
	int n;
	cin >> n;

	vector<int> life(n + 1);
	vector<int> joy(n + 1);
	vector<vector<int>> dp(LIFE, vector<int>(n + 1, 0));

	for (int i = 1; i <= n; i++)
		cin >> life[i];

	for (int i = 1; i <= n; i++)
		cin >> joy[i];

	for (int i = 0; i < LIFE; i++) {
		for (int j = 1; j <= n; j++) {
			if (i >= life[j])
				dp[i][j] = max(dp[i][j - 1], dp[i - life[j]][j - 1] + joy[j]);
			else
				dp[i][j] = dp[i][j - 1];
		}
	}
	cout << dp[LIFE - 1][n];
}
