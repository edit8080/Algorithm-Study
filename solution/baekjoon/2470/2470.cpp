/*
* 1. 용액의 입력 배열을 정렬한다.
* 2. 투 포인터를 통해 배열의 처음(front_idx)과 끝(end_idx)을 가리키게 한다.
* 3. 두 용액의 합이 0보다 작으면 front_idx를 증가시켜 용액의 합을 증가하는 방향으로 이동한다.
*    두 용액의 합이 0보다 크면 end_idx를 감소시켜 용액의 합이 감소하는 방향으로 이동한다.
* 4. 만약 두 용액 합의 절댓값이 이전까지 계산한 용액 최소합의 절댓값보다 작으면 min_val과 max_val 값을 갱신한다.
*
* 주의) abs(check) < abs(sum) 일 때 min_val과 max_val 값을 둘 다 갱신해야하는 것에 유의한다.
*
* 시간복잡도 = O(n)
*/
#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <climits>

using namespace std;

int n;
vector<int> num;

pair<int, int> solution() {
	int min_val = num[0];
	int max_val = num[n - 1];
	int front_idx = 0;
	int end_idx = n - 1;
	int sum = INT_MAX;

	while (front_idx < end_idx) {
		int check = num[front_idx] + num[end_idx];

		// check == 0
		if (check == 0) {
			min_val = num[front_idx];
			max_val = num[end_idx];
			return { min_val, max_val };
		}
		else if (check < 0) {
			if (abs(check) < abs(sum)) {
				sum = check;
				min_val = num[front_idx];
				max_val = num[end_idx];
			}
			front_idx += 1;
		}
		else if (check > 0) {
			if (abs(check) < abs(sum)) {
				sum = check;
				min_val = num[front_idx];
				max_val = num[end_idx];
			}
			end_idx -= 1;
		}
	}
	return { min_val, max_val };
}

int main() {
	cin >> n;
	num.resize(n);

	for (int i = 0; i < n; i++)
		cin >> num[i];

	sort(num.begin(), num.end());

	pair<int, int> ans = solution();
	cout << ans.first << ' ' << ans.second;
}
