/**
 * 1. 1 ~ 6번 배열 연산 명령을 구현한다.
 * 2. 입력받은 연산 명령대로 r번 순차적으로 연산한다.
 * 
 * 시간복잡도 = O(n*m*r) (n*m: 배열 크기, r: 연산 횟수)
 */

import java.io.*;
import java.util.*;

public class Main_16935 {
	static int n, m;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());

		// 입력 및 초기화
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());

		int[][] arr = new int[n][m];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++)
				arr[i][j] = Integer.parseInt(st.nextToken());
		}

		// 명령 입력
		st = new StringTokenizer(br.readLine());
		int[] oper = new int[st.countTokens()];

		for (int i = 0; i < oper.length; i++)
			oper[i] = Integer.parseInt(st.nextToken());

		// 명령 처리
		int cnt = 0, tmp;
		
		for (int j = 0; j < oper.length && cnt < r; j++) {
			switch (oper[j]) {
			case 1:
				// arr - 2
				arr = oper1(arr, n, m);
				break;
			case 2:
				// arr - 2
				arr = oper2(arr, n, m);
				break;
			case 3:
				arr = oper3(arr, n, m);
				tmp = n;
				n = m;
				m = tmp;
				break;
			case 4:
				arr = oper4(arr, n, m);
				tmp = n;
				n = m;
				m = tmp;
				break;
			case 5:
				arr = oper5(arr, n, m);
				break;
			case 6:
				arr = oper6(arr, n, m);
				break;
			}
			cnt++;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				sb.append(arr[i][j]).append(" ");
			sb.append("\n");
		}
		System.out.println(sb);
		br.close();
	}

	// 1번 연산 - 상하 반전
	public static int[][] oper1(int[][] arr, int n, int m) {
		int[][] ret = new int[n][m]; // ret - 2

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				ret[(n - 1) - i][j] = arr[i][j];

		return ret;
	}

	// 2번 연산 - 좌우 반전
	public static int[][] oper2(int[][] arr, int n, int m) {
		int[][] ret = new int[n][m]; // ret - 3

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				ret[i][(m - 1) - j] = arr[i][j];

		return ret;
	}

	// 3번 연산 - 오른쪽으로 90도 회전
	public static int[][] oper3(int[][] arr, int n, int m) {
		int[][] ret = new int[m][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				ret[j][(n - 1) - i] = arr[i][j];

		return ret;
	}

	// 4번 연산 - 왼쪽으로 90도 회전
	public static int[][] oper4(int[][] arr, int n, int m) {
		int[][] ret = new int[m][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				ret[(m - 1) - j][i] = arr[i][j];
		
		return ret;
	}

	// 5번 연산 - n/2 * m/2 의 부분배열을 시계방향으로 회전
	public static int[][] oper5(int[][] arr, int n, int m) {
		int[][] ret = new int[n][m];
		
		for (int i = 0; i < n / 2; i++) {
			// 4사분면
			for (int j = 0; j < m / 2; j++)
				ret[i][j + m / 2] = arr[i][j];

			// 1사분면
			for (int j = m / 2; j < m; j++)
				ret[i + n / 2][j] = arr[i][j];
		}
		for (int i = n / 2; i < n; i++) {
			// 3사분면
			for (int j = 0; j < m / 2; j++)
				ret[i - n / 2][j] = arr[i][j];

			// 2사분면
			for (int j = m / 2; j < m; j++)
				ret[i][j - m / 2] = arr[i][j];
		}

		return ret;
	}

	// 6번 연산 - n/2 * m/2 의 부분배열을 반시계방향으로 회전
	public static int[][] oper6(int[][] arr, int n, int m) {
		int[][] ret = new int[n][m];					
		
		for (int i = 0; i < n / 2; i++) {			
			// 4사분면
			for (int j = 0; j < m / 2; j++)
				ret[i + n / 2][j] = arr[i][j];

			// 1사분면
			for (int j = m / 2; j < m; j++)
				ret[i][j - m / 2] = arr[i][j];
		}
		for (int i = n / 2; i < n; i++) {
			// 3사분면
			for (int j = 0; j < m / 2; j++)
				ret[i][j + m / 2] = arr[i][j];

			// 2사분면
			for (int j = m / 2; j < m; j++)
				ret[i - n / 2][j] = arr[i][j];
		}

		return ret;
	}
}
