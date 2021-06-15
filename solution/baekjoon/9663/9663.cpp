/*
* 1. 인덱스를 행, 값을 열로 갖는 1차원 체스판 배열을 사용한다. (초기화 : -1)
* 2. 열을 1개씩 늘려나가면서 체크한다.
* 3. 행은 인덱스로 구분되어있고 열은 1개씩 늘려나가므로 행,열은 겹치지 않는다. 따라서 대각선만 체크한다.
* 4. 체스말을 놓을 수 있다면 다음 열에 대해 3에 대한 재귀탐색을 수행한다.
* 5. 백트래킹을 통해 이전에 탐색한 결과를 초기화한다.
*
* 주의) 대각선을 체크할 때 체스판 내에 있어야 한다(<0, >=n 이 될 수 없음)는 점에 유의한다.
*
* 시간복잡도 = O(n!) (n: 체스판의 크기)
*/

#include <iostream>
#include <vector>

using namespace std;

int n;
int cnt = 0;

bool check(vector<int>& board, int check_x, int check_y) {
	for (int i = 1; i < n; i++) {
		// 대각선 체크
		if (check_x >= i && check_y >= i && board[check_x - i] == check_y - i)
			return false;

		if (check_x + i < n && check_y + i < n && board[check_x + i] == check_y + i)
			return false;

		if (check_x >= i && check_y + i < n && board[check_x - i] == check_y + i)
			return false;

		if (check_x + i < n && check_y >= i && board[check_x + i] == check_y - i)
			return false;
	}
	return true;
}

void nqueen(vector<int>& board, int y) {
	if (y == n - 1)
		cnt++;
	else {
		for (int i = 0; i < n; i++) {
			// board[i] == -1 (행 최적화) && y+1 (열 최적화)
			if (board[i] == -1 && check(board, i, y + 1)) {
				board[i] = y + 1;
				nqueen(board, y + 1);
				board[i] = -1;
			}
		}
	}
}

int main() {
	cin >> n;

	for (int i = 0; i < n; i++) {
		// board : board[행] = 열
		vector<int> board(n, -1);
		board[i] = 0;
		nqueen(board, 0);
	}
	cout << cnt;
}