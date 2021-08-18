package com.ssafy.swea;

import java.io.*;
import java.util.*;

public class Solution_4012 {
	static int n;
	static int[][] synergy;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_모의_4012.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= t; testCase++) {
			n = Integer.parseInt(br.readLine());
			synergy = new int[n][n];

			// 시너지 정보 입력
			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					synergy[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// 재료 절반 나누기
			int[] selected = new int[n];
			for (int i = 0; i < n / 2; i++)
				selected[(n - 1) - i] = 1;

			// 모든 재료 조합에 대해 최소 시너지 차이 구하기 - O(n^4)
			int minDiff = Integer.MAX_VALUE;
			do {
				// A와 B 음식의 시너지 - O(n^2)
				int aSynergy = synergySum(selected, 0);
				int bSynergy = synergySum(selected, 1);

				// 최소 시너지 차이 계산
				minDiff = Math.min(minDiff, Math.abs(aSynergy - bSynergy));

			} while (nextPermutation(selected)); // O(n^2)

			sb.append('#').append(testCase).append(' ').append(minDiff).append('\n');
		}
		System.out.println(sb);
		br.close();
	}
	
	// 재료의 시너지 합 계산 - O(n^2)
	public static int synergySum(int[] selected, int s) {
		int sum = 0;

		for (int i = 0; i < n; i++) {
			if (selected[i] == s) {
				for (int j = 0; j < n; j++) {
					if (selected[j] == s)
						sum += synergy[i][j];
				}
			}
		}
		return sum;
	}

	// 다음 순열 생성 - O(n^2)
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
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
