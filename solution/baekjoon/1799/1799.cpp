/*
* 1. 체스판의 영역을 검은칸과 흰칸으로 분할한다.
* 2. 각 영역에 대해 비숍을 놓으면 좌상, 좌하 대각선 방향을 체크한다. (1차원 배열을 통해 O(1)로 체크한다.)
* 3. 백트래킹을 통해 비숍을 놓을 수 있는 경우를 최적화한다.
*
* 주의) 검은칸과 흰칸이 서로 독립되므로 영역을 분할하여 시간복잡도를 줄이는 기법에 주의한다.
*
* 시간복잡도 = O(2^(n/2*n/2)) (n: 체스판의 크기) => 영역칸의 최대 탐색 개수 = n/2*n/2
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n;
int max_cnt[2] = { 0 };
vector<vector<int>> board;
vector<pair<int, int>> area[2];
vector<bool> left_bottom;
vector<bool> left_up;

void backtracking(int color, int idx, int cnt) {
	max_cnt[color] = max(max_cnt[color], cnt);

	for (int i = idx; i < (int)area[color].size(); i++) {
		int x = area[color][i].first;
		int y = area[color][i].second;

		// 체스말 배치 여부, 좌하, 좌상 대각선 체크
		if (board[x][y] == 1 && !left_bottom[x + y] && !left_up[(n - 1) + (x - y)]) {
			left_bottom[x + y] = true;
			left_up[(n - 1) + (x - y)] = true;
			backtracking(color, i + 1, cnt + 1);
			left_bottom[x + y] = false;
			left_up[(n - 1) + (x - y)] = false;
		}
	}
}

int main() {
	cin >> n;
	board.resize(n, vector<int>(n));

	bool toggle = true;

	// toggle을 통해 검은칸과 흰칸 영역으로 분할 (n => n/2 + n/2)
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> board[i][j];

			toggle ? area[0].push_back({ i,j }) : area[1].push_back({ i,j });
			toggle = !toggle;
		}
		if (n % 2 == 0) toggle = !toggle;
	}

	left_bottom.resize(2 * n - 1, false);
	left_up.resize(2 * n - 1, false);
	backtracking(0, 0, 0);
	backtracking(1, 0, 0);

	cout << max_cnt[0] + max_cnt[1];
}