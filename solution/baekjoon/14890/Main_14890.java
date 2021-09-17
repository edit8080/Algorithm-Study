/**
 * 1. 0 지점에서 행과 열 방향으로 탐색을 시작한다.
 * 2. 동일한 높이라면 이동한다.
 * 3. 내려가는 경사로를 놓아야한다면 다음 조건을 확인한다. 경사로를 놓을 수 있다면 경사로 배치 여부를 따로 표기한다.
 *    - 경사로를 놓는 위치가 지도 내에 있는가
	  - 경사로를 놓는 높이가 평평한가
	  
 * 3. 올라가는 경사로를 놓아야한다면 다음 조건을 확인한다.
 *    - 경사로를 놓는 위치가 지도 내에 있는가
 *    - 이미 내리막 경사로를 놓았는가
 *    - 경사로를 놓는 높이가 평평한가 
 *    
 * 4. 위 탐색을 진행하면서 마지막 위치까지 도달하게 되는 경우의 수를 카운팅한다.
 * 
 * 시간복잡도 = O(N^2) (N : 지도의 크기)
 */

import java.util.*;
import java.io.*;

public class Main_14890 {
	static int N, L; // 지도의 크기, 경사로의 길이
	static int[][] map; // 지도
	static int roadCnt = 0; // 길의 개수

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		boolean[] isSlope; // 내리막 경사로 배치 여부
		for (int i = 0; i < N; i++) {
			isSlope = new boolean[N];
			roadRowCheck(isSlope, i, 0); // 행 기준 시작

			isSlope = new boolean[N];
			roadColCheck(isSlope, 0, i); // 열 기준 시작
		}
		System.out.println(roadCnt);
		br.close();
	}

	public static void roadRowCheck(boolean[] isSlope, int x, int y) {
		// 정상적으로 도착
		if (y == N - 1) {
			roadCnt++;
			return;
		}
		
		// 동일한 높이
		if (inMap(x, y + 1) && map[x][y] == map[x][y + 1]) {
			roadRowCheck(isSlope, x, y + 1);
			return;
		}

		// 내려가는 경사로
		if (inMap(x, y + 1) && map[x][y] - 1 == map[x][y + 1]) {
			int desc = map[x][y + 1];			

			// 1. 지도 내에 있는가
			// 2. 경사로를 놓는 높이가 평평한가
			for (int i = 1; i <= L; i++) {
				if (!inMap(x, y + i) || desc != map[x][y + i])
					return;
			}

			// 경사로 설정
			for (int i = 1; i <= L; i++)
				isSlope[y + i] = true;

			roadRowCheck(isSlope, x, y + L);
			return;
		}

		// 올라가는 경사로
		if (inMap(x, y + 1) && map[x][y] + 1 == map[x][y + 1]) {
			int asc = map[x][y];
			
			// 1. 지도 내에 있는가
			// 2. 이미 경사로를 놓았는가
			// 3. 경사로 높이가 평평한가
			for (int i = 0; i < L; i++) {
				if (!inMap(x, y - i) || isSlope[y - i] || asc != map[x][y - i])
					return;
			}
			roadRowCheck(isSlope, x, y + 1);
			return;
		}
	}

	public static void roadColCheck(boolean[] isSlope, int x, int y) {
		// 정상적으로 도착
		if (x == N - 1) {
			roadCnt++;
			return;
		}

		// 동일한 높이
		if (inMap(x + 1, y) && map[x][y] == map[x + 1][y]) {
			roadColCheck(isSlope, x + 1, y);
			return;
		}

		// 내려가는 경사로
		if (inMap(x + 1, y) && map[x][y] - 1 == map[x + 1][y]) {
			int desc = map[x + 1][y];

			// 1. 지도 내에 있는가
			// 2. 경사로를 놓는 높이가 평평한가
			for (int i = 1; i <= L; i++) {
				if (!inMap(x + i, y) || desc != map[x + i][y])
					return;
			}

			// 경사로 설정
			for (int i = 1; i <= L; i++)
				isSlope[x + i] = true;

			roadColCheck(isSlope, x + L, y);
			return;
		}

		// 올라가는 경사로
		if (inMap(x + 1, y) && map[x][y] + 1 == map[x + 1][y]) {
			int asc = map[x][y];

			// 1. 지도 내에 있는가
			// 2. 이미 경사로가 놓여져 있는가
			// 3. 경사로 높이가 평평한가
			for (int i = 0; i < L; i++) {
				if (!inMap(x - i, y) || isSlope[x - i] || asc != map[x - i][y])
					return;
			}
			roadColCheck(isSlope, x + 1, y);
			return;
		}
	}

	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}

}
