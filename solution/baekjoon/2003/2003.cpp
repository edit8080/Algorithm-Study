/*
* 1. 시작 인덱스를 변경해나가면서 합을 계산한다.
* 2. 계산된 합이 넘으면 탐색을 중단한다.
*
* 보충) 시간을 최적화하려면 투포인터 기법을 활용할 수 있다.
*
* 시간복잡도 = O(n^2)
*/

#include <iostream>
#include <vector>

using namespace std;

int main() {
	int n, m;
	int cnt = 0;
	vector<int> num;
	cin >> n >> m;

	num.resize(n);
	for (int i = 0; i < n; i++)
		cin >> num[i];

	for (int i = 0; i < n; i++) {
		int sum = 0;

		for (int j = i; j < n; j++) {
			sum += num[j];
			if (sum == m)
				cnt += 1;
			else if (sum > m)
				break;
		}
	}
	cout << cnt;
}
