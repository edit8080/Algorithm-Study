/**
 * 1. 미로 정보를 입력받을 때 민식이의 초기 위치와 출구들의 좌표를 입력받는다. 
 * 2. BFS로 최소 이동 횟수를 구한다. -> 출구에 가장 먼저 도달하는 순간이 최소 횟수
 * 3. 방문 배열을 모든 좌표 위치와 열쇠 정보에 기반해 설정한다. 열쇠 정보는 비트마스킹을 활용한다.
 *    -> 열쇠를 획득하면 열쇠 정보에 맞는 방문 배열로 이동하여 활용한다.
 *    -> ex) visited[x][y][000000] => B 열쇠 획득 => visited[x][y][000010] 방문 배열 사용
 * 4. 열쇠, 문, 이동가능 통로에 해당하는 상황에 맞게 처리한 후 다음 노드로 이동한다.  
 * 5. BFS 탐색간 탈출할 수 없었다면 -1을 return한다.
 * 
 * 시간복잡도 = O(N * M * k) (N: 미로 세로 크기 <= 50, M: 미로 가로 크기 <= 50, k: 열쇠 개수 <= 6)
 * 
 */

import java.io.*;
import java.util.*;

public class Main_1194 {
	static int N, M;
	static char[][] maze;
	static boolean[][][] visited;

	static class Node {
		int x, y;
		int move;
		int keys; // bit masking

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Node(int x, int y, int move, int keys) {
			this.x = x;
			this.y = y;
			this.move = move;
			this.keys = keys;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Node))
				return false;

			return ((Node) obj).x == this.x && ((Node) obj).y == this.y;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		maze = new char[N][];

		Node start = null;
		HashSet<Node> end = new HashSet<>();

		// 미로 정보 입력
		for (int i = 0; i < N; i++) {
			maze[i] = br.readLine().toCharArray();

			for (int j = 0; j < M; j++) {
				// 시작 위치
				if (maze[i][j] == '0')
					start = new Node(i, j);

				// 출구들 위치
				else if (maze[i][j] == '1')
					end.add(new Node(i, j));
			}
		}

		// BFS로 최소 이동 횟수를 구한다.
		System.out.println(bfs(start.x, start.y, end));
		br.close();
	}

	public static int bfs(int startX, int startY, HashSet<Node> end) {
		int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
		visited = new boolean[N][M][64]; // 2^6 (KEY : a ~ f 경우의 수)

		Queue<Node> q = new LinkedList<>();
		q.offer(new Node(startX, startY, 0, 0));

		while (!q.isEmpty()) {
			int x = q.peek().x;
			int y = q.peek().y;
			int move = q.peek().move;
			int keys = q.poll().keys;

			// 출구 도달 시 종료
			if (end.contains(new Node(x, y)))
				return move;

			for (int i = 0; i < 4; i++) {
				int nextX = x + dir[0][i];
				int nextY = y + dir[1][i];

				// 지도 내에 없고 벽이면 탐색 중단
				if (!inMaze(nextX, nextY) || visited[nextX][nextY][keys] || maze[nextX][nextY] == '#')
					continue;

				// 열쇠 획득
				if (maze[nextX][nextY] >= 'a' && maze[nextX][nextY] <= 'f') {
					visited[nextX][nextY][keys] = true;
					q.offer(new Node(nextX, nextY, move + 1, (keys | (1 << maze[nextX][nextY] - 'a'))));
				}

				// 열쇠가 있다면 문 열기
				if (maze[nextX][nextY] >= 'A' && maze[nextX][nextY] <= 'F'
						&& (keys & (1 << maze[nextX][nextY] - 'A')) != 0) {
					visited[nextX][nextY][keys] = true;
					q.offer(new Node(nextX, nextY, move + 1, keys));
				}

				// 단순 이동이 가능하면 이동
				if (maze[nextX][nextY] == '.' || maze[nextX][nextY] == '0' || maze[nextX][nextY] == '1') {
					visited[nextX][nextY][keys] = true;
					q.offer(new Node(nextX, nextY, move + 1, keys));
				}
			}
		}
		return -1;
	}

	// 지도 내에 있는지 확인
	public static boolean inMaze(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}

}
