/**
 * 1. DFS로 4개가 이어진 조각을 놓을 수 있는지 확인한다.
 *    -> 확인 과정에서 시간 최적화를 위해 백트래킹을 적용한다.
 * 2. DFS로 판별할 수 있는 조각은 한붓 그리기가 가능한 ㅡ, L, ㅁ, ㄹ 만 가능하므로 ㅗ 모양에 대해 따로 예외처리를 수행한다.
 * 3. 위 탐색동안 찾은 최대합을 출력한다.
 * 
 */
package com.ssafy.baekjoon;

import java.util.*;
import java.io.*;

public class Main_14500 {
	static int N, M;
	static int[][] map;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
	static int maxSum = 0;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];

		// 지도 값 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		// 4개가 이어진 조각을 놓을 수 있는지 확인
		boolean[][] visited = new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				visited[i][j] = true;
				dfs(visited, i, j, 0, 0);
				visited[i][j] = false;
				
				uShape(i, j);
			}
		}
		System.out.println(maxSum);
	}

	// ㅣ, ㅡ, ㅁ, ㄹ 모양 판단
	public static void dfs(boolean[][] visited, int x, int y, int cnt, int sum) {
		// 4개의 조각을 놓을 수 있다면 최대합 계산
		if (cnt == 4) {
			maxSum = Math.max(maxSum, sum);
			return;
		}

		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			if (inMap(nextX, nextY) && !visited[nextX][nextY]) {
				visited[nextX][nextY] = true;
				dfs(visited, nextX, nextY, cnt + 1, sum + map[x][y]);
				visited[nextX][nextY] = false;
			}
		}
	}

	// ㅗ 모양 판단
	public static void uShape(int x, int y) {
		int sum = map[x][y];

		// 4방향 합 계산
		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			sum += inMap(nextX, nextY) ? map[nextX][nextY] : 0;
		}

		// 4방향 중 하나 제거
		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			if (inMap(nextX, nextY))
				maxSum = Math.max(maxSum, sum - map[nextX][nextY]);
			else
				maxSum = Math.max(maxSum, sum);
		}
	}

	// 지도 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}

}
