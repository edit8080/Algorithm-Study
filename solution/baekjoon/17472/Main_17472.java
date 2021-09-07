/**
 * 1. DFS로 각 영역을 표시한다. - O(N*M)
 * 2. 각 영역의 점에서 4방향으로 뻗어나가면서 탐색하여 다른 영역 간의 최소거리를 계산한다. - O(N*M*MAX(N,M)) 
 * 3. 구성한 거리배열을 통해 프림 알고리즘으로 최소 신장 트리를 구성해 모든 영역을 지나가는 최소 거리를 계산한다. - O(k^2) (k: 영역의 개수)
 * 
 * 시간복잡도 = O(N*M*MAX(N,M)) (N: 영역 행 길이 <= 10, M: 영역 열 길이 <=10) 
 */

import java.util.*;
import java.io.*;

public class Main_17472 {
	static int N, M; // 지도 행,열 길이
	static int[][] map; // 초기 지도 정보
	static int[][] area; // 영역 정보
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } }; // 4방향 탐색
	static int areaCnt = 0; // 영역 개수
	static int[][] areaDist; // 영역간 거리 배열

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		area = new int[N][M];

		// 각 영역 표시 - O(N*M) (N: 영역 행 길이 <= 10, M: 영역 열 길이 <=10)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++)
				if (map[i][j] != 0 && area[i][j] == 0) {
					areaCnt++;
					markArea(i, j);
				}
		}

		areaDist = new int[areaCnt + 1][areaCnt + 1];

		for (int i = 1; i <= areaCnt; i++) {
			for (int j = 1; j <= areaCnt; j++)
				areaDist[i][j] = Integer.MAX_VALUE;
		}

		// 영역 지점이면 4방향 탐색 - O(N*M*MAX(N,M))
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++)
				if (map[i][j] == 1) {
					for (int d = 0; d < 4; d++)
						distance(area[i][j], i, j, dir[0][d], dir[1][d], 0);
				}
		}

		// 프림 알고리즘으로 각 영역을 지나가는 최소 신장 트리 길이 계산
		System.out.print(prim(areaCnt, areaDist));
	}

	// DFS로 연결 영역 표시 - O(N^2)
	public static void markArea(int x, int y) {
		area[x][y] = areaCnt;

		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			if (inMap(nextX, nextY) && map[nextX][nextY] == 1 && area[nextX][nextY] == 0)
				markArea(nextX, nextY);
		}
	}

	// 영역에 속한 정점에서 다른 영역에 도달 가능한 최소 거리를 비교 - O(MAX(N,M)) (N : 행, 열 길이 중 긴 길이 <= 10)
	public static void distance(int start, int x, int y, int dirX, int dirY, int dist) {
		int nextX = x + dirX;
		int nextY = y + dirY;

		// 영역 내에서 탐색 진행
		if (!inMap(nextX, nextY))
			return;

		int currArea = area[nextX][nextY];

		// 영역 도달
		if (currArea != 0) {
			// 동일 영역, 거리 < 2 무시
			if (start == currArea || dist < 2)
				return;

			// 서로 다른 영역이면 최소 거리 갱신
			areaDist[start][currArea] = Math.min(areaDist[start][currArea], dist);
			return;
		}

		distance(start, nextX, nextY, dirX, dirY, dist + 1);
	}

	// 맵 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}

	// 프림 알고리즘으로 신장 트리 구성 - O(K^2) (K: 영역의 개수 <= 6)
	public static int prim(int n, int[][] dist) {
		int[] minEdge = new int[n + 1];
		boolean[] visited = new boolean[n + 1];
		Arrays.fill(minEdge, Integer.MAX_VALUE);

		int cost = 0;
		minEdge[1] = 0;

		for (int i = 0; i < n; i++) {
			int min = Integer.MAX_VALUE;
			int minVertex = -1;

			for (int j = 1; j <= n; j++) {
				// 최소 노드 검색
				if (!visited[j] && minEdge[j] < min) {
					min = minEdge[j];
					minVertex = j;
				}
			}
			// 신장 트리 구성 불가능
			if (minVertex == -1)
				return -1;

			visited[minVertex] = true;
			cost += min;

			for (int j = 1; j <= n; j++) {
				if (!visited[j] && dist[minVertex][j] != Integer.MAX_VALUE && minEdge[j] > dist[minVertex][j])
					minEdge[j] = dist[minVertex][j];
			}
		}

		return cost;
	}
}
