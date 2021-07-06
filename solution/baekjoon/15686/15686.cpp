/*
* 1. 조합으로 살아남은 치킨집을 선택한다.
* 2. 선택한 치킨집에 대해 최소 거리합을 찾는다.
*
* 주의) 조합 사용 시 1개부터 체크할 필요 없이 최대(m개)로 치킨집을 선택하는 것에 유의한다. (포함관계)
*
* 시간복잡도 = O(m * n^2) (최대 m개 선택 * 최소 거리 계산)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

int n, m;

int main() {
	cin >> n >> m;
	int input;
	vector<pair<int, int>> house;
	vector<pair<int, int>> chicken;

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> input;
			if (input == 1)
				house.push_back({ i, j });

			if (input == 2)
				chicken.push_back({ i, j });
		}
	}

	int min_dist_sum = INT_MAX;

	// 조합으로 살아남은 치킨집 선택(최대 선택)
	vector<bool> v(chicken.size() - m, false);
	v.insert(v.end(), m, true);

	do {
		int dist_sum = 0;
		vector<pair<int, int>> selected_chicken;

		// 치킨집 선택
		for (int i = 0; i < chicken.size(); i++) {
			if (v[i])
				selected_chicken.push_back(chicken[i]);
		}
		selected_chicken[0];

		// 최소 거리합 찾기
		for (int i = 0; i < house.size(); i++) {
			int dist = INT_MAX;
			for (int j = 0; j < selected_chicken.size(); j++) {
				dist = min(dist, abs(house[i].first - selected_chicken[j].first) + abs(house[i].second - selected_chicken[j].second));
			}
			dist_sum += dist;
		}
		min_dist_sum = min(min_dist_sum, dist_sum);

	} while (next_permutation(v.begin(), v.end()));

	cout << min_dist_sum;
}
