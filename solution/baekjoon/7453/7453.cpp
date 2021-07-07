/*
* 1. 단순 조합으로 계산하면 4000^4 이므로 A,B 와 C,D의 조합의 합을 계산하고(4000*4000) 투 포인트로 문제를 해결한다.
* 2. A,B와 C,D 끼리의 조합의 합을 계산한 후 정렬한다.
* 3. 투 포인터를 통해 A, B를 front_idx, C,D를 end_idx로 지정해 투 포인터를 수행한다.
* 4. front_idx, end_idx 값의 합이 0인 경우 중복 상황에 대해 조합이 가능하니 따로 처리를 수행한다.
*
* 시간복잡도 = O(n^2 log n) (정렬)
*/


#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n;

bool in_bound(int idx) {
	return (idx >= 0 && idx < n * n) ? true : false;
}

long long two_pointer(vector<int>& comb_ab, vector<int>& comb_cd) {
	long long cnt = 0;
	int front_idx = 0;
	int end_idx = (n * n) - 1;

	while (in_bound(front_idx) && in_bound(end_idx)) {
		// 0이면 동일한 경우의 수 확인
		if (comb_ab[front_idx] + comb_cd[end_idx] == 0) {
			long long cnt_ab = 1;
			long long cnt_cd = 1;

			// 중복 체크
			while (in_bound(front_idx + 1) && comb_ab[front_idx] == comb_ab[front_idx + 1]) {
				cnt_ab += 1;
				front_idx += 1;
			}
			while (in_bound(end_idx - 1) && comb_cd[end_idx] == comb_cd[end_idx - 1]) {
				cnt_cd += 1;
				end_idx -= 1;
			}
			cnt += cnt_ab * cnt_cd;
			front_idx += 1;
			end_idx -= 1;
		}
		// 0보다 작으면 합이 증가하는 방향으로 진행
		else if (comb_ab[front_idx] + comb_cd[end_idx] < 0)
			front_idx += 1;

		// 0보다 크면 합이 감소하는 방향으로 진행
		else
			end_idx -= 1;
	}
	return cnt;
}


int main() {
	cin >> n;

	vector<vector<int>> input(n, vector<int>(4));
	vector<int> comb_ab;
	vector<int> comb_cd;

	for (int i = 0; i < n; i++)
		for (int j = 0; j < 4; j++)
			cin >> input[i][j];

	// A, B 조합의 합
	for (int i = 0; i < n; i++) {
		int a = input[i][0];

		for (int j = 0; j < n; j++) {
			int b = input[j][1];
			comb_ab.push_back(a + b);
		}
	}

	// C,D 조합의 합
	for (int i = 0; i < n; i++) {
		int c = input[i][2];

		for (int j = 0; j < n; j++) {
			int d = input[j][3];
			comb_cd.push_back(c + d);
		}
	}
	// 조합의 합 정렬
	sort(comb_ab.begin(), comb_ab.end());
	sort(comb_cd.begin(), comb_cd.end());

	cout << two_pointer(comb_ab, comb_cd);
}
