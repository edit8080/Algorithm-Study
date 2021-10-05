/**
 * 1. 보드판 정보를 입력받는다.
 * 2. 중복 순열을 통해 구슬을 쏠 위치를 결정한다. - 중복 순열 O(W^N)
 * 3. 구슬을 쏜 위치에 블록이 없다면 횟수만 날리고 pass
 * 4. 구슬을 쏜 위치에 블록이 있다면
 *    - 맞은 블록 및 주변 블록을 함께 제거 처리하고
 *    - 모든 열을 확인하여 행이 0인 부분을 제거한다.
 * 5. 이후 N번이 될 때까지 2~4번 과정을 반복한다.
 * 6. N번 수행했다면 남아있는 블록의 개수 중 최소값을 비교한다.
 * 
 * 시간복잡도 = O(T * W^N * H * W) (T: 테스트케이스 개수, H: 열의 길이 <= 15, W: 행의 길이 <= 12, N: 구슬 쏘기 횟수 <= 4)
 */

import java.util.*;
import java.io.*;

public class Solution_5656 {
	static class Point {
		int r, c, cnt;

		public Point(int r, int c, int cnt) {
			this.r = r;
			this.c = c;
			this.cnt = cnt;
		}
	}

	static int N, W, H;
	static int[][] board;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
	static int minCnt;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_모의_5656.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= T; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			// 보드판 정보 입력
			board = new int[H][W];

			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());

				for (int j = 0; j < W; j++)
					board[i][j] = Integer.parseInt(st.nextToken());
			}

			minCnt = Integer.MAX_VALUE;

			// 구슬 쏘기 시작
			go(0, board);

			// 최소 블록 개수 출력
			sb.append('#').append(testCnt).append(' ').append(minCnt).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 구슬 쏘기 - 중복 순열 O(W^N) (W: 행의 길이 <= 12, N: 구슬 쏘기 횟수 <= 4)
	public static void go(int cnt, int[][] board) {
		// 구슬을 모두 쐈다면 남아있는 블록 개수의 최소값 비교
		if (cnt == N) {
			int blockCnt = getRemain(board);
			minCnt = Math.min(minCnt, blockCnt);
			return;
		}

		int[][] newBoard = new int[H][W];

		// 현재 위치에 구슬 쏘기
		for (int c = 0; c < W; c++) {
			int r = 0;

			// 구슬 하강
			while (r < H && board[r][c] == 0)
				r++;

			if (r == H) { // 구슬을 던졌지만 맞은 블록이 없는 경우
				go(cnt + 1, board);
			} else {
				// 이전 cnt 까지의 board 상태 복사
				copy(board, newBoard);

				// 맞은 블록 및 주변 블록을 함께 제거 처리
				remove(newBoard, r, c);

				// 모든 열을 확인하여 행이 0인 부분 제거
				down(newBoard);

				// 다음 구슬 던지기
				go(cnt + 1, newBoard);
			}
		}
	}

	// 남아있는 벽돌 개수 세기 - O(H * W) (H : 세로 길이 <= 15, W: 가로 길이 <= 12)
	private static int getRemain(int[][] board) {
		int cnt = 0;

		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (board[i][j] != 0)
					cnt++;
			}
		}
		return cnt;
	}

	// 0인 부분을 아래로 내리기 - O(H * W) (H : 세로 길이 <= 15, W: 가로 길이 <= 12)
	public static void down(int[][] board) {
		for (int c = 0; c < W; c++) {
			int r = H - 1;

			while (r > 0) {
				// 0인 부분을 만나면 위 행 값들을 아래로 내려야함
				if (board[r][c] == 0) {
					int nr = r - 1;

					// 위 행 값이 처음으로 0이 아닌 값이 나타나는 부분을 아래로 내리기
					while (nr > 0 && board[nr][c] == 0)
						nr--;

					board[r][c] = board[nr][c];
					board[nr][c] = 0;
				}
				--r;
			}
		}
	}

	// 블록 부수기 - O(H * W) (H : 세로 길이 <= 15, W: 가로 길이 <= 12)
	public static void remove(int[][] board, int r, int c) {
		Queue<Point> q = new LinkedList<>();
		if (board[r][c] > 1) // 블록 숫자가 1보다 클때만 주변 블록 연쇄 처리
			q.offer(new Point(r, c, board[r][c]));

		board[r][c] = 0; // 벽돌을 제거하고 빈칸채우기

		// BFS로 함께 부숴질 벽돌 처리
		while (!q.isEmpty()) {
			Point p = q.poll();

			for (int d = 0; d < 4; d++) {
				int nextR = p.r;
				int nextC = p.c;

				for (int i = 1; i < p.cnt; i++) {
					nextR += dir[0][d];
					nextC += dir[1][d];

					if (nextR >= 0 && nextR < H && nextC >= 0 && nextC < W && board[nextR][nextC] != 0) {
						// 값이 1보다 크면 연쇄적으로 부수기
						if (board[nextR][nextC] > 1)
							q.offer(new Point(nextR, nextC, board[nextR][nextC]));
						// 블록 부숨
						board[nextR][nextC] = 0;
					}
				}
			}
		}
	}

	// board 내용을 newBoard로 Deep Copy - O(H * W) (H : 세로 길이 <= 15, W: 가로 길이 <= 12)
	public static void copy(int[][] board, int[][] newBoard) {
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++)
				newBoard[i][j] = board[i][j];
		}
	}
}
