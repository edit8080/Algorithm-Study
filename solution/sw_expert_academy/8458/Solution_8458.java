/**
 * 1. 각 점 위치 입력받는다.
 * 2. 모든 점들의 거리가 홀수인지 짝수로 동일한지 확인
 *    -> 만약 다르다면 절대 모든 점이 원점에 도달할 수 없음
 * 3. 위 조건을 만족한다면 최대 거리를 기준으로 최소 이동 횟수를 계산한다.
 *    -> 최대 거리 내에 있는 점들은 먼저 도달해서 진동하고 있음
 * 4. 최소 이동 횟수를 계산하기 위해서는 다음 식을 따른다.
 *    - 1부터 i까지의 합을 계속 계산한다.
 *    - 합이 최대 거리를 벗어나고 2번과 마찬가지로 홀수 or 짝수로 동일해야한다. 
 *
 * 주의) 합과 최대 거리의 홀수, 짝수가 동일해야하는 점을 유의한다. 
 * 
 * 시간복잡도 = O(N + sqrt(maxDist)) (N : 점의 개수 <= 10, maxDist : 최대 거리 <= 2*10^9)
 */

package com.ssafy.swea;

import java.util.*;
import java.io.*;

public class Solution_d4_8458_원점으로집합_서울_12반_이태희 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_8458.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= T; testCnt++) {
			int N = Integer.parseInt(br.readLine());

			int[][] points = new int[2][N];

			// 각 점 위치 입력
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				points[0][i] = Integer.parseInt(st.nextToken());
				points[1][i] = Integer.parseInt(st.nextToken());
			}

			int move;
			if (!valid(N, points))
				move = -1;
			else {
				// 최대 거리 계산
				int maxDist = 0;
				for (int i = 0; i < N; i++)
					maxDist = Math.max(maxDist, dist(points[0][i], points[1][i]));

				// 최소 횟수 탐색
				move = minMove(maxDist);
			}
			sb.append('#').append(testCnt).append(' ').append(move).append('\n');
		}

		System.out.println(sb);
		br.close();
	}
	
	// 최소 이동 횟수
	public static int minMove(int dist) {
		int sum = 0;
		int cnt = 0;
		
		// 합이 최대 거리를 벗어나고 합과 최대거리의 홀수 or 짝수여부가 동일해야한다. 
		while(!(sum >= dist && (dist-sum) % 2 == 0))
			sum += ++cnt;
		
		return cnt;
	}

	// 점의 유효성 확인 - 전체 점들의 거리가 홀수 or 짝수로 일치해야함
	public static boolean valid(int N, int[][] points) {
		int check = dist(points[0][0], points[1][0]) % 2;

		for (int i = 1; i < N; i++) {
			if (dist(points[0][i], points[1][i]) % 2 != check)
				return false;
		}
		return true;
	}

	public static int dist(int x, int y) {
		return Math.abs(x) + Math.abs(y);
	}

}
