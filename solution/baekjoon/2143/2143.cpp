/*
* 1. 연속된 부분 배열이 가능한 경우를 배열에 저장한다. (n*(n-1)/2)
* 2. 이후 저장한 부분 배열을 정렬한다.
* 3. 투 포인터를 통해 t값과 비교하여 가능한 경우의 수를 체크한다.
*
* 시간복잡도 = O(n^2 log n)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;
int t, n, m;

long long two_pointer(vector<int>& comb_a, vector<int>& comb_b) {
	long long cnt = 0;
	int front_idx = 0;
	int end_idx = comb_b.size() - 1;

	while (front_idx < comb_a.size() && end_idx >= 0) {
		int check = comb_a[front_idx] + comb_b[end_idx];

		// t 값과 합이 동일하면 가능한 경우 모두 탐색
		if (check == t) {
			long long cnt_front = 1;
			long long cnt_end = 1;

			while (front_idx + 1 < comb_a.size() && comb_a[front_idx] == comb_a[front_idx + 1]) {
				cnt_front += 1;
				front_idx += 1;
			}
			while (end_idx - 1 >= 0 && comb_b[end_idx] == comb_b[end_idx - 1]) {
				cnt_end += 1;
				end_idx -= 1;
			}
			cnt += cnt_front * cnt_end;
			front_idx += 1;
			end_idx -= 1;
		}
		// t 값보다 작으면 합을 키우는 방향으로 진행
		else if (check < t)
			front_idx += 1;
		// t 값보다 크면 합을 줄이는 방향으로 진행
		else
			end_idx -= 1;
	}
	return cnt;
}

// 부분합 만들기
vector<int> combine(vector<int> input, int size) {
	vector<int> ret;

	for (int i = 0; i < size; i++) {
		int sum = 0;

		for (int j = i; j < size; j++) {
			sum += input[j];
			ret.push_back(sum);
		}
	}
	return ret;
}

int main() {
	cin >> t;

	// A 경우의 수 구성
	cin >> n;
	vector<int> a(n);
	for (int i = 0; i < n; i++)
		cin >> a[i];

	vector<int> comb_a = combine(a, n);

	// B 경우의 수 구성
	cin >> m;
	vector<int> b(m);
	for (int i = 0; i < m; i++)
		cin >> b[i];

	vector<int> comb_b = combine(b, m);

	// 정렬
	sort(comb_a.begin(), comb_a.end());
	sort(comb_b.begin(), comb_b.end());

	cout << two_pointer(comb_a, comb_b);
}
