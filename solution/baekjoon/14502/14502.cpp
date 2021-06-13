/*
* 1. 조합을 통한 완전 탐색으로 3개의 벽을 세워 map을 구성한다.
* 2. 바이러스가 여러개 일 수 있으므로 바이러스의 모든 초기 위치를 큐에 넣어놓는다.
* 3. 1번에서 구성한 모든 map들과 2번 큐를 기반으로 BFS 탐색을 수행한다.
* 4. 모든 경우에서 가능한 안전 영역 중 최대 영역값을 출력한다.
*
* 시간복잡도 = O(n^3 * n^2) (n: 한 변의 길이) (n^3 : 조합 시간(nC3), n^2 : BFS 탐색 시간)
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int n, m;
int direction[2][4] = { {-1,1,0,0},{0,0,-1,1} };

void bfs(vector<vector<int>>& map, vector<vector<bool>>& visited, queue<pair<int, int>>& q) {

	while (!q.empty()) {
		int x = q.front().first;
		int y = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			int next_x = x + direction[0][i];
			int next_y = y + direction[1][i];

			if (next_x >= 0 && next_x < n && next_y >= 0 && next_y < m) {
				if (!visited[next_x][next_y] && map[next_x][next_y] == 0) {
					visited[next_x][next_y] = true;
					q.push(make_pair(next_x, next_y));
				}
			}
		}
	}
}

int main() {
	cin >> n >> m;

	vector<vector<int>> map(n, vector<int>(m));
	vector<vector<bool>> visited(n, vector<bool>(m));
	vector<pair<int, int>> blank;
	queue<pair<int, int>> q;
	int safe = 0;

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			cin >> map[i][j];

			if (map[i][j] == 0)
				blank.push_back(make_pair(i, j));

			if (map[i][j] == 2) {
				q.push(make_pair(i, j));
				visited[i][j] = true;
			}
		}
	}

	vector<int> check(blank.size(), 0);

	for (int i = 0; i < 3; i++)
		check[i] = 1;

	do {
		int cnt = 0;

		vector<vector<int>> map_copy = map;
		vector<vector<bool>> visited_copy = visited;
		queue<pair<int, int>> q_copy = q;

		for (int i = 0; i < (int)blank.size(); i++) {
			if (check[i] == 1)
				map_copy[blank[i].first][blank[i].second] = 1;
		}

		bfs(map_copy, visited_copy, q_copy);

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (!visited_copy[i][j] && map_copy[i][j] == 0)
					cnt++;

		safe = max(safe, cnt);

	} while (prev_permutation(check.begin(), check.end()));

	cout << safe;
}
