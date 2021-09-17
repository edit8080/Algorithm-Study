/**
 * 1. 보드 정보를 입력받을 때 코어의 위치를 따로 저장해놓는다.
 * 2. DFS와 백트래킹으로 탐색을 시작한다.
 *    - 전선을 놓기전 이미 코어나 전선이 놓여있는지 확인한다.
 *    - 전선을 놓을 수 있다면 전선을 설치한다. 이후 다음 탐색에 영향이 없도록하기 위해 재귀 탐색 종료 후 초기화를 수행한다.
 * 3. 상하좌우 모두 놓을 수 없다면 패스하고 다음 코어를 탐색한다.
 * 4. 마지막 코어까지 확인을 완료했다면 전선을 설치한 코어 개수와 전선의 최소 길이를 비교한다.
 *    - 탐색 코어 == 최대 코어라면 전선의 최소 길이를 비교한다.
 *    - 탐색 코어 > 최대 코어라면 최대 코어와 최소 길이를 갱신한다.
 * 
 * 주의) 상하좌우 모두 놓을 수 없는 경우에만 현재 노드를 패스해야하는 점을 유의한다.
 *       -> noCase 처리를 하지 않으면 시간초과 발생
 */

import java.util.*;
import java.io.*;

public class Solution_1767 {
	static int N; // 지도 크기
	static int[][] board; // 지도 정보
	static List<int[]> corePos; // 코어 위치
	static int dir[][] = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } }; // 이동 방향
	static int maxInstallCnt; // 설치한 최대 코어 개수
	static int minLen; // 최소 전선 길이

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_SW샘플_1767.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			N = Integer.parseInt(br.readLine());

			// 보드 정보 입력
			board = new int[N][N];
			corePos = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < N; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());

					// 코어의 위치 저장 (벽면 코어 제외)
					if (board[i][j] == 1)
						corePos.add(new int[] { i, j });
				}
			}

			// 백트래킹 시작
			boolean[][] visited = new boolean[N][N];
			maxInstallCnt = 0;
			minLen = Integer.MAX_VALUE;

			connectCore(visited, 0, 0, 0);
			sb.append('#').append(testCnt).append(' ').append(minLen).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	public static void connectCore(boolean[][] visited, int coreNum, int installCnt, int sum) {
		// 마지막까지 탐색완료
		if (coreNum == corePos.size()) {
			// 설치한 코어개수가 최대 코어개수와 동일하면 최소합 갱신
			if (installCnt == maxInstallCnt) 
				minLen = Math.min(minLen, sum);			

			// 설치한 코어개수가 여태 설치한 최대 코어개수보다 크면 최대 코어개수, 최소합 전부 갱신
			else if (installCnt > maxInstallCnt) {
				maxInstallCnt = installCnt;
				minLen = sum;
			}

			return;
		}

		boolean noCase = true;
		for (int d = 0; d < 4; d++) {
			boolean canSet = true; // 전선 설치 가능 여부
			
			int currX = corePos.get(coreNum)[0];
			int currY = corePos.get(coreNum)[1];

			// 이미 코어나 전선이 놓여있는지 확인
			for (int i = 1; i < N; i++) {
				int lineX = currX + i * dir[0][d];
				int lineY = currY + i * dir[1][d];

				if (!inBoard(lineX, lineY))
					break;

				if (board[lineX][lineY] == 1 || visited[lineX][lineY]) {
					canSet = false;
					break;
				}
			}

			// 전선을 설치할 수 있다면 전선 설치
			if (canSet) {
				noCase = false;
				int len = setLines(visited, coreNum, d, true);
				connectCore(visited, coreNum + 1, installCnt + 1, sum + len);
				setLines(visited, coreNum, d, false);
			}
		}
		// 전선을 설치할 수 없다면 다음 코어 확인
		if (noCase)
			connectCore(visited, coreNum + 1, installCnt, sum);
	}

	// 전선 설치 및 제거 (value 기준)
	public static int setLines(boolean[][] visited, int coreNum, int d, boolean value) {
		int len = -1;

		int currX = corePos.get(coreNum)[0];
		int currY = corePos.get(coreNum)[1];

		for (int i = 0; i < N; i++) {
			int lineX = currX + i * dir[0][d];
			int lineY = currY + i * dir[1][d];

			if (!inBoard(lineX, lineY))
				break;

			visited[lineX][lineY] = value;
			len++;
		}
		return len;
	}

	// 지도 내에 있는지 확인
	public static boolean inBoard(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
