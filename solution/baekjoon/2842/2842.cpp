/*
* 1. 투 포인터를 통해 피로도 범위를 설정한다.
* 2. 설정한 범위를 기준으로 BFS 탐색을 수행했을 때 탐색 가능한 집의 개수를 탐색한다.
* 3. 모든 집을 탐색할 수 있다면 low 범위를 늘리면서 안되는 경우가 될 때까지 탐색한다.
* 4. 만약 모든 집을 탐색할 수 없다면 high 범위를 늘려가며 모든 집을 탐색할 수 있는 경우를 탐색한다.
*
* 주의) 우체국의 위치(시작 위치)가 피로도 범위내에 존재해야한다.
*
* 시간복잡도 = O(n^3) (n : 투포인터를 통한 범위 탐색, n^2 : BFS 탐색)
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int n;
int direction[2][8] = { {-1,1,0,0,-1,-1,1,1},{0,0,-1,1,-1,1,-1,1,} };
vector<string> village;
vector<vector<int>> weight;
vector<vector<bool>> visited;

bool in_bound(int x, int y) {
	return (x >= 0 && x < n && y >= 0 && y < n) ? true : false;
}

int bfs(int start_x, int start_y, int low, int high) {
	int cnt = 0;
	vector<vector<bool>> visited(n, vector<bool>(n, false));
	queue<pair<int, int>> house;

	house.push({ start_x, start_y });
	visited[start_x][start_y] = true;

	while (!house.empty()) {
		int x = house.front().first;
		int y = house.front().second;
		house.pop();

		// 집을 찾으면 탐색한 개수 증가
		if (village[x][y] == 'K')
			cnt += 1;

		// 다음 노드 이동 (범위내 이동, 방문여부, 피로도 범위 확인)
		for (int i = 0; i < 8; i++) {
			int next_x = x + direction[0][i];
			int next_y = y + direction[1][i];

			if (in_bound(next_x, next_y) && !visited[next_x][next_y] && weight[next_x][next_y] >= low && weight[next_x][next_y] <= high) {
				visited[next_x][next_y] = true;
				house.push({ next_x, next_y });
			}
		}
	}
	return cnt;
}


int main() {
	cin >> n;
	int cnt_house = 0;
	int post_x, post_y;
	vector<int> weights;

	village.resize(n);
	weight.resize(n, vector<int>(n));

	for (int i = 0; i < n; i++)
		cin >> village[i];

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> weight[i][j];
			weights.push_back(weight[i][j]);
		}
	}

	// K, P 위치 탐색
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (village[i][j] == 'K')
				cnt_house += 1;

			else if (village[i][j] == 'P') {
				post_x = i;
				post_y = j;
			}
		}
	}

	// 투포인터 준비
	sort(weights.begin(), weights.end());
	int low_idx = 0;
	int high_idx = 0;
	int min_diff = weights.back() - weights.front();

	// 피로도 범위 설정
	while (low_idx < weights.size() && high_idx < weights.size() && low_idx <= high_idx) {
		int low = weights[low_idx];
		int high = weights[high_idx];
		int post_weight = weight[post_x][post_y];

		// 해당 피로도 범위에서 집을 전부 찾을 수 있다면 체크
		if ((post_weight >= low && post_weight <= high) && bfs(post_x, post_y, low, high) == cnt_house) {
			min_diff = min(min_diff, high - low);
			// 전부 찾을 수 있다면 low를 늘려가면서 가능한지 체크
			low_idx += 1;
		}
		// 전부 찾을 수 없다면 high를 늘려가면서 가능한지 체크
		else
			high_idx += 1;
	}
	cout << min_diff;
}
