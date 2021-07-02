/*
* 1. 구간합을 구하기 위해 세그먼트 트리를 활용한다.
*
* 주의) - 세그먼트 트리 내 합을 계산할 때 트리값을 변경해서는 안된다.
*         - n,m,k 이외의 수는 모두 long long 자료형이다.
*
* 시간복잡도 = O(n log n)
*/

#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

vector<long long> arr;
vector<long long> tree;

// 1. 세그먼트 트리 초기화 (start == end 까지 좌, 우 값 합을 계산)
long long init(int node, int start, int end) {
	if (start == end)
		return tree[node] = arr[start];

	int mid = (start + end) / 2;
	return tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
}
// 2. 특정값이 변경되면 세그먼트 트리를 갱신 (특정값의 인덱스가 start, end 내에 있으면 갱신)
void update(int node, int start, int end, int index, long long diff) {
	// 범위를 벗어나면 return
	if (index < start || index > end)
		return;
	tree[node] += diff;

	// 마지막에 도달할 때까지
	if (start != end) {
		int mid = (start + end) / 2;
		update(node * 2, start, mid, index, diff);
		update(node * 2 + 1, mid + 1, end, index, diff);
	}
}
// 3.  세그먼트 트리를 통해 구간합 return
long long sum(int node, int start, int end, int left, int right) {
	// 범위를 벗어나면 return 0
	if (left > end || right < start)
		return 0;
	// 범위 내에 있다면 해당 세그먼트 트리값 return
	if (left <= start && end <= right)
		return tree[node];

	// 범위에 걸쳐있다면 분할 후 재귀호출
	int mid = (start + end) / 2;
	return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);
}
int main() {
	int n, m, k;
	long long a, b, c;

	cin >> n >> m >> k;

	arr.resize(n + 1);

	int height = (int)ceil(log2(n));
	tree.resize(1 << (height + 1));

	for (int i = 0; i < n; i++)
		cin >> arr[i];

	init(1, 0, n - 1);

	for (int i = 0; i < m + k; i++) {
		cin >> a >> b >> c;

		if (a == 1) {
			long long diff = c - arr[b - 1];
			arr[b - 1] = c;
			update(1, 0, n - 1, b - 1, diff);
		}
		else if (a == 2)
			cout << sum(1, 0, n - 1, b - 1, c - 1) << '\n';
	}
}
