/*
* 1. �� �����͸� ���� �Ƿε� ������ �����Ѵ�.
* 2. ������ ������ �������� BFS Ž���� �������� �� Ž�� ������ ���� ������ Ž���Ѵ�.
* 3. ��� ���� Ž���� �� �ִٸ� low ������ �ø��鼭 �ȵǴ� ��찡 �� ������ Ž���Ѵ�.
* 4. ���� ��� ���� Ž���� �� ���ٸ� high ������ �÷����� ��� ���� Ž���� �� �ִ� ��츦 Ž���Ѵ�.
*
* ����) ��ü���� ��ġ(���� ��ġ)�� �Ƿε� �������� �����ؾ��Ѵ�.
*
* �ð����⵵ = O(n^3) (n : �������͸� ���� ���� Ž��, n^2 : BFS Ž��)
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int n;
int direction[2][8] = { {-1,1,0,0,-1,-1,1,1},{0,0,-1,1,-1,1,-1,1,} };
vector<string> village;
vector<vector<int>> weight;
vector<vector<bool>> visited;

bool in_bound(int x, int y) {
	return (x >= 0 && x < n && y >= 0 && y < n) ? true : false;
}

int bfs(int start_x, int start_y, int low, int high) {
	int cnt = 0;
	vector<vector<bool>> visited(n, vector<bool>(n, false));
	queue<pair<int, int>> house;

	house.push({ start_x, start_y });
	visited[start_x][start_y] = true;

	while (!house.empty()) {
		int x = house.front().first;
		int y = house.front().second;
		house.pop();

		// ���� ã���� Ž���� ���� ����
		if (village[x][y] == 'K')
			cnt += 1;

		// ���� ��� �̵� (������ �̵�, �湮����, �Ƿε� ���� Ȯ��)
		for (int i = 0; i < 8; i++) {
			int next_x = x + direction[0][i];
			int next_y = y + direction[1][i];

			if (in_bound(next_x, next_y) && !visited[next_x][next_y] && weight[next_x][next_y] >= low && weight[next_x][next_y] <= high) {
				visited[next_x][next_y] = true;
				house.push({ next_x, next_y });
			}
		}
	}
	return cnt;
}


int main() {
	cin >> n;
	int cnt_house = 0;
	int post_x, post_y;
	vector<int> weights;

	village.resize(n);
	weight.resize(n, vector<int>(n));

	for (int i = 0; i < n; i++)
		cin >> village[i];

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> weight[i][j];
			weights.push_back(weight[i][j]);
		}
	}

	// K, P ��ġ Ž��
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (village[i][j] == 'K')
				cnt_house += 1;

			else if (village[i][j] == 'P') {
				post_x = i;
				post_y = j;
			}
		}
	}

	// �������� �غ�
	sort(weights.begin(), weights.end());
	int low_idx = 0;
	int high_idx = 0;
	int min_diff = weights.back() - weights.front();

	// �Ƿε� ���� ����
	while (low_idx < weights.size() && high_idx < weights.size() && low_idx <= high_idx) {
		int low = weights[low_idx];
		int high = weights[high_idx];
		int post_weight = weight[post_x][post_y];

		// �ش� �Ƿε� �������� ���� ���� ã�� �� �ִٸ� üũ
		if ((post_weight >= low && post_weight <= high) && bfs(post_x, post_y, low, high) == cnt_house) {
			min_diff = min(min_diff, high - low);
			// ���� ã�� �� �ִٸ� low�� �÷����鼭 �������� üũ
			low_idx += 1;
		}
		// ���� ã�� �� ���ٸ� high�� �÷����鼭 �������� üũ
		else
			high_idx += 1;
	}
	cout << min_diff;
}
