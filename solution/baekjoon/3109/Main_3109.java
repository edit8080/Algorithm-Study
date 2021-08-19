/**
 * 1. 가스관의 모든 시작 위치에 대해 DFS 탐색을 수행한다.
 * 2. DFS 탐색방향은 우상, 우, 우하 방향 순으로 진행한다.
 *    -> 그리디적으로 생각하면 최대한 위로 붙는 파이프라인을 구성하는 것이 유리하다. 
 * 3. 이미 파이프라인이 구성되었다면 추가적인 탐색을 진행하지 않는다.
 *    -> 최대 이득 파이프라인이 구성 되었으므로 
 *  
 * 시간복잡도 = O(r*c)
 */

import java.util.*;
import java.io.*;

public class Main_3109 {
	static int r, c;
	static char[][] map;
	static boolean[][] visited;
	static int[] dir = { -1, 0, 1 }; // x좌표 - 우상, 우, 우하 (※ 주의: 그리디적으로 위에서부터 채움)
	static int cnt = 0;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		map = new char[r][c];
		visited = new boolean[r][c];

		for (int i = 0; i < r; i++)
			map[i] = br.readLine().toCharArray();

		// 각 가스관 시작점에 대해 DFS 탐색
		for (int i = 0; i < r; i++) {
			pipeline(i, 0);
		}

		System.out.println(cnt);
		br.close();
	}
	
	// DFS로 파이프라인 설치 - 그리디적으로 위에서부터 설치한다. 
	public static boolean pipeline(int x, int y) {
		// 파이프 라인 설치 가능
		if (y == c - 1) {
			cnt++;
			return true;
		}

		visited[x][y] = true;

		// 우, 우상, 우하 이동
		for (int i = 0; i < 3; i++) {
			int nextX = x + dir[i];
			int nextY = y + 1;

			// 파이프 설치
			if (nextX >= 0 && nextX < r && !visited[nextX][nextY] && map[nextX][nextY] == '.') {
				// ※ 주의 - 이미 파이프라인을 설정했다면 다시 탐색할 필요가 없음
				if (pipeline(nextX, nextY))
					return true;
			}
		}
		return false;
	}
}
