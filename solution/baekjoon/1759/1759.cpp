/*
* 1. BFS 탐색을 통해 문자를 하나씩 붙여나가면서 탐색한다.
* 2. 자음과 모음의 개수를 센 다음 탐색 종료 후 남아있는 값들이 조건에 만족하는 값만 결과로 출력한다.
*
* 시간복잡도 = O(c^c)
* (단, 문제의 조건에 의해 탐색이 이루어지지 않을 때가 존재하므로 시간이 적게 걸림)
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <string>

using namespace std;

int l, c;
vector<char> input;
vector<bool> vowel;

vector<string> bfs() {
	queue<pair<string, pair<int, int>>> q;
	vector<string> ans;

	// BFS 초기 설정
	for (int i = 0; i < c; i++) {
		string letter = "";
		letter += input[i];
		vowel[input[i] - 'a'] ? q.push({ letter, {1, 0} }) : q.push({ letter, {0, 1} });
	}

	// BFS 탐색
	for (int i = 0; i < l - 1; i++) {
		int q_size = q.size();

		while (q_size--) {
			string front = q.front().first;
			int vowel_cnt = q.front().second.first;
			int conso_cnt = q.front().second.second;

			q.pop();

			for (int i = 0; i < c; i++) {
				bool in_string = false;

				// 이미 있다면 패스
				for (int j = 0; j < front.length(); j++) {
					if (front[j] == input[i])
						in_string = true;
				}
				// 없다면 사전순으로 단어 설정
				if (!in_string && front[front.length() - 1] < input[i])
					vowel[input[i] - 'a'] ? q.push({ front + input[i], {vowel_cnt + 1, conso_cnt} }) : q.push({ front + input[i], {vowel_cnt, conso_cnt + 1} });
			}
		}
	}

	// 결과 정리
	while (!q.empty()) {
		string front = q.front().first;
		int vowel_cnt = q.front().second.first;
		int conso_cnt = q.front().second.second;
		q.pop();

		// 최소 모음 1개, 자음 2개
		if (vowel_cnt >= 1 && conso_cnt >= 2)
			ans.push_back(front);
	}
	sort(ans.begin(), ans.end());
	return ans;
}

int main() {
	cin >> l >> c;
	input.resize(c);
	vowel.resize(26);

	for (int i = 0; i < c; i++) {
		cin >> input[i];

		if (input[i] == 'a')
			vowel[input[i] - 'a'] = true;

		else if (input[i] == 'e')
			vowel[input[i] - 'a'] = true;

		else if (input[i] == 'i')
			vowel[input[i] - 'a'] = true;

		else if (input[i] == 'o')
			vowel[input[i] - 'a'] = true;

		else if (input[i] == 'u')
			vowel[input[i] - 'a'] = true;
	}

	vector<string> ans = bfs();

	for (int i = 0; i < ans.size(); i++)
		cout << ans[i] << '\n';
}
