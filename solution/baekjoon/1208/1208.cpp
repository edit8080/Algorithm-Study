/*
* 1. 2^40 은 시간초과가 발생하므로 절반(left, right)을 쪼개 2^20 * 2 형태로 계산한다.
* 2. left, right 를 통해 생성될 수 있는 순열의 합을 비트마스킹을 통해 계산한다.
* 3. 생성된 순열의 합을 하나는 내림차순, 다른 하나는 오름차순으로 정렬한다. (4번 동작에서 활용)
* 4. 두 순열의 합을 각각의 인덱스를 기준으로 목표값과 비교하여 각 동작을 수행한다.
*     - 합이 목표값과 일치하면 해당 값들과 동일한 값을 찾은 다음 곱하여 갯수를 계산(조합이 가능하므로)
*     - 합이 목표값보다 더 크면 감소시키는 방향으로 포인터 이동
*     - 합이 목표값보다 더 작으면 증가시키는 방향으로 포인터 이동
*
* 주의) 부분수열의 개수가 long long 자료형인 것에 주의한다.
*
* 시간복잡도 = O(2^n/2) (n: 정수의 개수)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, s;

long long combination_merge(vector<int>& left_sum, vector<int>& right_sum) {
	long long cnt = 0;
	int left_idx = 0;
	int right_idx = 0;
	int left_size = (int)left_sum.size();
	int right_size = (int)right_sum.size();

	while (left_idx < left_size && right_idx < right_size) {
		int check = left_sum[left_idx] + right_sum[right_idx];

		// 합이 목표값과 일치하면 해당 값들과 동일한 값을 찾은 다음 곱하여 갯수를 계산(조합이 가능하므로)
		if (check == s) {
			long long left_cnt = 0;
			long long right_cnt = 0;

			do {
				left_cnt += 1;
				left_idx += 1;
			} while (left_idx < left_size && left_sum[left_idx] == left_sum[left_idx - 1]);

			do {
				right_cnt += 1;
				right_idx += 1;
			} while (right_idx < right_size && right_sum[right_idx] == right_sum[right_idx - 1]);

			cnt += left_cnt * right_cnt;
		}
		// 합이 목표값보다 더 크면 감소시키는 방향으로 포인터 이동
		else if (check > s)
			left_idx += 1;

		// 합이 목표값보다 더 작으면 증가시키는 방향으로 포인터 이동
		else if (check < s)
			right_idx += 1;
	}
	return cnt;
}

vector<int> combination_sum(vector<int>& input) {
	int cnt = 1 << (int)input.size();
	vector<int> ret;

	// 비트마스킹을 통한 순열의 합 계산
	for (int i = 0; i < cnt; i++) {
		int sum = 0;

		for (int j = 0; j < (int)input.size(); j++)
			if (i & 1 << j) sum += input[j];

		ret.push_back(sum);
	}
	return ret;
}

int main() {
	cin >> n >> s;
	vector<int> left(n / 2);
	vector<int> right((n + 1) / 2);

	for (int i = 0; i < n / 2; i++)
		cin >> left[i];

	for (int j = 0; j < (n + 1) / 2; j++)
		cin >> right[j];

	vector<int> left_sum = combination_sum(left);
	vector<int> right_sum = combination_sum(right);

	sort(left_sum.begin(), left_sum.end(), greater<int>()); // 내림차순 정렬
	sort(right_sum.begin(), right_sum.end()); // 오름차순 정렬

	long long ans = combination_merge(left_sum, right_sum);

	cout << (s == 0 ? ans - 1 : ans);
}
