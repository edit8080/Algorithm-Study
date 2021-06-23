/*
* 1. ü������ ������ ����ĭ�� ��ĭ���� �����Ѵ�.
* 2. �� ������ ���� ����� ������ �»�, ���� �밢�� ������ üũ�Ѵ�. (1���� �迭�� ���� O(1)�� üũ�Ѵ�.)
* 3. ��Ʈ��ŷ�� ���� ����� ���� �� �ִ� ��츦 ����ȭ�Ѵ�.
*
* ����) ����ĭ�� ��ĭ�� ���� �����ǹǷ� ������ �����Ͽ� �ð����⵵�� ���̴� ����� �����Ѵ�.
*
* �ð����⵵ = O(2^(n/2*n/2)) (n: ü������ ũ��) => ����ĭ�� �ִ� Ž�� ���� = n/2*n/2
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

		// ü���� ��ġ ����, ����, �»� �밢�� üũ
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

	// toggle�� ���� ����ĭ�� ��ĭ �������� ���� (n => n/2 + n/2)
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