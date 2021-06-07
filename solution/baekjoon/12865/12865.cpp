/*
* 1. DP - Knapsack 알고리즘과 메모이제이션을 활용한다.
* 2. 현재 물건을 삽입할 수 없으면 이전까지 삽입한 물건의 최댓값 계승한다.
* 3. 현재 물건을 삽입할 수 있다면 이전까지 삽입한 물건의 최대값과
*	   현재 weight값 만큼 뺀 위치의 최대값 + 현재 가치 를 서로 비교한다.
*
* 시간복잡도 = O(n * k) (n : 물건의 수, k : 측정할 무게)
*
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, k;
vector<int> weight;
vector<int> value;
vector<vector<int>> max_value;

void knapsack() {
	for (int i = 1; i <= k; i++) {
		for (int j = 1; j <= n; j++) {
			// 못들어가는 경우 : 이전 값 계승(누적 탐색)
			if (i < weight[j])
				max_value[i][j] = max_value[i][j - 1];

			// 들어가는 경우 : 이전 값과 (현재 weight값 만큼 뺀 위치의 최대값 + 현재 가치) 비교
			else
				max_value[i][j] = max(max_value[i][j - 1], max_value[i - weight[j]][j - 1] + value[j]);
		}
	}
}

int main() {
	cin >> n >> k;

	max_value.resize(k + 1, vector<int>(n + 1, 0));
	weight.resize(n + 1);
	value.resize(n + 1);

	for (int i = 1; i <= n; i++)
		cin >> weight[i] >> value[i];

	knapsack();

	cout << max_value[k][n];
}