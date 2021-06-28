/*
* 1. 시작점을 확인점 집합에 push하고 방문여부를 체크한다.
* 2. 모든 확인점을 기준으로 아직 방문하지 않았고 확인점들과의 거리가 최소인 점을 체크한다.
* 3. 최소거리에 해당하는 점을 points에 push하고 방문여부, 최소합을 계산한다.
* 4. 모든 점들을 체크할 때까지 2~3을 반복한다.
*
* 시간복잡도 = O(V^3)
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>
#include <cmath>

using namespace std;

int n;
vector<pair<double, double>> cords;
vector<bool> visited;
vector<int> points; // 확인점 집합

double distance(double x1, double y1, double x2, double y2) {
	return sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
}

double prim(int start) {
	double dist_sum = 0.0;
	points.push_back(start);
	visited[start] = true;

	// 4. 모든 점들을 체크할 때까지 반복한다.
	while ((int)points.size() < n) {
		double min_dist = INT_MAX;
		int next = -1;

		// 1. 모든 확인점에 대해 탐색한다.
		for (int i = 0; i < (int)points.size(); i++) {
			int point = points[i];

			for (int j = 0; j < n; j++) {
				double dist = distance(cords[point].first, cords[point].second, cords[j].first, cords[j].second);

				// 2. 아직 방문하지 않았고 거리가 최소인 점을 체크한다.
				if (!visited[j] && min_dist > dist) {
					min_dist = dist;
					next = j;
				}
			}
		}
		// 3. 최소거리에 해당하는 점을 points에 push하고 방문여부, 최소합을 계산한다. 
		if (next != -1) {
			points.push_back(next);
			dist_sum += min_dist;
			visited[next] = true;
		}
	}
	return dist_sum;
}

int main() {
	cin >> n;

	cords.resize(n);
	visited.resize(n, false);

	for (int i = 0; i < n; i++)
		cin >> cords[i].first >> cords[i].second;

	printf("%.2lf", prim(0));
}
