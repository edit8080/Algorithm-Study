/*
* 1. 교환이 불가능한 수는 미리 체크한다. (1,2,... 9, 10, 20, ... , 90)
* 2. 각 탐색마다 BFS 탐색을 수행한다.
* 3. 탐색마다 발생하는 중복을 체크한다.
* 4. 교환 후 맨 앞자리가 0이 되지 않는다면 다음 탐색을 위해 큐에 push 한다.
*
* 시간복잡도 = O(n^k) (n: 자릿수, k: 교환 횟수)
* (단, 탐색간 중복을 제거했기 때문에 실제 시간복잡도는 더 작아진다.)
*/

#include <iostream>
#include <queue>
#include <vector>
#include <algorithm>
#include <string>

using namespace std;

int k;
string num;

// 교환이 불가능한 수 체크
bool check(int x) {
	if (x < 100 && (x % 10) == 0)
		return false;
	if (x < 10)
		return false;
	return true;
}

int bfs() {
	queue<string> q;
	q.push(num);

	for (int i = 0; i < k; i++) {
		int q_size = q.size();
		// 탐색마다 발생하는 중복 체크
		vector<bool> visited(1000001, false);

		while (q_size--) {
			string front = q.front();
			q.pop();

			for (int i = 0; i < (int)front.length(); i++) {
				for (int j = i + 1; j < (int)front.length(); j++) {
					// 맨 앞자리 0 체크
					if (i == 0 && front[j] == '0')
						continue;

					string next = front;
					next[i] = front[j];
					next[j] = front[i];

					if (!visited[stoi(next)]) {
						visited[stoi(next)] = true;
						q.push(next);
					}
				}
			}
		}
	}

	// k 번 탐색 종료 후 가능한 경우들 비교
	int ans = 0;
	while (!q.empty()) {
		ans = max(ans, stoi(q.front()));
		q.pop();
	}
	return ans;
}

int main() {
	cin >> num >> k;

	if (!check(stoi(num)))
		cout << -1;
	else
		cout << bfs();
}
