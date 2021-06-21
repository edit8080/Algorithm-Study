/*
* 1. 입력값을 앞에서부터 순차적으로 더하고(sum) 길이를 1개씩 늘려나가면서(length) 큐에 저장한다.
* 2. sum값이 기준값(s)보다 크거나 같다면 큐에 저장된 값을 앞에서부터 하나씩 pop하면서 확인한다.
* 3. 2번을 확인할 때마다 sum값과 길이를 감소시킨다.
*
* 시간복잡도 = O(n)
*
*/

#include <iostream>
#include <vector>
#include <queue>
#include <climits>

using namespace std;

int partitial_length(vector<int>& input, int n, int s) {
	int min_length = INT_MAX;
	int sum = 0;
	int length = 0;
	queue<int> q;

	for (int i = 0; i < n; i++) {
		sum += input[i];
		length += 1;
		q.push(input[i]);

		if (sum < s)
			continue;

		else {
			// sum이 기준값 s보다 크거나 같다면 앞에서 하나씩 제거하면서 확인한다.
			while (!q.empty() && sum >= s) {
				if (sum >= s)
					min_length = min(min_length, length);

				sum -= q.front();
				length -= 1;
				q.pop();
			}
		}
	}
	return min_length == INT_MAX ? 0 : min_length;
}

int main() {
	int n, s;
	cin >> n >> s;

	vector<int> input(n);

	for (int i = 0; i < n; i++)
		cin >> input[i];

	cout << partitial_length(input, n, s);
}
