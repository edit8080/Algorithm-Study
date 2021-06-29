/*
* 프림 알고리즘
* 1. 아직 방문하지 않은 점들 중 확인점 집합과 최소 거리가 되는 점을 찾는다.
* 2. 확인점 집합과 최소 거리가 되는 점을 방문한다.
* 3. 확인점 집합과의 거리를 업데이트한다.
* 4. 모든 점을 탐색할 때까지 1~3 과정을 반복한다.
*
* 시간복잡도 = O(V^2)
*/
#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

vector<vector<pair<int, int>>> edges; // index : 출발지, value : { 목적지, 비용 }
vector<int> dist;
vector<bool> selected;

int prim(int v) {
	int min_val = 0;

	// 1번 노드부터 시작
	dist[1] = 0;

	// 4. 모든 점에 대해 탐색한다.
	for (int i = 1; i <= v; i++) {
		int now = -1;
		int min_dist = INT_MAX;

		// 1. 아직 방문하지 않은 점들 중 확인점 집합과 최소 거리가 되는 점을 찾는다. 
		for (int j = 1; j <= v; j++) {
			if (!selected[j] && min_dist > dist[j]) {
				min_dist = dist[j];
				now = j;
			}
		}
		// 2. 확인점 집합과 최소 거리가 되는 점을 방문한다.
		min_val += min_dist;
		selected[now] = true;

		// 3. 확인점 집합과의 거리를 업데이트한다.
		for (auto edge : edges[now])
			dist[edge.first] = min(dist[edge.first], edge.second);
	}
	return min_val;
}

int main() {
	int v, e;
	int v1, v2, val;
	cin >> v >> e;

	edges.resize(v + 1);
	dist.resize(v + 1, INT_MAX);
	selected.resize(v + 1, false);

	for (int i = 0; i < e; i++) {
		cin >> v1 >> v2 >> val;
		edges[v1].push_back({ v2, val });
		edges[v2].push_back({ v1, val });
	}
	cout << prim(v);
}
