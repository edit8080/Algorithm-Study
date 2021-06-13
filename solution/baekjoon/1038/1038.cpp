/*
* 1. 0~9부터 시작한다.
* 2. 가장 앞에 있는 수보다 큰 수를 앞에 붙여가며 확장해나간다. (완전 탐색)
* 3. 9876543210 이 최대이고 해당 수는 1022번째 자리이다.
*
* 시간복잡도 = O(n) (n: 탐색 목적 번째 (n <= 1022))
*/
#include <iostream>
#include <vector>
#include <queue>
#include <string>
#include <algorithm>

using namespace std;

vector <string> decrease_num(vector<string>& num) {
	vector<string> ret;
	queue <string> q;

	for (int i = 0; i < (int)num.size(); i++)
		q.push(num[i]);

	while (!q.empty()) {
		string x = q.front();
		q.pop();

		int top = x[0] - '0';

		for (int i = top + 1; i <= 9; i++)
			ret.push_back(to_string(i) + x);
	}
	return ret;
}

int main() {
	int n;
	vector <string> num;

	cin >> n;

	if (n > 1022)
		cout << -1;
	else {
		for (int i = 0; i < 10; i++)
			num.push_back(to_string(i));

		int cnt = num.size();
		int bef = 0;

		while (cnt <= n) {
			bef = cnt;
			num = decrease_num(num);
			cnt += num.size();

			if (num[0].compare("9876543210") == 0) break;
		}

		sort(num.begin(), num.end());
		cout << num[n - bef];
	}
}
