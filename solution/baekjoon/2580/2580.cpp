#include <iostream>
#include <vector>

using namespace std;

const int len = 9;
vector<vector<int>> sudoku;
bool complete = false;

// 스도쿠 가능 체크
bool check(int x, int y, int val) {
	int area_x = (x / 3) * 3;
	int area_y = (y / 3) * 3;

	for (int i = 0; i < len; i++)
		if (val == sudoku[x][i])
			return false;

	for (int i = 0; i < len; i++)
		if (val == sudoku[i][y])
			return false;

	for (int i = area_x; i < area_x + 3; i++)
		for (int j = area_y; j < area_y + 3; j++)
			if (val == sudoku[i][j])
				return false;

	return true;
}

void backtracking(int idx) {
	int x = idx / len;
	int y = idx % len;

	if (idx >= len * len) {
		complete = true;
		return;
	}
	if (sudoku[x][y] != 0)
		backtracking(idx + 1);
	else {
		for (int j = 1; j <= 9; j++) {
			if (check(x, y, j)) {
				sudoku[x][y] = j;
				backtracking(idx + 1);
				if (complete) return;
				sudoku[x][y] = 0;
			}
		}
	}
}

int main() {
	sudoku.resize(9, vector<int>(9));

	for (int i = 0; i < len; i++)
		for (int j = 0; j < len; j++)
			cin >> sudoku[i][j];

	backtracking(0);

	for (int i = 0; i < len; i++) {
		for (int j = 0; j < len; j++) {
			cout << sudoku[i][j] << ' ';
		}
		cout << '\n';
	}
}
