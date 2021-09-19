/**
 * 1. 사무실 정보를 입력받을 때 CCTV의 위치 정보를 따로 저장한다.
 * 2. 각 CCTV를 회전해나가면서 모든 경우에 대한 탐색을 시작한다. - O(4^C)
 * 3. 모든 CCTV를 회전시켰다면 남아있는 사각지대 영역의 최소를 비교한다.
 * 
 * 주의) 감시 영역을 나타내는 checked 배열을 초기화할 때 boolean이 아닌 int로 사용하는 것에 유의한다.
 *       -> boolean으로 해제하면 중복된 영역을 모두 해제하는 문제가 있음 
 * 
 * 시간복잡도 = O(4^C) (C : CCTV 개수 <= 8)
 */
package baekjoon;

import java.io.*;
import java.util.*;

public class Main_15683 {
	static int N, M;
	static int[][] office; // 사무실 정보
	static List<int[]> cctvs; // CCTV 위치 정보
	static int[][][] dir = { { { 0, 1 } }, // 1번 감시 방향
			{ { 0, -1 }, { 0, 1 } }, // 2번 감시 방향
			{ { -1, 0 }, { 0, 1 } }, // 3번 감시 방향
			{ { 0, -1 }, { -1, 0 }, { 0, 1 } }, // 4번 감시 방향
			{ { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } } // 5번 감시 방향
	};
	static int[][] checked; // 감시 여부
	static int minArea; // 최소 사각 영역 개수

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		office = new int[N][M];
		cctvs = new ArrayList<>();

		// 사무실 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < M; j++) {
				office[i][j] = Integer.parseInt(st.nextToken());

				// CCTV 위치 정보 저장
				if (office[i][j] >= 1 && office[i][j] <= 5)
					cctvs.add(new int[] { i, j });
			}
		}

		checked = new int[N][M];
		minArea = Integer.MAX_VALUE;
		// 감시 시작
		overwatch(0);
		
		System.out.println(minArea);
		br.close();
	}

	// 4방향 회전에 대해 감시 시작 - O(4^C) (C : CCTV의 개수)
	public static void overwatch(int cctvCnt) {
		// 모든 CCTV 확인 완료
		if (cctvCnt == cctvs.size()) {
			int area = 0;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (office[i][j] == 0 && checked[i][j] == 0)
						area++;
				}
			}
			minArea = Math.min(minArea, area);
			return;
		}

		// 현재 CCTV 위치
		int cctvX = cctvs.get(cctvCnt)[0];
		int cctvY = cctvs.get(cctvCnt)[1];

		// 4방향 회전
		for (int r = 0; r < 4; r++) {
			int[][] currDir = dir[office[cctvX][cctvY] - 1];

			for (int d = 0; d < currDir.length; d++) {
				// 회전된 방향 구하기
				int[] rotateDir = rotate(currDir[d][0], currDir[d][1], r);

				// 감시 지역 체크
				checkOffice(cctvX, cctvY, rotateDir[0], rotateDir[1], 1);
			}
			overwatch(cctvCnt + 1);

			for (int d = 0; d < currDir.length; d++) {
				// 회전된 방향 구하기
				int[] rotateDir = rotate(currDir[d][0], currDir[d][1], r);

				// 감시 지역 체크 해제
				checkOffice(cctvX, cctvY, rotateDir[0], rotateDir[1], -1);
			}
		}
	}

	// 감시 방향 회전
	public static int[] rotate(int dirX, int dirY, int r) {
		switch (r) {
		case 0:
			return new int[] { dirX, dirY };
		case 1:
			return new int[] { dirY, -dirX };
		case 2:
			return new int[] { -dirX, -dirY };
		case 3:
			return new int[] { -dirY, dirX };
		}
		return null;
	}

	// 주어진 방향으로 감시 체크·해제
	public static void checkOffice(int cctvX, int cctvY, int dirX, int dirY, int state) {
		while (true) {
			int nextX = cctvX + dirX;
			int nextY = cctvY + dirY;

			// 사무실을 벗어나거나 벽을 만나면 탐색 종료
			if (!inOffice(nextX, nextY) || office[nextX][nextY] == 6)
				break;

			checked[nextX][nextY] += state;
			cctvX = nextX;
			cctvY = nextY;
		}
	}

	// 사무실 내에 있는지 확인
	public static boolean inOffice(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}
}
