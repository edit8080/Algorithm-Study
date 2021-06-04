/*
* 1. n=1이면 1개 삽입 가능
* 2. n=2이면 3개 삽입 가능 (=, ㅁ)
* 3. n=3이면 1개 뒤에 ㅁ, = 가 붙고 2개 뒤에 ㅣ가 오는 형태
* 4. 따라서 cnt(n) = cnt(n-2)*2 + cnt(n-1) 의 점화식이 성립함
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
	cnt[2] = 3;

	for (int i = 3; i <= n; i++)
		cnt[i] = (cnt[i - 1] + 2 * cnt[i - 2]) % 10007;

	cout << cnt[n];
}
