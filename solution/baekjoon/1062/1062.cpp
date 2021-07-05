/*
* 1. 백트래킹을 통해 알파벳의 조합을 만든다.
* 2. 선택한 알파벳들이 k 개일 때 가능한 단어의 개수를 센다.
* 3. 알파벳 조합을 통해 가능한 최대 단어의 개수를 출력한다.
*
* 주의) 조합의 시간복잡도 = 2^n 임에 유의한다. (n! (x))
*
* 시간복잡도 = O(2^n)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, k;
bool alphabet[26] = { false };
vector<string> words;
int max_cnt = 0;

void max_word(int idx, int letter) {
	// 알파벳의 조합에서 가능한 단어의 개수를 센다
	if (letter == k) {
		int cnt = 0;

		for (int i = 0; i < n; i++) {
			bool check = true;

			for (int j = 0; j < (int)words[i].length(); j++) {
				if (!alphabet[words[i][j] - 'a']) {
					check = false;
					break;
				}
			}
			if (check)
				cnt += 1;
		}
		max_cnt = max(max_cnt, cnt);
		return;
	}

	// 가능한 알파벳의 조합을 백트래킹으로 탐색
	for (int i = idx; i < 26; i++) {
		if (!alphabet[i]) {
			alphabet[i] = true;
			max_word(i + 1, letter + 1);
			alphabet[i] = false;
		}
	}
}

int main() {
	cin >> n >> k;
	words.resize(n);

	// 단어 전처리
	for (int i = 0; i < n; i++) {
		cin >> words[i];
		words[i] = words[i].substr(4);
		words[i] = words[i].substr(0, words[i].length() - 4);
	}
	alphabet['a' - 'a'] = true;
	alphabet['n' - 'a'] = true;
	alphabet['t' - 'a'] = true;
	alphabet['i' - 'a'] = true;
	alphabet['c' - 'a'] = true;

	if (k < 5)
		cout << 0;
	else {
		max_word(0, 5);
		cout << max_cnt;
	}
}
