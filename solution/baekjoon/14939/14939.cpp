/*
* 1. 나머지 줄에서 윗 칸에 대해서 체크를 하기 위해,
*    윗 줄이 없는 첫번째 줄에 대해서 누를 수 있는 모든 경우를 체크한다.
* 2. 2~마지막 줄에서는 윗 칸의 전구가 켜져있을 때 현재 칸을 누른다.
* 3. 마지막 줄까지 탐색한 후 최소 경우의 수를 비교한다.
*
* 시간복잡도 = O(2^n * n^2) (2^n : 첫 줄을 누르는 모든 경우, n^2: 2~마지막 줄까지 누르는 경우)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

#define SIZE 10

using namespace std;

int direction[2][4] = { {-1,1,0,0},{0,0,-1,1} };

// 모든 전구가 꺼졌는지 체크
bool all_off_check(vector<vector<bool>>& bulbs) {
	for (int i = 0; i < SIZE; i++)
		for (int j = 0; j < SIZE; j++)
			if (bulbs[i][j]) return false;
	return true;
}

// 전구 스위치 누르기 (상하좌우, 중심 Toggle)
void toggle_bulbs(vector<vector<bool>>& bulbs, int x, int y) {
	bulbs[x][y] = !bulbs[x][y];

	for (int i = 0; i < 4; i++) {
		int next_x = x + direction[0][i];
		int next_y = y + direction[1][i];

		if (next_x >= 0 && next_x < SIZE && next_y >= 0 && next_y < SIZE)
			bulbs[next_x][next_y] = !bulbs[next_x][next_y];
	}
}

int bulbs_check(vector<vector<bool>>& bulbs) {
	int min_cnt = INT_MAX;

	// 첫 줄에 대한 모든 경우의 수
	for (int step = 0; step < (1 << SIZE); step++) {
		vector<vector<bool>> copy_bulbs = bulbs;
		int cnt = 0;

		// 첫 줄 Toggle
		for (int bit = 0; bit < SIZE; bit++) {
			if (step & (1 << bit)) {
				cnt += 1;
				toggle_bulbs(copy_bulbs, 0, bit);
			}
		}

		// 2~10 줄 Toggle (위 전구가 꺼져있으면 누름)
		for (int i = 1; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (copy_bulbs[i - 1][j]) {
					cnt += 1;
					toggle_bulbs(copy_bulbs, i, j);
				}
			}
		}
		// 최소 경우의 수 체크
		if (all_off_check(copy_bulbs))
			min_cnt = min(min_cnt, cnt);
	}
	return min_cnt;
}

int main() {
	char c;
	vector<vector<bool>> bulbs(SIZE, vector<bool>(SIZE));

	for (int i = 0; i < SIZE; i++) {
		for (int j = 0; j < SIZE; j++) {
			cin >> c;
			bulbs[i][j] = (c == '#' ? false : true);
		}
	}

	int ret = bulbs_check(bulbs);

	cout << (ret == INT_MAX ? -1 : ret);
}
