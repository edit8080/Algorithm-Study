/*
* 1. 사진틀에 사진을 넣을 때 {추천 대상자, 추천수, 시간} 정보를 함께 넣는다.
* 2. 사진틀에 사진을 넣기 전에 입력받은 추천 대상자가 이미 있다면 추천수를 증가시킨다.
* 3. 추천 대상자가 없고 사진틀에 여유가 있다면 그냥 넣는다.
* 4. 추천 대상자가 없는데 사진틀에 여유가 있다면 가장 앞에 있는 사진틀에 넣는다.
* 5. 사진틀에 넣고 난 후 추천수, 시간 조건에 따라 정렬한다.
*
* 시간복잡도 = O(n^2)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct info {
	int num, cnt, age;
};

// 추천수, 시간에 따른 정렬 조건
bool cmp(const info& a, const info& b) {
	if (a.cnt < b.cnt)
		return true;
	else if (a.cnt == b.cnt) {
		if (a.age < b.age)
			return true;
	}
	return false;
}

int main() {
	int n, m;
	int put = 0;
	cin >> n >> m;

	vector<info> frame;
	int recommend;

	for (int i = 0; i < m; i++) {
		cin >> recommend;
		bool in_frame = false;

		// 이미 있다면 추천수 증가
		for (int j = 0; j < (int)frame.size(); j++) {
			if (recommend == frame[j].num) {
				frame[j].cnt += 1;
				in_frame = true;
			}
		}
		// 없다면 교체
		if (!in_frame && put >= n)
			frame[0] = { recommend, 1, i };

		// 여유가 있다면 그냥 넣기
		if (!in_frame && put < n) {
			frame.push_back({ recommend, 1, i });
			put += 1;
		}

		// 추천수, 시간순으로 정렬
		sort(frame.begin(), frame.end(), cmp);
	}

	// 정렬 후 결과 출력
	vector<int> result;
	for (int i = 0; i < n; i++)
		result.push_back(frame[i].num);

	sort(result.begin(), result.end());
	for (int i = 0; i < n; i++)
		cout << result[i] << ' ';
}
