/*
* 1. 2x2 타일을 덮는다. 덮을 수 없다면 false 를 반환하고 덮을 수 있다면 닫은 타일로 표시한다.
*    <덮을 수 없는 경우>
*	  - 2x2 타일이 범위를 넘어가는 경우
*	  - 2x2 영역에 닫혀있는 타일이 있는 경우
*
* 시간복잡도 = O(n^2)
*/

#include <iostream>
#include <vector>

using namespace std;

int n, m;

bool cover_tile(vector<string>& tile, int x, int y) {
	for (int i = x; i < x + 2; i++) {
		for (int j = y; j < y + 2; j++) {
			// 덮을 수 없다면 false 반환
			if (i >= n || j >= m || tile[i][j] == '.')
				return false;

			// 타일 덮기
			tile[i][j] = '.';
		}
	}
	return true;
}

int main() {
	int t;
	cin >> t;

	for (int test_case = 1; test_case <= t; test_case++) {
		cin >> n >> m;
		bool check = true;
		vector<string> tile(n);

		for (int i = 0; i < n; i++)
			cin >> tile[i];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (check && tile[i][j] == '#')
					check = check & cover_tile(tile, i, j);
			}
		}
		cout << "#" << test_case << ' ' << (check ? "YES" : "NO") << '\n';
	}
}
