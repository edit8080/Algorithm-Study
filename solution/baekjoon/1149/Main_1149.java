/**
 * 1. 처음 집을 칠한다면 기저조건을 초기화한다.
 * 2. 현재 칠하려는 집의 색과 이전에 칠한 집의 색깔이 다른 비용합 상황 중 최소합을 선택한다.
 * 3. 마지막 집까지 칠했다면 세 경우 중 최소합을 출력한다.
 * 
 * 시간복잡도 = O(n) (n : 집의 수 <= 1000)
 */

import java.io.*;
import java.util.*;

public class Main_1149 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int COLOR_CNT = 3;
		int[] color = new int[COLOR_CNT];

		int n = Integer.parseInt(br.readLine());
		int[][] sum = new int[n][COLOR_CNT];

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			// 현재 집의 색깔 비용
			for (int c = 0; c < COLOR_CNT; c++)
				color[c] = Integer.parseInt(st.nextToken());

			// 기저 조건 설정
			if (i == 0) {
				for (int j = 0; j < COLOR_CNT; j++)
					sum[0][j] = color[j];
			}
			// 페인트 교차 선택 (현재 집을 0번을 칠했다면 이전 집은 1또는 2번 페인트를 사용했었어야함)
			else {
				sum[i][0] = color[0] + Math.min(sum[i-1][1], sum[i-1][2]);
				sum[i][1] = color[1] + Math.min(sum[i-1][0], sum[i-1][2]);
				sum[i][2] = color[2] + Math.min(sum[i-1][0], sum[i-1][1]);
			}
		}

		// 색깔을 칠한 경우 중 최소 합을 구함
		int minCost = Math.min(sum[n - 1][0], Math.min(sum[n - 1][1], sum[n - 1][2]));
		System.out.println(minCost);
		br.close();
	}
}
