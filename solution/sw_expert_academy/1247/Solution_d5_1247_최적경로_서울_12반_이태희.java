/**
 * 1. 순열을 통해 방문할 고객 순서를 지정한다. - O(n!)
 * 2. 회사 -> 첫번째 고객, 고객 간 이동, 마지막 고객 -> 집까지의 이동 거리를 계산한다. - O(n) 
 * 3. 2에서 구한 이동 거리중 최소 거리를 출력한다.
 * 
 * 시간복잡도 - O(n! * n)
 */

package com.ssafy.swea;

import java.util.*;
import java.io.*;

public class Solution_d5_1247_최적경로_서울_12반_이태희 {
	static int compX, compY;
	static int homeX, homeY;
	static int[][] customers;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d5_1247.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			int n = Integer.parseInt(br.readLine());
			customers = new int[2][n];

			StringTokenizer st = new StringTokenizer(br.readLine());

			compX = Integer.parseInt(st.nextToken());
			compY = Integer.parseInt(st.nextToken());

			homeX = Integer.parseInt(st.nextToken());
			homeY = Integer.parseInt(st.nextToken());

			for (int i = 0; i < n; i++) {
				customers[0][i] = Integer.parseInt(st.nextToken());
				customers[1][i] = Integer.parseInt(st.nextToken());
			}

			int[] visitOrder = new int[n];
			for (int i = 0; i < n; i++)
				visitOrder[i] = i;

			// 순열을 통해 방문할 고객 순서 지정 - O(n! * n)
			int minDist = Integer.MAX_VALUE;

			do {
				int dist = 0;

				// 회사 -> 첫번째 고객
				dist += distance(compX, compY, customers[0][visitOrder[0]], customers[1][visitOrder[0]]);
				
				// 나머지 고객 방문 - O(n)
				for (int i = 0; i < n - 1; i++) 
					dist += distance(customers[0][visitOrder[i]], customers[1][visitOrder[i]], customers[0][visitOrder[i + 1]], customers[1][visitOrder[i + 1]]);
				
				// 마지막 고객 -> 집
				dist += distance(customers[0][visitOrder[n-1]], customers[1][visitOrder[n-1]], homeX, homeY);
				
				// 최소 거리 비교
				minDist = Math.min(minDist, dist);				
			} while (nextPermutation(visitOrder)); // O(n!)

			sb.append("#").append(testCnt).append(' ').append(minDist).append('\n');
		}
		System.out.println(sb);
		br.close();
	}
	
	// 다음 순열 지정 - O(n)
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		
		int i = n - 1;
		while (i > 0 && arr[i - 1] >= arr[i])
			i--;

		if (i == 0)
			return false;

		int j = n - 1;
		while (arr[i - 1] >= arr[j])
			j--;

		swap(arr, i - 1, j);

		int k = n - 1;
		while (i < k)
			swap(arr, i++, k--);

		return true;
	}
 
	// 배열 원소 교환
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 거리 계산
	public static int distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

}
