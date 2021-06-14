/*
* 1. 전체 메모리에 제한이 있으니 재귀로 값을 출력한다.
* 2. 중앙 영역과 나머지 영역으로 분할한 후 중앙 영역에 속한 값은 1을 return한다.
* 3. 나머지 영역에 속해있으면 전체 size를 n으로 나누어 분할하고 시작점을 변경하여 재귀 호출을 수행한다.
* 4. size==1 일 때 0을 return한다.
*
* 시간복잡도 = O(n*m*k) (n: 탐색 범위 행, m: 탐색 범위 열, k: 단위 시간(재귀 반복횟수))
*/

#include <iostream>
#include <cmath>

using namespace std;

int s, n, k;

int recursive(int x, int y, int start_x, int start_y, int size) {
	int middle_x, middle_y;
	int size_unit = size / n;

	// 초기
	if (size == 1)
		return 0;

	middle_x = (size / 2) - (k * size_unit / 2) + start_x;
	middle_y = (size / 2) - (k * size_unit / 2) + start_y;

	// 중앙 영역
	if (x >= middle_x && x < middle_x + size_unit * k && y >= middle_y && y < middle_y + size_unit * k)
		return 1;

	// 나머지 영역
	for (int i = start_x; i < start_x + size; i += size_unit) {
		for (int j = start_y; j < start_y + size; j += size_unit) {
			if (x >= i && x < i + size_unit && y >= j && y < j + size_unit)
				return recursive(x, y, i, j, size_unit);
		}
	}
}

int main() {
	int x1, x2, y1, y2;

	cin >> s >> n >> k;
	cin >> x1 >> x2 >> y1 >> y2;

	for (int i = x1; i <= x2; i++) {
		for (int j = y1; j <= y2; j++) {
			cout << recursive(i, j, 0, 0, (int)pow(n, s));
		}
		cout << '\n';
	}
}
