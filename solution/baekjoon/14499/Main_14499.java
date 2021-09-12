/**
 * 1. 주사위가 이동하려는 곳이 지도 내에 있는지 확인한다. -> 지도 내에 없으면 무시
 * 2. 주사위가 이동할 수 있다면 주사위가 구른 다음의 모습에 맞게 값을 변경한다.
 * 3. 주사위나 바닥면이 0이라면 그에 맞게 처리를 수행한다.
 * 4. 이후 주사위 윗면의 값을 출력한다.
 * 
 * 시간복잡도 - O(K) (K : 명령의 개수 <= 1000) 
 */

import java.util.*;
import java.io.*;

/*   
 *   주사위 전개도 인덱스
 *   ---
 *   |5|
 * ---------
 * |0|1|2|3|  0 : 밑면, 2 : 윗면 
 * ---------
 *   |4|
 *   ---
 */
public class Main_14499 {
	static int N, M; // 지도의 크기
	static int[][] map; // 지도
	static int diceX, diceY; // 주사위 위치
	static int[] dice = new int[6]; // 주사위 모양
	static int[][] dir = { { 0, 0, 0, -1, 1 }, { 0, 1, -1, 0, 0 } }; // 이동 방향 : 제자리, 동,서,북,남
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		diceX = Integer.parseInt(st.nextToken());
		diceY = Integer.parseInt(st.nextToken());

		int order = Integer.parseInt(st.nextToken());

		// 지도 입력
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		// 명령 입력
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < order; i++)
			moveDice(Integer.parseInt(st.nextToken()));

		System.out.println(sb);
		br.close();
	}

	// 주사위 이동
	public static void moveDice(int moveDir) {
		// 1. 주사위가 이동하려는 곳이 지도 내에 있는지 확인
		int nextX = diceX + dir[0][moveDir];
		int nextY = diceY + dir[1][moveDir];

		if (!inMap(nextX, nextY))
			return;

		// 주사위 이동
		diceX = nextX;
		diceY = nextY;

		// 2. 주사위가 굴러서 이동할 때 변화되는 모습
		switch (moveDir) {
		case 1:
			rollEast();
			break;
		case 2:
			rollWest();
			break;
		case 3:
			rollNorth();
			break;
		case 4:
			rollSouth();
			break;
		}

		// 3. 바닥면이 0이면 주사위 밑면값 -> 바닥
		if (map[diceX][diceY] == 0)
			map[diceX][diceY] = dice[0];
		// 바닥면이 0이 아니면 바닥값 -> 주사위 밑면, 이후 바닥면을 0으로 초기화
		else {
			dice[0] = map[diceX][diceY];
			map[diceX][diceY] = 0;
		}

		// 4. 윗면 값 출력
		sb.append(dice[2]).append('\n');
	}

	// 지도 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

	// 주사위가 동쪽으로 이동한 뒤의 모습
	public static void rollEast() {
		int[] aftDice = new int[6];

		aftDice[0] = dice[1];
		aftDice[1] = dice[2];
		aftDice[2] = dice[3];
		aftDice[3] = dice[0];
		aftDice[4] = dice[4];
		aftDice[5] = dice[5];

		dice = aftDice;
	}

	// 주사위가 서쪽으로 이동한 뒤의 모습
	public static void rollWest() {
		int[] aftDice = new int[6];

		aftDice[0] = dice[3];
		aftDice[1] = dice[0];
		aftDice[2] = dice[1];
		aftDice[3] = dice[2];
		aftDice[4] = dice[4];
		aftDice[5] = dice[5];

		dice = aftDice;
	}

	// 주사위가 북쪽으로 이동한 뒤의 모습
	public static void rollNorth() {
		int[] aftDice = new int[6];

		aftDice[0] = dice[5];
		aftDice[1] = dice[1];
		aftDice[2] = dice[4];
		aftDice[3] = dice[3];
		aftDice[4] = dice[0];
		aftDice[5] = dice[2];

		dice = aftDice;
	}

	// 주사위가 남쪽으로 이동한 뒤의 모습
	public static void rollSouth() {
		int[] aftDice = new int[6];

		aftDice[0] = dice[4];
		aftDice[1] = dice[1];
		aftDice[2] = dice[5];
		aftDice[3] = dice[3];
		aftDice[4] = dice[2];
		aftDice[5] = dice[0];

		dice = aftDice;
	}
}
