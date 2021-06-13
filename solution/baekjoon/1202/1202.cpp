/*
* 1. 그리디 알고리즘을 활용한다.
* 2. 배낭과 보석을 무게 기준으로 오름차순 정렬한다.
* 3. 배낭을 순차적으로 탐색하면서 넣을 수 있는 모든 보석의 가치를 우선순위 큐에 넣는다.
* 4. 3번에서 인덱스를 사용하여 배낭에 넣을 수 있는 최대 보석의 위치를 저장해놓고 다음 배낭을 탐색할 때 해당 인덱스부터
*	   넣을 수 있는 보석을 탐색한다.
* 5. 우선순위 큐에 가장 앞에 있는 가치가 넣을 수 있는 최대 보석이다.
*
* 주의) 이미 넣은 보석을 기존에 넣은 가치를 재사용하고 탐색 범위에서 쉽게 제거하기 위해 우선순위 큐를 사용하는 것에 주의한다.
*
* 시간복잡도 = O(k*log(n)) (k: 배낭의 수, n: 보석의 수) (log(n) : 우선순위 큐를 통한 최대값 탐색)
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int main() {
	int n, k;
	int m, v;
	int bag;
	vector<pair<int, int>> jewels;
	vector<int> bags;

	cin >> n >> k;

	for (int i = 0; i < n; i++) {
		cin >> m >> v;
		jewels.push_back(make_pair(m, v));
	}
	for (int i = 0; i < k; i++) {
		cin >> bag;
		bags.push_back(bag);
	}

	sort(jewels.begin(), jewels.end());
	sort(bags.begin(), bags.end());

	int idx = 0;
	priority_queue<int> pq;
	long long ans = 0;

	for (int i = 0; i < k; i++) {
		int max_price = 0;

		while (idx < n && jewels[idx].first <= bags[i])
			pq.push(jewels[idx++].second);

		if (!pq.empty()) {
			ans += pq.top();
			pq.pop();
		}
	}
	cout << ans;
}
