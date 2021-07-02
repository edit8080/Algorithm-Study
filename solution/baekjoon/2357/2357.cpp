/*
* 1. 세그먼트 트리의 양 노드를 통해 범위에 대한 최대,최소값을 초기화한다.
* 2. 범위와 세그먼트 트리를 통해 최대, 최소값을 계산한다.
*
* 주의) 입출력이 많이 발생하면 입출력 최적화를 수행한다.
*
* 시간복잡도 = O(n log n)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <climits>

using namespace std;

vector<int> num;
vector<pair<int, int>> tree;

// 세그먼트 트리 초기화
pair<int, int> init(int node, int start, int end) {
	if (start == end)
		return tree[node] = { num[start], num[start] };

	int mid = (start + end) / 2;

	pair<int, int> left_node = init(node * 2, start, mid);
	pair<int, int> right_node = init(node * 2 + 1, mid + 1, end);

	return tree[node] = { min(left_node.first, right_node.first), max(left_node.second, right_node.second) };
}
// 최대, 최소 계산
pair<int, int> min_max(int node, int start, int end, int left, int right) {
	if (end < left || start > right)
		return { INT_MAX, 0 };

	if (left <= start && end <= right)
		return tree[node];

	int mid = (start + end) / 2;
	pair<int, int> left_node = min_max(node * 2, start, mid, left, right);
	pair<int, int> right_node = min_max(node * 2 + 1, mid + 1, end, left, right);

	return { min(left_node.first, right_node.first), max(left_node.second, right_node.second) };
}
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	int n, m;
	int height;
	int left, right;
	pair<int, int> ret;

	cin >> n >> m;

	num.resize(n);
	height = (int)ceil(log2(n));
	tree.resize(1 << (height + 1));

	for (int i = 0; i < n; i++)
		cin >> num[i];

	init(1, 0, n - 1);

	for (int i = 0; i < m; i++) {
		cin >> left >> right;
		ret = min_max(1, 0, n - 1, left - 1, right - 1);
		cout << ret.first << ' ' << ret.second << '\n';
	}
}
