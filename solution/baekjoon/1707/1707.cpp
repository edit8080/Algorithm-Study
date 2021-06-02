﻿

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

bool bfs(vector<vector<int>>& graph, vector<int>& visited, int start) {
	queue<pair<int, int>> q;
	visited[start] = 0;
	q.push(make_pair(start, 0));

	while (!q.empty()) {
		int front = q.front().first;
		int order = q.front().second;
		q.pop();

		for (int i = 0; i < (int)graph[front].size(); i++) {
			int next = graph[front][i];
			int next_order = (order + 1) % 2;

			if (visited[next] == -1) {
				visited[next] = next_order;
				q.push(make_pair(next, next_order));
			}
			else
				if (visited[next] != next_order) return false;
		}
	}
	return true;
}


int main() {
	int k;
	cin >> k;

	while (k--) {
		int v, e;
		int x, y;
		bool is_bipartite = true;
		vector<vector<int>> graph;
		vector<int> visited;

		cin >> v >> e;
		graph.resize(v + 1);
		visited.resize(v + 1, -1);

		for (int i = 0; i < e; i++) {
			cin >> x >> y;
			graph[x].push_back(y);
			graph[y].push_back(x);
		}

		for (int i = 1; i <= v; i++) {
			if (visited[i] == -1 && is_bipartite)
				is_bipartite = bfs(graph, visited, i);
		}
		cout << (is_bipartite ? "YES" : "NO") << '\n';
	}
}
