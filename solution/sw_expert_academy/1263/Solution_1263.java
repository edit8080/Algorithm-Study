import java.io.*;
import java.util.*;

public class Solution_1263 {
	static final int INF = Integer.MAX_VALUE / 2;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d6_1263.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int[][] adjMatrix = new int[N][N];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int item = Integer.parseInt(st.nextToken());

					if (i == j)
						continue;
					else if (item == 0)
						adjMatrix[i][j] = INF;
					else
						adjMatrix[i][j] = item;
				}
			}

			// 플로이드-와셜로 각 정점에서 다른 정점까지 뻗어나갈 수 있는 최소거리 탐색 - O(n^3)
			floyd(N, adjMatrix);

			// 각 지점까지 거리의 합 중 최소값 비교
			int min = INF;
			for (int i = 0; i < N; i++) {
				int sum = 0;
				for (int j = 0; j < N; j++) {
					sum += adjMatrix[i][j];
				}
				min = Math.min(min, sum);
			}
			sb.append('#').append(testCnt).append(' ').append(min).append('\n');
		}
		System.out.println(sb);
		br.close();

	}

	// 각 정점에서 다른 정점까지 뻗어나갈 수 있는 최소거리 탐색 - O(n^3)
	public static void floyd(int N, int[][] adjMatrix) {
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (i == j || i == k || j == k)
						continue;

					adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
				}
			}
		}
	}

}
