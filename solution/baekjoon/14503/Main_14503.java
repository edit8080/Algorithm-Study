/**
 * 1. 현재 위치가 청소되어있지 않다면 청소한다.
 * 2. 현재 위치 기준 왼쪽 방향으로 돌면서 청소 여부를 탐색한다.
 *    -> 빈 영역이고 아직 청소가 안되었다면 이동
 * 3. 4방향이 모두 청소되었다면 바라보는 방향을 유지하면서 한 칸 후진
 *    -> 후진한 곳이 벽이라면 탐색을 종료한다.
 * 
 * 시간복잡도 - O(N*M) (N: 세로 크기 <= 50, M: 가로 크기 <= 50)
 */

import java.util.*;
import java.io.*;

public class Main_14503 {
	static int N, M; // 공간 크기
	static int robotX, robotY, robotDir; // 로봇 위치, 방향
	static int[][] room; // 공간 정보
	static boolean[][] cleaned; // 청소 여부
	static int cleanCnt = 0; // 청소한 칸 개수
	static int[][] dir = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } }; // 탐색 방향 (북, 동, 남, 서)

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 공간 크기 입력
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 로봇 정보 입력
		st = new StringTokenizer(br.readLine());
		robotX = Integer.parseInt(st.nextToken());
		robotY = Integer.parseInt(st.nextToken());
		robotDir = Integer.parseInt(st.nextToken());

		room = new int[N][M];

		// 내부 상태 입력 (바깥 영역은 모두 벽)
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++)
				room[i][j] = Integer.parseInt(st.nextToken());
		}

		// 방 청소 시작
		cleaned = new boolean[N][M];
		cleanRoom();

		System.out.println(cleanCnt);
		br.close();
	}

	// 방 청소 - O(N*M)
	public static void cleanRoom() {

		while (true) {
			boolean allClear = true;

			// 1. 현재 위치 청소 (이미 청소되어있는 칸을 청소하지 않음)
			if (!cleaned[robotX][robotY]) {
				cleanCnt++;
				cleaned[robotX][robotY] = true;
			}

			// 2. 현재 위치 기준 왼쪽 방향으로 돌면서 청소 여부 탐색
			for (int i = 0; i < 4; i++) {
				robotDir = (robotDir + dir[0].length - 1) % dir[0].length;
				
				int nextX = robotX + dir[0][robotDir];
				int nextY = robotY + dir[1][robotDir];

				// 빈 영역이고 아직 청소가 안되었다면 이동
				if (room[nextX][nextY] == 0 && !cleaned[nextX][nextY]) {
					robotX = nextX;
					robotY = nextY;
					allClear = false;
					break;
				}
			}

			// 3. 4방향이 모두 청소되었다면
			if (allClear) {
				// 바라보는 방향을 유지하면서 한 칸 후진
				robotX = robotX - dir[0][robotDir];
				robotY = robotY - dir[1][robotDir];

				// 만약 후진한 위치가 벽이라면 종료
				if (room[robotX][robotY] == 1)
					return;
			}		
		}
	}
}
