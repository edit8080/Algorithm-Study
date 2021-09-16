package com.ssafy.swea;

import java.util.*;
import java.io.*;

public class Solution_3307 {

	public static void main(String[] args) throws Exception {
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d3_3307.txt")));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			int N = Integer.parseInt(br.readLine());
			int[] num = new int[N];
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) 
				num[i] = Integer.parseInt(st.nextToken());			

			sb.append('#').append(testCnt).append(' ').append(LCSCnt(num)).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 최장 증가 수열의 길이 구하기 - O(N^2)
	public static int LCSCnt(int[] num) {
		int max = 0;
		int[] LCS = new int[num.length];

		for (int i = 0; i < num.length; i++) {
			LCS[i] = 1;
			
			for (int j = 0; j < i; j++) {
				if (num[i] > num[j])
					LCS[i] = Math.max(LCS[i], LCS[j] + 1);
			}
			max = Math.max(max, LCS[i]);
		}
		return max;
	}
}
