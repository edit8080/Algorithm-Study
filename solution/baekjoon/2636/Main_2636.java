/** 
 * 1. 정보를 입력받을 때 치즈 개수를 카운팅한다.
 * 2. 치즈가 남아있다면 탐색을 시작한다. 탐색하기 직전에 남아있는 치즈의 개수를 저장한다.
 * 3. 공기를 기준으로 DFS 탐색을 수행한다.
 *    - 다음 위치가 공기라면 해당 위치로 이동하여 탐색한다.
 *    - 다음 위치가 치즈라면 치즈를 녹이고 공기로 취급한다.
 * 
 * 시간복잡도 = O(N^2 * M^2) (N: 가로 길이 <= 100, M: 세로 길이 <= 100)
 */

package com.ssafy.baekjoon;

import java.util.*;
import java.io.*;

public class Main_2636 {
	static int N, M;
	static int[][] board;
	static int time, cheeseCnt, lastCheeseCnt;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new int[N][M];
		time = cheeseCnt = lastCheeseCnt = 0;

		// 보드 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());

				// 치즈 개수 카운팅
				if (board[i][j] == 1)
					cheeseCnt++;
			}
		}

		// 치즈가 남아있다면 탐색 시작 - O(N*M / 2)
		while (cheeseCnt > 0) {
			time++;
			// 직전에 남아있는 치즈 개수를 저장
			lastCheeseCnt = cheeseCnt;

			boolean[][] visited = new boolean[N][M];
			dfs(visited, 0, 0); // (0,0)은 무조건 공기편
		}
		System.out.println(time + "\n" + lastCheeseCnt);
		br.close();
	}

	// 공기를 기준으로 탐색 시작 - O(N*M)
	public static void dfs(boolean[][] visited, int x, int y) {
		visited[x][y] = true;

		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			if (inBoard(nextX, nextY) && !visited[nextX][nextY]) {
				// 공기 영역이면 다음 탐색 진행
				if (board[nextX][nextY] == 0)
					dfs(visited, nextX, nextY);

				// 치즈라면 녹임 -> 남아있는 치즈 개수를 줄이고 공기가 됨
				else if (board[nextX][nextY] == 1) {
					cheeseCnt--;
					board[nextX][nextY] = 0;
					visited[nextX][nextY] = true;
				}
			}
		}
	}

	// 보드판 내에 있는지 확인
	public static boolean inBoard(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}
}
