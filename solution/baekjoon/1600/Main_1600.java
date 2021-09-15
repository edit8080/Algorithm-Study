/**
 * 1. 입력받은 지도 정보를 기반해 인접해서 이동하는 것과 말처럼 이동하는 것을 BFS를 통해 탐색한다. -> 최소 횟수
 * 2. BFS를 탐색할 때는 말모양 이동 횟수(K)에 기반해 방문 여부를 탐색한다.
 * 3. 탐색중 목적지에 도달했을 때, 그에 따른 최소 횟수를 반환한다.
 * 
 * 주의) - 무조건 최소로 이동하는 것이 최적해가 아님 -> 인접으로 이동했다가 점프하는 경우가 존재
 *       - 3차원 visited 배열을 사용해야함 -> K에 따라 최소 횟수 구하기
 * 
 * 0 0 0 0
 * 0 0 0 1
 * 0 0 1 0
 * 
 * 시간복잡도 = O(H*W*K) (H : 세로 길이 <= 200, W : 가로 길이 <= 200, K : 말모양 이동 횟수 <= 30)
 */

package com.ssafy.baekjoon;

import java.io.*;
import java.util.*;

public class Main_1600 {
	static int W, H; // 가로, 세로 길이
	static int[][] map; // 지도 정보
	static boolean[][][] visited; // 방문 여부
	static int[][] adjDir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } }; // 원숭이가 이동하는 방향
	static int[][] horseDir = { { -2, -2, -1, -1, 1, 1, 2, 2 }, { -1, 1, -2, 2, -2, 2, -1, 1 } }; // 말이 이동하는 방향

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int K = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		map = new int[H][W];
		visited = new boolean[H][W][K + 1];

		// 지도 정보 입력
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < W; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}
		System.out.println(bfs(0, 0, K));
		br.close();
	}

	// 최소 횟수를 판단하기 위해 인접해서 이동하는 것과 말처럼 이동하는 것을 BFS를 통해 탐색 -> O(H*W*K)
	public static int bfs(int startX, int startY, int k) {
		Queue<int[]> q = new LinkedList<>();

		visited[startX][startY][k] = true;
		q.offer(new int[] { startX, startY, 0, k });

		while (!q.isEmpty()) {
			int x = q.peek()[0];
			int y = q.peek()[1];
			int currCnt = q.peek()[2];
			int kCnt = q.poll()[3];

			// 가장 처음으로 도착점에 도달하는 경우
			if (x == H - 1 && y == W - 1)
				return currCnt;

			// 인접 이동
			for (int i = 0; i < 4; i++) {
				int nextX = x + adjDir[0][i];
				int nextY = y + adjDir[1][i];

				if (inMap(nextX, nextY) && map[nextX][nextY] == 0 && !visited[nextX][nextY][kCnt]) {
					visited[nextX][nextY][kCnt] = true;
					q.offer(new int[] { nextX, nextY, currCnt + 1, kCnt });
				}
			}
			// 말 모양 이동
			if (kCnt > 0) {
				for (int i = 0; i < 8; i++) {
					int nextX = x + horseDir[0][i];
					int nextY = y + horseDir[1][i];

					if (inMap(nextX, nextY) && map[nextX][nextY] == 0 && !visited[nextX][nextY][kCnt - 1]) {
						visited[nextX][nextY][kCnt - 1] = true;
						q.offer(new int[] { nextX, nextY, currCnt + 1, kCnt - 1 });
					}
				}
			}
		}
		return -1;
	}

	// 지도 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < H && y >= 0 && y < W);
	}
}
