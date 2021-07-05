/*
* 1. 날짜마다 탐색을 수행해야하니 BFS 탐색을 수행한다.
* 2. 물이 이동할 때마다 숲에 물 표시를 넣어 고슴도치가 동시에 이동할 수 없도록 한다.
* 3. BFS 탐색 동안 visited 배열에 날짜를 넣어 BFS 탐색 후 비버의 굴 위치의 날짜 출력 (-1 이면 KAKTUS)
*
* 시간복잡도 = O(n^2)
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int r, c;
int direction[2][4] = { {-1,1,0,0},{0,0,-1,1} };
int day = 0;
vector<string> forest;
vector<vector<vector<int>>> visited; // 물과 고슴도치 이동 체크 (0 - 물 / 1 - 고슴도치)
queue<pair<int, pair<int, int>>> water;
queue<pair<int, pair<int, int>>> target;

// 숲에 있는지 확인
bool inForest(int x, int y) {
	return (x >= 0 && x < r && y >= 0 && y < c) ? true : false;
}

void bfs() {
	while (!target.empty()) {

		// 1. 물 먼저 이동 (동일한 날짜에 대해서)
		while (!water.empty() && day == water.front().second.second) {
			int waterX = water.front().first;
			int waterY = water.front().second.first;
			water.pop();

			for (int i = 0; i < 4; i++) {
				int nextWaterX = direction[0][i] + waterX;
				int nextWaterY = direction[1][i] + waterY;

				if (inForest(nextWaterX, nextWaterY) && (forest[nextWaterX][nextWaterY] == '.' || forest[nextWaterX][nextWaterY] == 'S')
					&& visited[0][nextWaterX][nextWaterY] == -1) {
					water.push({ nextWaterX, {nextWaterY, day + 1} });
					forest[nextWaterX][nextWaterY] = '*';  // 숲 변경
					visited[0][nextWaterX][nextWaterY] = day + 1;
				}
			}
		}

		// 2. 고슴도치 이동 (동일한 날짜에 대해서)
		while (!target.empty() && day == target.front().second.second) {
			int targetX = target.front().first;
			int targetY = target.front().second.first;

			// 찾으면 종료
			if (forest[targetX][targetY] == 'D')
				return;

			target.pop();

			for (int i = 0; i < 4; i++) {
				int nextTargetX = direction[0][i] + targetX;
				int nextTargetY = direction[1][i] + targetY;

				if (inForest(nextTargetX, nextTargetY) && (forest[nextTargetX][nextTargetY] == '.' || forest[nextTargetX][nextTargetY] == 'D') &&
					visited[1][nextTargetX][nextTargetY] == -1) {
					target.push({ nextTargetX, { nextTargetY, day + 1 } });
					visited[1][nextTargetX][nextTargetY] = day + 1;
				}
			}
		}
		day += 1;
	}
}

int main() {
	int beaverX, beaverY;

	cin >> r >> c;

	forest.resize(r);
	visited.resize(2, vector<vector<int>>(r, vector<int>(c, -1)));

	for (int i = 0; i < r; i++)
		cin >> forest[i];

	for (int i = 0; i < r; i++) {
		for (int j = 0; j < c; j++) {
			if (forest[i][j] == '*') {
				water.push({ i, {j, 0} });
				visited[0][i][j] = 0;
			}
			else if (forest[i][j] == 'S') {
				target.push({ i, {j, 0} });
				visited[1][i][j] = 0;
			}
			else if (forest[i][j] == 'D') {
				beaverX = i;
				beaverY = j;
			}
		}
	}

	bfs();

	if (visited[1][beaverX][beaverY] != -1)
		cout << visited[1][beaverX][beaverY];
	else
		cout << "KAKTUS";
}
