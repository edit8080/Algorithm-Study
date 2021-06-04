/*
* 1. n=1이면 1개 삽입할 수 있음
* 2. n=2이면 2개 삽입할 수 있음
* 3. n=3이면 n=1인 경우와 n=2인 경우가 합쳐진 경우라고 판단할 수 있음
* 4. 따라서 cnt(n) = cnt(n-1)+cnt(n-2) 라는 점화식을 세울 수 있음
*
* 시간복잡도 = O(n)
*/

#include <iostream>

using namespace std;

int main() {
	int n;
	int cnt[1001] = { 0 };

	cin >> n;
	cnt[1] = 1;
	cnt[2] = 2;

	for (int i = 3; i <= n; i++)
		cnt[i] = (cnt[i - 1] + cnt[i - 2]) % 10007;

	cout << cnt[n];
}
