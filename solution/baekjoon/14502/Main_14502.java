/**
 * 1. 좌표 입력받을 때 빈 칸 좌표는 따로 저장한다.
 * 2. 조합을 통해 빈 칸에 3개의 벽을 세운다. - O(n*mC3)
 * 3. 벽을 세운 환경에 대해서 바이러스를 확산 시킨다 - O(n*m)
 * 4. 이후 남아있는 안전 영역 개수를 카운팅한다. - O(N*M)
 * 5. 2~4 과정을 반복했을 때 나올 수 있는 최대 안전 영역 개수를 출력한다.
 * 
 * 시간복잡도 = O(n*mC3 * n * m) (n : 지도 세로 크기 <= 8, m : 지도 가로 크기 <= 8)
 */

import java.io.*;
import java.util.*;

public class Main_14502 {
	static int N, M;
	static int[][] map;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		List<int[]> blankCords = new ArrayList<>();

		// 좌표 입력 (빈 칸 좌표는 따로 저장)
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (map[i][j] == 0)
					blankCords.add(new int[] { i, j });
			}
		}

		// 조합을 위한 인덱스 배열 초기화
		int[] blankIdx = new int[blankCords.size()];
		for (int i = 1; i <= 3; i++)
			blankIdx[blankCords.size() - i] = 1;

		int maxSafeCnt = 0;
		do {
			// 지도 Deep Copy
			int[][] mapCopy = new int[N][M];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++)
					mapCopy[i][j] = map[i][j];
			}

			// 조합을 통해 빈 칸에 벽 세우기 - O(N*M)
			for (int i = 0; i < blankCords.size(); i++) {
				if (blankIdx[i] == 1)
					mapCopy[blankCords.get(i)[0]][blankCords.get(i)[1]] = 1;
			}

			// 바이러스 확산 - O(N*M)
			boolean[][] visited = new boolean[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++)
					if (mapCopy[i][j] == 2)
						spreadVirus(mapCopy, visited, i, j);
			}
						
			// 안전 영역 개수 카운팅(O(N*M)) -> 최대값 비교
			maxSafeCnt = Math.max(maxSafeCnt, safeCnt(mapCopy));
		} while (nextPermutation(blankIdx)); // 조합 생성 - O(n*mC3)

		System.out.println(maxSafeCnt);
		br.close();
	}

	// 벽을 세우기 위한 조합을 만들기 위해 사용
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;

		int i = n - 1;
		while (i > 0 && arr[i - 1] >= arr[i])
			i--;

		if (i == 0)
			return false;

		int j = n - 1;
		while (arr[i - 1] >= arr[j])
			j--;

		swap(arr, i - 1, j);
		int k = n - 1;
		while (i < k)
			swap(arr, i++, k--);

		return true;
	}

	// 배열 원소 교환
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 바이러스 확산
	public static void spreadVirus(int[][] map, boolean[][] visited, int x, int y) {
		visited[x][y] = true;

		for (int i = 0; i < 4; i++) {
			int nextX = x + dir[0][i];
			int nextY = y + dir[1][i];

			if (inMap(nextX, nextY) && map[nextX][nextY] == 0 && !visited[nextX][nextY]) {
				map[nextX][nextY] = 2;
				spreadVirus(map, visited, nextX, nextY);
			}
		}
	}

	// 안전 영역 개수 카운팅
	public static int safeCnt(int[][] map) {
		int cnt = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0)
					cnt++;
			}
		}
		return cnt;
	}

	// 좌표가 지도 내에 있는지 확인
	public static boolean inMap(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}

}
