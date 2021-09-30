/**
 * 1. 지도 정보를 입력받는다.
 * 2. BFS 탐색으로 지도 정보를 통한 최소 시간을 구한다. - O(N^2)
 *    -> 서로 다른 경로를 통한 최소가 존재할 수 있으므로 visited 배열말고 현재값과 이전값의 비교로 탐색여부를 결정한다.
 * 3. 다음 노드가 지도 내에 있고, 새로운 경로의 시간합이 기존 시간합보다 작으면 추가로 탐색한다.
 * 
 * 시간복잡도 = O(N^2) (N: 지도의 크기 <= 100)
 */

import java.util.*;
import java.io.*;

public class Solution_1249 {
	static class Node {
		int x, y, time;

		public Node(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}

	static int N;
	static char[][] map;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_1249.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= T; testCnt++) {
			N = Integer.parseInt(br.readLine());

			// 지도 정보 입력
			map = new char[N][N];
			for (int i = 0; i < N; i++)
				map[i] = br.readLine().toCharArray();

			// BFS 탐색으로 최소 시간 구하기
			sb.append('#').append(testCnt).append(' ').append(minTime(0, 0)).append('\n');
		}

		System.out.println(sb);
		br.close();
	}

	public static int minTime(int startX, int startY) {
		int[][] time = new int[N][N];
		int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

		Queue<Node> q = new LinkedList<>();

		for (int i = 0; i < N; i++)
			Arrays.fill(time[i], Integer.MAX_VALUE);

		time[startX][startY] = map[startX][startY] - '0';
		q.offer(new Node(startX, startY, time[startX][startY]));

		// BFS로 최소 시간 구하기
		// 서로 다른 경로를 통한 최소가 존재할 수 있으므로 visited 배열말고 현재값과 이전값의 비교로 탐색여부를 결정
		while (!q.isEmpty()) {
			Node curr = q.poll();
			
			// 현재 위치까지의 시간합이 새로운 경로를 통한 시간합보다 작으면 추가 탐색을 방지 (최적화)
			if (time[curr.x][curr.y] < curr.time)
				continue;

			// 다음 노드 탐색
			for (int i = 0; i < 4; i++) {
				int nextX = curr.x + dir[0][i];
				int nextY = curr.y + dir[1][i];

				// 다음 노드가 지도 내에 있고, 새로운 경로의 시간합이 기존 시간합보다 작으면 추가 탐색
				if (inMap(nextX, nextY) && time[nextX][nextY] > curr.time + map[nextX][nextY] - '0') {
					time[nextX][nextY] = curr.time + map[nextX][nextY] - '0';
					q.offer(new Node(nextX, nextY, curr.time + map[nextX][nextY] - '0'));
				}
			}
		}

		return time[N - 1][N - 1];
	}

	// 지도 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
