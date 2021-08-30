/**
 * 1. 보드판 중앙을 초기화한다.
 * 2. 돌을 놓고 난 이후 8방향 탐색을 통해 동일한 돌이 있는지 확인한다. - O(M * N)
 * 3. 2번에서 돌을 발견했다면 돌을 놓고난 시작점과 2번에서 탐색한 점까지의 사이의 돌들을 변경한다. - O(M * N)
 * 4. 게임이 끝난 이후 각 돌의 개수를 카운팅한다. - O(N^2) 
 * 
 * 시간복잡도 = O(N^2 + M*N) (N : 보드 한 변의 길이 <= 8, M : 돌을 놓는 경우)
 */


import java.io.*;
import java.util.*;

public class Solution_4615 {
	static int[][] board;
	static int N, M;
	static int[][] dir = { { -1, -1, -1, 0, 0, 1, 1, 1 }, { -1, 0, 1, -1, 1, -1, 0, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d3_4615.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= t; testCase++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			board = new int[N][N];
			initBoard();

			// 각 돌을 놓는 위치에 대해 변경할 수 있는 돌의 색 체크 - O(M)
			while (M-- > 0) {
				st = new StringTokenizer(br.readLine());

				int x = Integer.parseInt(st.nextToken()) - 1;
				int y = Integer.parseInt(st.nextToken()) - 1;
				int color = Integer.parseInt(st.nextToken());

				checkBoard(x, y, color, M);
			}

			int bCnt = 0, wCnt = 0;

			// 모든 탐색이 끝나고 돌의 색 판단하기 - O(N^2)
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (board[i][j] == 1)
						bCnt++;
					else if (board[i][j] == 2)
						wCnt++;
				}
			}

			sb.append("#").append(testCase).append(' ').append(bCnt).append(' ').append(wCnt).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 보드 중앙 초기화
	public static void initBoard() {
		board[(N / 2) - 1][(N / 2) - 1] = 2;
		board[(N / 2) - 1][N / 2] = 1;
		board[N / 2][(N / 2) - 1] = 1;
		board[N / 2][N / 2] = 2;
	}

	// 각 돌을 놓는 위치에 대해 변경할 수 있는 돌의 색 체크 - O(N)
	public static void checkBoard(int x, int y, int color, int m) {
		board[x][y] = color;

		// 현재 돌을 놓은 지점에서부터 8방향 탐색 -> 동일한 색의 돌이 있는지 체크 - O(N)
		for (int i = 0; i < 8; i++) {
			int nextX = x;
			int nextY = y;

			while (inBoard(nextX += dir[0][i], nextY += dir[1][i]) && board[nextX][nextY] != 0) {
				// 동일한 색으로 감싸져 있다면 그 사이에 있는 돌의 색을 모두 변경
				if (board[nextX][nextY] == color) {
					int changeX = x;
					int changeY = y;

					while (!(changeX == nextX && changeY == nextY)) {
						board[changeX][changeY] = color;
						changeX += dir[0][i];
						changeY += dir[1][i];
					}
					break;
				}
			}
		}
	}

	// 보드 내에 있는지 확인
	public static boolean inBoard(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
