/*
* 1. 1을 기준으로 *3, *2, +1 에 대한 최소 연산 개수를 cnt 배열에 저장한다.
* 2. 6과 같이 *3이 *2보다 빠른 경우가 메모한 경우가 있으므로 저장된 값에 대해 최소값을 비교한다.
* 3. 최소값을 비교하기 위해 모든 cnt를 INT_MAX로 초기화하고 cnt[1] = 0으로 초기화한다.
*
* 시간복잡도 = O(n)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

int main() {
	int n;
	cin >> n;

	vector<int> cnt(n + 1, INT_MAX);
	cnt[1] = 0;

	for (int i = 1; i <= n; i++) {
		if (i * 3 <= n)
			cnt[i * 3] = min(cnt[i * 3], cnt[i] + 1);
		if (i * 2 <= n)
			cnt[i * 2] = min(cnt[i * 2], cnt[i] + 1);
		if (i + 1 <= n)
			cnt[i + 1] = min(cnt[i + 1], cnt[i] + 1);
	}
	cout << cnt[n];
}
