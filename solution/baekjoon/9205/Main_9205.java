/**
 * 1. 출발지 / 편의점 / 도착지에 대한 좌표를 입력받는다.
 * 2. 입력받은 좌표를 기반으로 각 정점에 대한 인접행렬을 생성한다. - O(n^2)
 *    -> 두 정점간 거리가 1000 이하일 때 경로가 존재한다고 말할 수 있다. - 맥주 1병당 50m * 20병
 * 3. 플로이드-워샬 알고리즘으로 각 지점을 거쳐서 도달할 수 있는 경로를 확인한다. - O(n^3)
 * 4. 출발지에서 도착지로 가는 경로가 존재하면 happy, 아니면 sad 를 출력한다.
 *    -> 1개 이상 INF개 미만인 경우 경로가 존재한다. 
 *    
 * 시간복잡도 = O(t * n^3) (t : 테스트 케이스 개수 <= 50, n : 정점의 개수 <= 100 )
 */

import java.io.*;
import java.util.*;

public class Main_9205 {
	static final int INF = Integer.MAX_VALUE / 2;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());

			int[][] cords = new int[n + 2][2];

			// 좌표 입력 (출발지 / 편의점 / 도착지)
			for (int i = 0; i < n + 2; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < 2; j++)
					cords[i][j] = Integer.parseInt(st.nextToken());
			}

			// 좌표를 기반으로 인접행렬 생성
			int[][] adjMatrix = makeAdjMatrix(n, cords);

			// 플로이드-워샬 알고리즘으로 각 지점을 거쳐서 도달할 수 있는 경로를 확인
			floyd(n, adjMatrix);

			// 출발지에서 도착지로 가는 경로가 존재하면 happy, 아니면 sad 출력
			sb.append(adjMatrix[0][n + 1] > 0 && adjMatrix[0][n + 1] < INF ? "happy" : "sad").append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 좌표를 기반으로 인접행렬 생성 - O(n^2)
	public static int[][] makeAdjMatrix(int n, int[][] cords) {
		int[][] adjMatrix = new int[n + 2][n + 2];

		// 거리가 1000 이내인 지점은 연결되었다고 볼 수 있다
		for (int i = 0; i < n + 2; i++) {
			for (int j = 0; j < n + 2; j++) {
				int dist = Math.abs(cords[i][0] - cords[j][0]) + Math.abs(cords[i][1] - cords[j][1]);

				if (i != j)
					adjMatrix[i][j] = dist <= 1000 ? 1 : INF;
			}
		}
		return adjMatrix;
	}

	// 각 지점을 거쳐서 도달할 수 있는 경로를 확인한다. - O(n^3)
	public static void floyd(int n, int[][] adjMatrix) {
		for (int k = 0; k < n + 2; k++) {
			for (int i = 0; i < n + 2; i++) {
				for (int j = 0; j < n + 2; j++) {
					if (j == i || j == k || i == k)
						continue;

					adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
				}
			}
		}
	}
}
