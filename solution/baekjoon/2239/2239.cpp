/*
* 1. 9*9 이므로 0~80까지의 인덱스를 활용한다.
* 2. 가로, 세로, 3*3 영역의 스도쿠 값을 체크한다.
* 3. 만약 값을 삽입할 수 없다면 백트래킹을 통해 이전의 값을 수정하여 탐색을 진행한다.
*
* 주의) 인덱스가 마지막에 도달했을 때가 최소이므로 complete 변수로 체크한다.
*         체크하지 않으면 스도쿠가 가능한 모든 경우를 탐색한다.
*/

#include <iostream>
#include <vector>

using namespace std;

const int len = 9;
bool complete = false;
vector<vector<int>> sudoku;

bool check(int x, int y, int val) {
	int area_x = (x / 3) * 3;
	int area_y = (y / 3) * 3;

	// 가로 체크
	for (int i = 0; i < len; i++) {
		if (sudoku[x][i] == val) {
			return false;
		}
	}
	// 세로 체크
	for (int i = 0; i < len; i++) {
		if (sudoku[i][y] == val)
			return false;
	}
	// 3*3 영역 체크
	for (int i = area_x; i < area_x + 3; i++) {
		for (int j = area_y; j < area_y + 3; j++) {
			if (sudoku[i][j] == val)
				return false;
		}
	}
	return true;
}

void backtracking(int idx) {
	if (idx > 80) {
		complete = true;
		return;
	}

	int x = idx / len;
	int y = idx % len;

	if (sudoku[x][y] != 0)
		backtracking(idx + 1);
	else {
		for (int j = 1; j <= len; j++) {
			if (sudoku[x][y] == 0 && check(x, y, j)) {
				sudoku[x][y] = j;
				backtracking(idx + 1);
				if (complete) return;
				sudoku[x][y] = 0;
			}
		}
	}
}

int main() {
	sudoku.resize(len, vector<int>(len));

	for (int i = 0; i < len; i++) {
		for (int j = 0; j < len; j++) {
			sudoku[i][j] = cin.get() - '0';
		}
		cin.get();
	}
	backtracking(0);

	for (int i = 0; i < len; i++) {
		for (int j = 0; j < len; j++) {
			cout << sudoku[i][j];
		}
		cout << '\n';
	}
}