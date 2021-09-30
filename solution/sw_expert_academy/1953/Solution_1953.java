/**
 * 1. 각 터널 번호에 맞는 이동방향을 미리 설정하고, 터널 정보를 입력받는다.
 * 2. BFS의 레벨 단위로 탐색하는 것으로 제한 시간 내에 존재 가능한 위치 개수를 카운팅한다. - O(N*M)
 * 3. 다음 노드를 탐색하기 위해서 다음 조건을 만족하는지 확인한다.
 *    - 범위 내에 있는지
 *    - 다음 터널과 연결되어있는지 (터널 여부 확인, 이동 가능 방향 비교)
 *    - 아직 방문하지 않았는지 확인
 * 
 * 시간복잡도 = O(T*N*M) (T : 테스트케이스 개수 <= 50, N : 세로 크기 <= 50, M : 가로 크기 <= 50)
 */

import java.io.*;
import java.util.*;

public class Solution_1953 {
	static int N, M;
	static int[][] tunnel;
	static int dir[][][] = { 
			{ { -1, 1, 0, 0 }, { 0, 0, -1, 1 } }, 
			{ { -1, 1 }, { 0, 0 } }, { { 0, 0 }, { -1, 1 } },
			{ { -1, 0 }, { 0, 1 } },
			{ { 1, 0 }, { 0, 1 } }, 
			{ { 1, 0 }, { 0, -1 } }, 
			{ { -1, 0 }, { 0, -1 } }, 
		}; // 각 터널의 이동방향

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_모의_1953.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= T; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());						
			
			int startX = Integer.parseInt(st.nextToken()); // 맨홀 위치
			int startY = Integer.parseInt(st.nextToken()); 
			int limit = Integer.parseInt(st.nextToken()); // 제한 시간
			
			// 터널 정보 입력
			tunnel = new int[N][M];
			for(int i=0; i < N; i++) {
				st = new StringTokenizer(br.readLine()); 
				for(int j=0; j < M; j++)
					tunnel[i][j] = Integer.parseInt(st.nextToken()) - 1;
			}			
			
			// BFS의 레벨 단위 탐색으로 존재 가능한 위치 개수 카운팅
			sb.append('#').append(testCnt).append(' ').append(bfs(startX, startY, limit)).append('\n');
		}
		
		System.out.println(sb);
		br.close();
	}

	// BFS의 레벨 단위 탐색으로 존재 가능한 위치 개수 카운팅
	public static int bfs(int startX, int startY, int limit) {						
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		
		visited[startX][startY] = true;		
		q.offer(new int[] { startX, startY });
		
		int time = 0; // 소요 시간
		int cnt = 0; // 가능한 경우

		while (!q.isEmpty() && time < limit) {
			int qSize = q.size();

			// 레벨 단위 탐색
			while (qSize-- > 0) {
				cnt++;

				int x = q.peek()[0];
				int y = q.poll()[1];
				int curr = tunnel[x][y];

				for (int d = 0; d < dir[curr][0].length; d++) {
					int nextX = x + dir[curr][0][d];
					int nextY = y + dir[curr][1][d];					
					
					// 범위 내에 있는지, 다음 터널과 연결되어있는지, 아직 방문하지 않았는지 확인
					if(inTunnel(nextX, nextY) && tunnel[nextX][nextY] != -1 && isConnected(dir[curr][0][d], dir[curr][1][d], dir[tunnel[nextX][nextY]])&& !visited[nextX][nextY]) {
						visited[nextX][nextY] = true;
						q.offer(new int[]{nextX, nextY});
					}
				}
			}
			time++;
		}
		return cnt;
	}

	// 범위 내에 있는지 확인
	public static boolean inTunnel(int x, int y) {
		return (x >= 0 && x < N & y >= 0 && y < M);
	}

	// 다음 터널과 연결되어있는지 확인
	public static boolean isConnected(int xDir, int yDir, int[][] toDir) {
		boolean connect = false;

		// 현재 노드 이동방향(→)의 역방향이 다음 노드의 이동 가능 방향(←)으로 존재하면 연결되어있음
		for (int i = 0; i < toDir[0].length; i++) {
			if (toDir[0][i] == -xDir && toDir[1][i] == -yDir)
				connect = true;
		}
		return connect;
	}
}
