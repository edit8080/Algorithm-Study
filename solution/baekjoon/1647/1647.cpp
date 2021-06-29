/*
* 1. 마을이 2개 남을 때까지 최소를 찾아야하므로 프림 알고리즘보다 크루스칼 알고리즘을 사용한다.
* 2. 크루스칼 알고리즘으로 노드를 합칠 때 전체 그룹의 개수를 1개씩 감소시킨다.
* 3. 마을이 2개 남을 때까지 크루스칼 알고리즘을 수행한다.
*
* 시간복잡도 = O(E log E) = O(E log V) (V^2 <= E 이므로) (E : 간선, V : 정점)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct edge {
	int v1;
	int v2;
	int val;
};

int n, m;
vector<edge> costs;
vector<int> parent;
vector<int> height;

// 유니온 파인드 - find
int find_parent(int v) {
	if (parent[v] == v)
		return v;
	return parent[v] = find_parent(parent[v]);
}

// 유니온 파인드 - union
void union_vertex(int v1, int v2) {
	v1 = find_parent(v1);
	v2 = find_parent(v2);

	if (v1 == v2)
		return;

	// height가 더 작은 정점이 자식이 되는 것에 유의한다.
	if (height[v1] < height[v2])
		swap(v1, v2);

	parent[v2] = v1;
	if (height[v1] == height[v2])
		height[v1]++;
}
bool cmp(const edge& a, const edge& b) {
	return a.val < b.val ? true : false;
}

// 크루스칼 알고리즘
int kruskal() {
	int total_cost = 0;
	int group = n;

	sort(costs.begin(), costs.end(), cmp);

	for (auto cost : costs) {
		if (find_parent(cost.v1) != find_parent(cost.v2) && group > 2) {
			total_cost += cost.val;
			union_vertex(cost.v1, cost.v2);
			group -= 1;
		}
	}
	return total_cost;
}

int main() {
	int v1, v2, val;
	cin >> n >> m;

	parent.resize(n + 1, 0);
	height.resize(n + 1, 0);

	// 개별 부모 초기화
	for (int i = 1; i <= n; i++)
		parent[i] = i;

	for (int i = 0; i < m; i++) {
		cin >> v1 >> v2 >> val;
		costs.push_back({ v1, v2, val });
	}
	cout << kruskal();
}
