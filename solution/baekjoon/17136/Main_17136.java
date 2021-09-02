/**
 * 1. 값이 1이고 이미 확인하지 않은 노드라면 탐색을 시작한다.
 * 2. 해당 위치에서 1x1 ~ 5x5 크기의 색종이를 덮을 수 있는지 확인한다. -> 백트래킹 최적화
 *    최적화 사항) 전체 색종이를 벗어남/해당 크기의 색종이로 덮을 수 없음/이미 확인함/ 해당 색종이 사용 개수 <= 5  
 *    
 * 3. 만약 덮을 수 있다면 해당 크기의 색종이로 덮고난 이후의 상태를 토대로 다음 탐색을 진행한다. 
 * 4. 1을 전부 덮었다면, 덮었을 때 사용한 모든 색종이의 개수 중 최소를 비교한다.
 * 
 */
package com.ssafy.baekjoon;

import java.io.*;
import java.util.*;

public class Main_17136 {
	final static int LEN = 10;
	static int[][] board = new int[LEN][LEN];
	static int minCnt = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int oneCnt = 0;

		// 전체 종이 정보 입력
		for (int i = 0; i < LEN; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int j = 0; j < LEN; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());

				// 덮을 색종이 면적 확인
				if (board[i][j] == 1)
					oneCnt++;
			}
		}
		cover(new boolean[LEN][LEN], new int[5], 0, oneCnt);

		System.out.print(minCnt == Integer.MAX_VALUE ? -1 : minCnt);
		br.close();
	}

	public static void cover(boolean[][] visited, int[] cnt, int pos, int oneCnt) {
		// 전부 다 덮었다면 최소 비교
		if (oneCnt == 0) {
			int checkCnt = 0;
			for (int i = 0; i < 5; i++)
				checkCnt += cnt[i];
			
			minCnt = Math.min(minCnt, checkCnt);
			return;
		}

		int x = pos / LEN;
		int y = pos % LEN;

		if (board[x][y] == 1 && !visited[x][y]) {
			// 덮을 색종이 길이
			for (int i = 0; i <= 4; i++) {
				// 1x1 ~ 5x5 크기의 색종이로 덮어보기
				for (int checkX = x; checkX <= x + i; checkX++) {
					for (int checkY = y; checkY <= y + i; checkY++) {
						// 불가능하면 종료
						// -> 전체 색종이를 벗어남, 체크할 전체 원소 중 1이 아닌 원소가 있음, 이미 체크함
						if (checkX >= LEN || checkY >= LEN || board[checkX][checkY] != 1 || visited[checkX][checkY])
							return;
					}
				}

				// 덮을 수 있고 색종이 개수가 충분하다면 다음 탐색 시작
				if (cnt[i] < 5) {
					// visited와 cnt 배열을 deep copy
					boolean[][] visitedCopy = new boolean[LEN][LEN];
					for (int idx = 0; idx < LEN; idx++)
						visitedCopy[idx] = Arrays.copyOf(visited[idx], LEN);

					int[] cntCopy = Arrays.copyOf(cnt, 5);

					// 색종이를 덮고 체크 표시
					cntCopy[i]++;
					for (int checkX = x; checkX <= x + i; checkX++)
						for (int checkY = y; checkY <= y + i; checkY++)
							visitedCopy[checkX][checkY] = true;

					// 새로운 상태인 visited와 cnt 복사본을 토대로 다음 위치에 대해 탐색 시작
					cover(visitedCopy, cntCopy, pos + 1, oneCnt - ((i + 1) * (i + 1)));
				}
			}
		} else
			cover(visited, cnt, pos + 1, oneCnt);
	}
}
