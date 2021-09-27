/**
 * 1. 방 정보를 입력받을 때 공기 청정기 위치를 따로 저장한다.
 * 2. 방에 있는 미세먼지를 주어진 과정에 따라 확산시킨다.
 * 3. 이후 공기청정기의 상하부에 따른 방향으로 공기를 순환시킨다.
 * 4. T초 시간동안 2~3 과정을 반복한 후 마지막 남은 미세먼지의 총합을 출력한다.
 * 
 * 시간복잡도 = O(R*C*T) (R: 세로 길이 <= 50 C: 가로 길이<= 50, T: 시간 <= 1000) 
 */

import java.util.*;
import java.io.*;

public class Main_17144 {
	static int R, C;
	static int[][] room;
	static int[][] purifierPos = new int[2][];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		int T = Integer.parseInt(st.nextToken());

		// 방 정보 입력
		room = new int[R][C];
		int idx = 0;

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				room[i][j] = Integer.parseInt(st.nextToken());

				// 공기 청정기 위치 찾기
				if (room[i][j] == -1)
					purifierPos[idx++] = new int[] { i, j };
			}
		}

		// T초 반복
		while (T-- > 0) {
			diffusion(); // 확산
			circulation(); // 순환
		}

		// 남아있는 미세먼지 카운팅
		int sum = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (room[i][j] > 0)
					sum += room[i][j];
			}
		}
		System.out.println(sum);
	}

	// 미세먼지 확산
	public static void diffusion() {
		int[][] aftRoom = new int[R][C]; // 이후의 방 모습
		int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } }; // 주위 4방향

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				// 해당 영역에 공기청정기가 있다면 위치 복사
				if (room[i][j] == -1) {
					aftRoom[i][j] = -1;
					continue;
				}
				// 해당 영역에 먼지가 없으면 패스
				else if (room[i][j] == 0)
					continue;

				int spread = 0; // 주위로 퍼지는 미세먼지 양
				// 4방향 확산
				for (int d = 0; d < 4; d++) {
					int nextX = i + dir[0][d];
					int nextY = j + dir[1][d];

					if (inMap(nextX, nextY) && room[nextX][nextY] != -1) {
						aftRoom[nextX][nextY] += room[i][j] / 5;
						spread += room[i][j] / 5;
					}
				}
				aftRoom[i][j] += room[i][j] - spread;
			}
		}
		// 현재 방 모습을 이후의 방 모습으로 변경
		room = aftRoom;
	}

	// 공기청정기 바람 순환
	public static void circulation() {
		int[][] aftRoom = new int[R][C]; // 이후 방모습
		int[][][] rotateDir = { { { 0, -1, 0, 1 }, { 1, 0, -1, 0 } }, // 상단 부분 반시계방향 순환 (우, 상, 좌, 하)
				{ { 0, 1, 0, -1 }, { 1, 0, -1, 0 } } // 하단 부분 시계방향 순환 (우, 하, 좌, 상)
		};

		// Deep Copy
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				aftRoom[i][j] = room[i][j];

		// 상단, 하단 공기 순환 시작
		for (int r = 0; r < 2; r++) {
			int dIdx = 0;
			int currX = purifierPos[r][0] + rotateDir[r][0][dIdx];
			int currY = purifierPos[r][1] + rotateDir[r][1][dIdx];
			aftRoom[currX][currY] = 0;

			while (true) {
				int nextX = currX + rotateDir[r][0][dIdx];
				int nextY = currY + rotateDir[r][1][dIdx];

				// 다음 위치가 공기청정기 위치면 순환 종료
				if (nextX == purifierPos[r][0] && nextY == purifierPos[r][1])
					break;

				// 방을 벗어나거나 상, 하단 위치를 벗어나면 방향전환
				if (!inMap(nextX, nextY) || nextX == purifierPos[(r + 1) % 2][0]) {
					dIdx = (dIdx + 1) % 4;
					continue;
				}

				// 다음 위치에 현재 미세먼지량 저장
				aftRoom[nextX][nextY] = room[currX][currY];

				// 다음 위치로 이동
				currX = nextX;
				currY = nextY;
			}
		}
		// 현재 방 모습을 이후의 방 모습으로 변경
		room = aftRoom;
	}

	// 지도 내에 존재하는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < R && y >= 0 && y < C);
	}
}
