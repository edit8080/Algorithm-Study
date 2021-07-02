/*
* 1. 세그먼트 트리를 통해 구간의 최소값을 계산한다.
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
vector<int> tree;

int init(int node, int start, int end) {
	if (start == end)
		return tree[node] = num[start];

	int mid = (start + end) / 2;
	return tree[node] = min(init(node * 2, start, mid), init(node * 2 + 1, mid + 1, end));
}
int find_min(int node, int start, int end, int left, int right) {
	if (end < left || start > right)
		return INT_MAX;

	if (left <= start && end <= right)
		return tree[node];

	int mid = (start + end) / 2;
	return min(find_min(node * 2, start, mid, left, right), find_min(node * 2 + 1, mid + 1, end, left, right));
}
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	int n, m;
	int height;
	int left, right;

	cin >> n >> m;

	num.resize(n);
	height = (int)ceil(log2(n));
	tree.resize(1 << (height + 1));

	for (int i = 0; i < n; i++)
		cin >> num[i];

	init(1, 0, n - 1);

	for (int i = 0; i < m; i++) {
		cin >> left >> right;
		cout << find_min(1, 0, n - 1, left - 1, right - 1) << '\n';
	}
}
