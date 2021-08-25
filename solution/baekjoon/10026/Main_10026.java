/**
 * 1. 일반인은 주어진 구역 정보를 통해 색상 구역을 계산한다. 
 * 2. 적록색약인은 녹색을 빨간색과 동일하게 바라보기 때문에 구역 정보를 녹색을 빨간색으로 변경한다.
 * 3. 색상 구역을 계산할 때는 방문 여부와 DFS를 통해 계산한다.
 * 
 * 시간복잡도 = O(N^2) (N : 구역의 크기 <= 100)
 * 
 */

import java.io.*;

public class Main_10026 {
	static int N;
	static char[][] area;
	static boolean[][] visited;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		area = new char[N][N];

		for (int i = 0; i < N; i++)
			area[i] = br.readLine().toCharArray();

		// 1. 정상인
		int seeCnt = seeColor();

		// 2. 적록색약 - 녹색을 빨간색과 동일하게 바라봄
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (area[i][j] == 'G')
					area[i][j] = 'R';
			}
		}

		int cantSeeCnt = seeColor();

		System.out.printf("%d %d", seeCnt, cantSeeCnt);
		br.close();
	}

	// 색상 구역 카운팅 - O(N^2) () (※ DFS와 수행시간이 반비례한다)
	public static int seeColor() {
		int cnt = 0;

		visited = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) {
					cnt++;
					dfs(i, j, area[i][j]);
				}
			}
		}
		return cnt;
	}

	// 색상 구역 판별하기 - O(N^2)
	public static void dfs(int x, int y, char color) {
		visited[x][y] = true;

		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			if (inArea(nextX, nextY) && !visited[nextX][nextY] && area[nextX][nextY] == color)
				dfs(nextX, nextY, color);
		}
	}

	// 탐색 지점이 구역 내에 있는지 확인
	public static boolean inArea(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
