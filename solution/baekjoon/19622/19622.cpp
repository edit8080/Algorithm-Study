/*
* 1. k번째 회의는 k-1과 k+1 회의와 겹치지 않음을 통해 회의 정보가 순차적으로 들어온다는 사실을 알 수 있음
* 2. k번째 회의의 최대 인원은 k-2와 k-3에서 건너오는 최대 인원 중 최대값과 자신의 인원 수를 더한 값이다.
*     (그 이전에 들어오는 값은 k-2, k-3에 도달했을 때 최대가 된다(그리디 알고리즘))
*
* 시간복잡도 = O(n)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
	int n;
	vector<int> room;
	int total[100001] = { 0 };
	cin >> n;

	for (int i = 0; i < n; i++) {
		int start, end, people;
		cin >> start >> end >> people;
		room.push_back(people);
	}
	total[1] = room[0];
	total[2] = room[1];

	for (int i = 3; i <= n; i++)
		total[i] = max(total[i - 2], total[i - 3]) + room[i - 1];

	cout << max(total[n - 1], total[n]);
}
