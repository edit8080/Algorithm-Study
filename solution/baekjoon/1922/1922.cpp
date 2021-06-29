/*
* 1. MST (크루스칼 알고리즘) 을 통해 최소비용을 계산한다.
*
* 시간복잡도 = O(E log E) (E: 간선의 개수)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct edge {
	int v1, v2, val;
};

vector<edge> costs;
vector<int> parents;
vector<int> heights;

bool cmp(const edge& a, const edge& b) {
	return a.val < b.val ? true : false;
}

int find_parents(int v) {
	if (parents[v] == v) return v;
	return parents[v] = find_parents(parents[v]);
}

void union_vertex(int v1, int v2) {
	v1 = find_parents(v1);
	v2 = find_parents(v2);

	if (heights[v1] < heights[v2])
		swap(v1, v2);

	if (v1 == v2) return;

	parents[v2] = v1;
	if (heights[v1] == heights[v2])
		heights[v1]++;
}

int kruskal() {
	int min_val = 0;
	sort(costs.begin(), costs.end(), cmp);

	for (edge cost : costs) {
		if (find_parents(cost.v1) != find_parents(cost.v2)) {
			min_val += cost.val;
			union_vertex(cost.v1, cost.v2);
		}
	}
	return min_val;
}

int main() {
	int n, m;
	cin >> n >> m;

	costs.resize(m);
	parents.resize(n + 1);
	heights.resize(n + 1, 0);

	for (int i = 0; i < m; i++)
		cin >> costs[i].v1 >> costs[i].v2 >> costs[i].val;

	for (int i = 1; i <= n; i++)
		parents[i] = i;

	cout << kruskal();
}
