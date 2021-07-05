/*
* 1. 100000 개를 탐색해야하므로 O(n) 탐색으로 불가능하다.
* 2. 이분탐색을 통해 탐색한다.
*
* 주의) cin 최적화에 유의한다.
*
* 시간복잡도 = O(n log n) (정렬)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> num;

int binary_search(int low, int high, int x) {
	while (low <= high) {
		int mid = (low + high) / 2;

		if (num[mid] == x)
			return 1;
		else if (num[mid] > x)
			high = mid - 1;
		else
			low = mid + 1;
	}
	return 0;
}

int main() {
	cin.tie(0);
	ios_base::sync_with_stdio(0);

	int n, m, find_num;
	cin >> n;
	num.resize(n);

	for (int i = 0; i < n; i++)
		cin >> num[i];

	sort(num.begin(), num.end());

	cin >> m;
	while (m--) {
		cin >> find_num;
		cout << binary_search(0, n - 1, find_num) << '\n';
	}
}
