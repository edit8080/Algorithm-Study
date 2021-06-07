/**
* 1. 현재 위치가 1인 경우 탐색을 시작한다.
* 2. 상, 좌, 좌상 대각선 방향의 값이 1이면 true를 return한다. (정사각형 만족)
* 3. 2번에서 true이면 상, 좌, 좌상 대각선이 가지고 있는 max_length 중 (최소값+1)을 return 한다.
*
* 주의) 전체가 0인 경우가 있으니 mlength 값 초기화에 주의
*
* 시간복잡도 = O(n*m) (n=세로 길이, m=가로 길이)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, m;
int direction[2][3] = { {-1, -1, 0}, {-1, 0, -1} };
vector<string> square;
vector<vector<int>> max_length;

int length(int x, int y) {
	int ret = 1000;

	for (int i = 0; i < 3; i++) {
		int bef_x = x + direction[0][i];
		int bef_y = y + direction[1][i];

		ret = min(ret, max_length[bef_x][bef_y]);
	}
	return ret + 1;
}

bool is_square(int x, int y) {
	for (int i = 0; i < 3; i++) {
		int bef_x = x + direction[0][i];
		int bef_y = y + direction[1][i];

		if (!(square[bef_x][bef_y] == '1'))
			return false;
	}
	return true;
}

int main() {
	int mlength = 0;  // 주의!
	cin >> n >> m;

	square.resize(n);
	max_length.resize(n, vector<int>(m, 0));

	for (int i = 0; i < n; i++)
		cin >> square[i];

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			if (square[i][j] == '1') {
				max_length[i][j] = 1;
				mlength = 1; // 주의!
			}
		}
	}

	for (int i = 1; i < n; i++) {
		for (int j = 1; j < m; j++) {
			if (square[i][j] == '1' && is_square(i, j)) {
				max_length[i][j] = length(i, j);
				mlength = max(mlength, max_length[i][j]);
			}
		}
	}
	cout << mlength * mlength;
}
