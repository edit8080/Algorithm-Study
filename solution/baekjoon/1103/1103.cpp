/*
* 1. 백트래킹을 통해 게임을 진행할 수 있는지 탐색한다.
* 2. 다음 노드가 게임을 종료시키지 않고, 아직 방문하지 않았고, 최대값을 갱신할 수 있다면 다음 노드로 탐색 수행
* 3. 다음 노드로 이미 방문한 노드가 선택된다면 사이클이 형성된다.
*
* 주의) 최대값을 갱신할 수 있는지 확인하기 위해 DP 의 메모이제이션 개념을 사용한다.
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, m;
bool infinity = false;
int max_cnt = 1;
int direction[2][4] = { {-1,1,0,0},{0,0,-1,1 } };

vector<vector<int>> board;
vector<vector<bool>> visited;
vector<vector<int>> max_memo;

bool end_game(int x, int y) {
	if (x < 0 || x >= n || y < 0 || y >= m)
		return true;
	if (board[x][y] == -1)
		return true;
	return false;
}

void backtracking(int x, int y, int cnt) {
	for (int i = 0; i < 4; i++) {
		int next_x = x + board[x][y] * direction[0][i];
		int next_y = y + board[x][y] * direction[1][i];

		// 게임 종료 가능 확인
		if (end_game(next_x, next_y))
			continue;

		// DP를 통해 최대값을 갱신할 수 있는지 확인한다. (백트래킹이 중복되는 것을 방지)
		if (!visited[next_x][next_y] && max_memo[next_x][next_y] < cnt + 1) {
			visited[next_x][next_y] = true;
			max_cnt = max(max_cnt, cnt + 1);
			max_memo[next_x][next_y] = cnt + 1;

			backtracking(next_x, next_y, cnt + 1);

			if (infinity) return;
			visited[next_x][next_y] = false;
		}
		// 이미 방문한 노드는 사이클 형성
		else if (visited[next_x][next_y]) {
			infinity = true;
			return;
		}
	}
}

int main() {
	cin >> n >> m;
	board.resize(n, vector<int>(m));
	visited.resize(n, vector<bool>(m, false));
	max_memo.resize(n, vector<int>(m, 0));

	cin.get();
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			board[i][j] = cin.get();

			if (board[i][j] == 'H')
				board[i][j] = -1;
			else
				board[i][j] = board[i][j] - '0';
		}
		cin.get();
	}
	visited[0][0] = true;
	backtracking(0, 0, 1);

	if (infinity)
		cout << -1;
	else
		cout << max_cnt;
}
