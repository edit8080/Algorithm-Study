/*
* 1. 모든 테스트케이스에 대해 찾을 수 있는 모든 열쇠를 구한다.
* 2. 구한 열쇠를 통해 문서를 찾는다.
* 3. 가능한 모든 열쇠를 찾게되면 자연스럽게 최대 개수의 문서를 찾을 수 있다.
*
* 시간복잡도 = O(t * h * w) (t: 테스트케이스의 개수, h * w : 방의 크기(BFS))
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int direction[2][4] = { {-1,1,0,0},{0,0,-1,1} };

bool in_room(int h, int w, int x, int y) {
	return (x >= 0 && x < h + 2 && y >= 0 && y < w + 2) ? true : false;
}

// 가능한 모든 열쇠들로 문서 개수 구하기
int find_docs(vector<vector<char>>& room, vector<bool>& key, int h, int w, int start_x, int start_y) {
	int cnt = 0;
	queue<pair<int, int>> q;
	vector<vector<bool>> visited(h + 2, vector<bool>(w + 2, false));

	q.push({ start_x, start_y });
	visited[start_x][start_y] = true;

	while (!q.empty()) {
		int x = q.front().first;
		int y = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			int next_x = x + direction[0][i];
			int next_y = y + direction[1][i];

			if (in_room(h, w, next_x, next_y) && !visited[next_x][next_y] && room[next_x][next_y] != '*') {
				char room_check = room[next_x][next_y];

				// 열쇠로 문열기
				if (room_check >= 'A' && room_check <= 'Z') {
					if (!key[room_check - 'A'])
						continue;
				}
				// 문서 찾기
				else if (room_check == '$')
					cnt += 1;

				visited[next_x][next_y] = true;
				q.push({ next_x, next_y });
			}
		}
	}
	return cnt;
}

// 가능한 모든 열쇠찾기
void get_all_key(vector<vector<char>>& room, vector<bool>& key, int h, int w, int start_x, int start_y) {
	queue<pair<int, int>> q;
	vector<vector<bool>> visited(h + 2, vector<bool>(w + 2, false));

	q.push({ start_x, start_y });
	visited[start_x][start_y] = true;

	while (!q.empty()) {
		int x = q.front().first;
		int y = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			int next_x = x + direction[0][i];
			int next_y = y + direction[1][i];

			if (in_room(h, w, next_x, next_y) && !visited[next_x][next_y] && room[next_x][next_y] != '*') {
				char room_check = room[next_x][next_y];

				// 열쇠 습득
				if (room_check >= 'a' && room_check <= 'z') {
					key[room_check - 'a'] = true;
				}
				// 열쇠로 문열기
				else if (room_check >= 'A' && room_check <= 'Z') {
					if (!key[room_check - 'A'])
						continue;
				}
				visited[next_x][next_y] = true;
				q.push({ next_x, next_y });
			}
		}
	}
}

int main() {
	cin.tie(0);
	ios_base::sync_with_stdio(0);

	int t;
	cin >> t;

	while (t--) {
		int h, w;
		string init_key;

		cin >> h >> w;
		vector<vector<char>> room(h + 2, vector<char>(w + 2, '.'));

		cin.get();
		for (int i = 1; i <= h; i++) {
			for (int j = 1; j <= w; j++) {
				room[i][j] = cin.get();
			}
			cin.get();
		}

		cin >> init_key;
		vector<bool> key(26, false);

		// 열쇠 초기화
		if (init_key[0] != '0') {
			for (int i = 0; i < init_key.length(); i++)
				key[init_key[i] - 'a'] = true;
		}

		for (int i = 0; i < h + 2; i++) {
			for (int j = 0; j < w + 2; j++) {
				// 가장자리에 대해 진입
				if (i == 0 || i == h + 1 || j == 0 || j == w + 1) {
					// 모든 열쇠 구하기
					get_all_key(room, key, h, w, i, j);
				}
			}
		}
		// 문서 구하기
		cout << find_docs(room, key, h, w, 0, 0) << '\n';
	}
}
