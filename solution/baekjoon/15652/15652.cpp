/*
* 1. vector에 방문값을 저장하고 재귀적으로 탐색을 수행한다.
* 2. 재귀 탐색동안 vector에 넣은 개수를 세고 기준값과 일치하면 vector에 저장된 값을 출력한다.
* 3. 재귀 탐색을 마치면 기존에 넣었던 방문값을 뺀다. (백트래킹 기법)
*
* 시간복잡도 = O(n^m)
*/

#include <iostream>
#include <vector>

using namespace std;

int n, m;
vector<int> num;

void loop(vector<int>& permutation, int start, int cnt) {
	if (cnt == m) {
		for (int i = 0; i < m; i++)
			cout << permutation[i] << ' ';
		cout << '\n';
		return;
	}

	for (int i = start; i < n; i++) {
		permutation.push_back(num[i]);
		loop(permutation, i, cnt + 1);
		permutation.pop_back();
	}
}

int main() {
	cin >> n >> m;
	vector<int> permutation;

	for (int i = 1; i <= n; i++)
		num.push_back(i);

	loop(permutation, 0, 0);
}
