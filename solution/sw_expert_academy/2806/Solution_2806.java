package com.ssafy.swea;

import java.io.*;

public class Solution_2806 {
	static int n;
	static int[] col;
	static int cnt;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= t; testCase++) {
			n = Integer.parseInt(br.readLine());
			col = new int[n];
			cnt = 0;

			setNQueen(0);

			sb.append('#').append(testCase).append(' ').append(cnt).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 퀸 배치하기
	public static void setNQueen(int rowNo) {
		if (rowNo >= n) {
			cnt++;
			return;
		}
		
		for (int i = 0; i < n; i++) {
			col[rowNo] = i;
			
			if (isAvailable(rowNo))
				setNQueen(rowNo + 1);
		}
	}
 
	// 퀸 배치 유효성 검사
	public static boolean isAvailable(int rowNo) {
		// 이전 퀸의 유효성 체크
		for (int i = 0; i < rowNo; i++) {
			// 열 체크
			if (col[rowNo] == col[i])
				return false;

			// 대각선 체크
			if (Math.abs(i - rowNo) == Math.abs(col[i] - col[rowNo]))
				return false;
		}
		return true;
	}
}
