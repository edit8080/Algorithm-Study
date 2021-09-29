/**
 * 1. 동굴 크기와 동굴 정보를 입력받는다.
 * 2. 다익스트라를 통해 최소 거리를 탐색한다. 정점은 동굴의 각 좌표로 간주한다. (V = N^2)
 *    -> 시간초과 O(N^4)를 막기 위해 우선순위 큐를 사용한다.
 * 3. 다익스트라를 통한 최소 거리 탐색을 완료했으면 도착점인 (N-1, N-1) 의 최소 거리값을 반환한다.
 * 4. 동굴 크기로 0을 입력받을 때까지 위 과정을 반복한다.
 * 
 * 주의) DP로 해결할 경우 visited[x][y][x*y] 크기의 방문 배열이 필요하므로 O(N^4) 로직이 되어 시간초과가 발생
 *       -> 그래프의 사이클 여부 체크와 일부 사이클 이용의 중간을 체크하기 위해 [x*y] 크기의 배열이 필요하다. (이동하는 모든 경우의 수) 
 *       -> ex) (하, 하, 하) vs (우, 하, 하, 하, 좌) : 단순 방문 체크로는 비교할 수 없다.
 * 
 * 시간복잡도 = O(N^2 log N) (N : 동굴의 크기 <= 125)
 */

import java.util.*;
import java.io.*;

public class Main_4485 {
	static int N;
	static int[][] cave;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = 1;

		while (true) {
			N = Integer.parseInt(br.readLine());

			if (N == 0)
				break;

			// 동굴 정보 입력
			cave = new int[N][N];
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					cave[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			sb.append("Problem ").append(t++).append(": ").append(minLoopy()).append('\n');
		}
		System.out.print(sb);
		br.close();
	}

	// 도착지점까지 최소 거리 구하기 - 다익스트라 O(N^2 log N)
	public static int minLoopy() {
		// 거리 배열 초기화
		int[][] dist = new int[N][N];
		for (int i = 0; i < N; i++)
			Arrays.fill(dist[i], Integer.MAX_VALUE);

		// 우선순위 큐 설정 - (거리합, x좌표, y좌표)
		PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> Integer.compare(x[0], y[0]));

		// 시작점 설정
		dist[0][0] = cave[0][0];
		pq.offer(new int[] { cave[0][0], 0, 0 });

		// 다익스트라 시작
		while (!pq.isEmpty()) {
			int d = pq.peek()[0];
			int x = pq.peek()[1];
			int y = pq.poll()[2];

			if (dist[x][y] < d)
				continue;

			// 다음 노드 찾기
			for (int i = 0; i < 4; i++) {
				int nextX = x + dir[0][i];
				int nextY = y + dir[1][i];

				if (inCave(nextX, nextY) && dist[nextX][nextY] > d + cave[nextX][nextY]) {
					dist[nextX][nextY] = d + cave[nextX][nextY];
					pq.offer(new int[] { d + cave[nextX][nextY], nextX, nextY });
				}
			}
		}

		return dist[N - 1][N - 1];
	}

	// 동굴 내에 있는지 확인
	public static boolean inCave(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
