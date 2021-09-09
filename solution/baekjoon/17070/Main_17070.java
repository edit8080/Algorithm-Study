/**
 * 1. DFS로 (N,N)에 도달할 수 있는 경우의 수 탐색한다. - 3방향(우, 우하, 하)으로 움직일 수 있으므로 O(3^n)
 * 2. 현재 파이프 방향이 우측이면 우, 우하 / 우하단이면 우, 우하, 하 / 하단이면 우하, 하 방향으로 이동하여 탐색을 진행한다.
 * 3. (N, N) 위치에 도달하면 경우의 수를 카운팅한다.
 * 
 * 주의) BFS로 해결할 경우 시간초과 발생
 * 
 * 시간복잡도 = O(3^n) (n: 한 변의 크기 <= 16)
 */

import java.util.*;
import java.io.*;

public class Main_17070 {
	static class pipe {
		int x, y, pDir; // 파이프 x, y 좌표와 방향 (0: 우, 1: 우하, 2: 하)

		pipe(int x, int y, int pDir) {
			this.x = x;
			this.y = y;
			this.pDir = pDir;
		}
	}

	static int N;
	static int[][] map;
	static int[][] dir = { { 0, 1, 1 }, { 1, 1, 0 } }; // 파이프 이동 가능 방향 : 우, 우하, 하
	static int cnt = 0;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		// 지도 정보 입력
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}
		// DFS로 (N,N)에 도달할 수 있는 경우의 수 탐색
		dfs(0, 1, 0);

		System.out.println(cnt);
		br.close();
	}

	// DFS로 (N,N)에 도달할 수 있는 경우의 수 탐색 - O(3^n) (n: 한 변의 크기 <= 16)
	public static void dfs(int x, int y, int dir) {
		// (N,N)에 도달하는 개수 카운팅
		if (x == N - 1 && y == N - 1)
			cnt++;

		// 현재 파이프 방향에 따라 다음 파이프 지정
		switch (dir) {
		case 0: // 우측 -> 우, 우하 이동가능
			checkRight(x, y);
			checkRightBottom(x, y);
			break;
		case 1: // 우하 -> 우, 우하, 하 이동가능
			checkRight(x, y);
			checkRightBottom(x, y);
			checkBottom(x, y);
			break;
		case 2: // 하 -> 우하, 하 이동가능
			checkRightBottom(x, y);
			checkBottom(x, y);
			break;
		}

	}

	// 우측 이동 체크
	public static void checkRight(int x, int y) {
		int nextX = x + dir[0][0];
		int nextY = y + dir[1][0];

		// 다음 노드가 지도 내에 없거나 벽으로 막혀있다면 탐색 종료
		if (!inMap(nextX, nextY) || map[nextX][nextY] == 1)
			return;

		dfs(nextX, nextY, 0);
	}

	// 우하단 이동 체크
	public static void checkRightBottom(int x, int y) {
		int nextX = x + dir[0][1];
		int nextY = y + dir[1][1];

		// 다음 노드가 지도 내에 없거나 벽으로 막혀있다면 탐색 종료
		if (!inMap(nextX, nextY) || map[nextX - 1][nextY] == 1 || map[nextX][nextY - 1] == 1 || map[nextX][nextY] == 1)
			return;

		dfs(nextX, nextY, 1);
	}

	// 하단 이동 체크
	public static void checkBottom(int x, int y) {
		int nextX = x + dir[0][2];
		int nextY = y + dir[1][2];

		// 다음 노드가 지도 내에 없거나 벽으로 막혀있다면 탐색 종료
		if (!inMap(nextX, nextY) || map[nextX][nextY] == 1)
			return;

		dfs(nextX, nextY, 2);
	}

	// 지도 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
