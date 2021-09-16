package com.ssafy.swea;

import java.util.*;
import java.io.*;

public class Solution_3307_nlogn {

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

			sb.append('#').append(testCnt).append(' ').append(LISCnt(num)).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 최장 증가 수열의 길이 구하기 - O(N log N)
	public static int LISCnt(int[] num) {
		int[] LIS = new int[num.length];

		int size = 0; // LIS에 채워지고 있는 원소 개수
		for (int i = 0; i < num.length; i++) {
			int pos = Math.abs(Arrays.binarySearch(LIS, 0, size, num[i])) - 1;
			
			// 1값으로 중복되는 경우에 대한 예외처리
			if(pos == -1)
				break;
			
			// 현재 위치에 값 삽입
			LIS[pos] = num[i];
			
			// 만약 끝에 삽입된 경우 size를 증가
			if(pos == size)
				size++;			
		}
		
		return size;
	}
}
