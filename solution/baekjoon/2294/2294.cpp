/*
* 1. 동전의 입력을 받을 때 해당 동전의 개수(cnt)를 1로 저장하고
*     나머지 값은 INT_MAX로 나중에 최소를 비교할 때 영향을 미치지 않도록 한다.
* 2. 현재 위치에서 모든 동전의 값만큼 뺀 위치에 저장된 동전의 개수가 INT_MAX가 아니면 동전이 사용된 것이다.
* 3. 중복 탐색이 일어날 때는 해당 경우의 최소를 비교한다.
*
* 시간복잡도 = O(n*k) (n: 동전의 개수, k: 목적지)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

int main() {
	int n, k;
	vector<int> coin;
	vector<int> cnt(10001, INT_MAX);

	cin >> n >> k;
	coin.resize(n);

	for (int i = 0; i < n; i++) {
		cin >> coin[i];
		cnt[coin[i]] = 1;
	}
	sort(coin.begin(), coin.end());

	for (int i = coin[0]; i <= k; i++)
		for (int j = 0; j < n; j++)
			if (i - coin[j] >= 0 && cnt[i - coin[j]] != INT_MAX)
				cnt[i] = min(cnt[i], cnt[i - coin[j]] + 1);

	cout << (cnt[k] == INT_MAX ? -1 : cnt[k]);
}
