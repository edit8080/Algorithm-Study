/**
 * 1. 학생의 키 순에 대한 간선 정보 입력받을 때, 정방향과 역방향을 따로 저장한다.
 * 2. 정방향 인접행렬과 역방향 인접행렬로 플로이드-와셜 알고리즘을 적용해 각 노드에서 다른 노드로 갈 수 있는 경로를 구한다. - O(N^3)
 * 3. 플로이드-와셜의 결과가 INF가 아니라면 갈 수 있는 경로가 존재하는 것이기 때문에 현 학생보다 키가 큰 학생, 키가 작은 학생의 수를 구할 수 있다.
 * 4. 현 학생을 기준으로 판별된 키가 큰 학생과 키가 작은 학생의 합이 N-1 명이라면 현 학생의 순위를 알 수 있다. 
 *    -> 위 과정을 모든 학생에 적용하여 순위를 알 수 있는 모든 학생의 수를 카운팅한다. - O(T)
 * 
 * 시간복잡도 = O(T * N^3) (T: 테스트케이스 개수 <= 15, N : 학생 수 <= 500)
 */

import java.io.*;
import java.util.*;

public class Solution_5643 {
	static final int INF = Integer.MAX_VALUE / 2;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_5643.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			N = Integer.parseInt(br.readLine());
			int[][] frontMatrix = new int[N][N];
			int[][] backMatrix = new int[N][N];

			for (int i = 0; i < N; i++) {
				Arrays.fill(frontMatrix[i], INF);
				Arrays.fill(backMatrix[i], INF);
			}

			// 간선 정보 입력
			int M = Integer.parseInt(br.readLine());
			for (int i = 0; i < M; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				int from = Integer.parseInt(st.nextToken()) - 1;
				int to = Integer.parseInt(st.nextToken()) - 1;

				frontMatrix[from][to] = 1;
				backMatrix[to][from] = 1;
			}

			floydWashall(frontMatrix);
			floydWashall(backMatrix);

			int[] frontCnt = new int[N];
			int[] backCnt = new int[N];

			// 결과가 INF가 아니라면 갈 수 있는 경로가 존재하는 것이기 때문에 현 학생보다 키가 큰 학생, 키가 작은 학생의 수를 구할 수 있다.
			for (int i = 0; i < N; i++) {
				int front = 0;
				int back = 0;

				for (int j = 0; j < N; j++) {
					if (frontMatrix[i][j] != INF)
						front++;
					if (backMatrix[i][j] != INF)
						back++;
				}
				frontCnt[i] = front;
				backCnt[i] = back;
			}			

			// (앞에 있는 학생 + 뒤에 있는 학생)이 N-1명이 있다는 것을 파악하면 본인의 위치를 알 수 있음
			int check = 0;
			for (int i = 0; i < N; i++) {
				if (frontCnt[i] + backCnt[i] == N - 1)
					check++;
			}
			sb.append('#').append(testCnt).append(' ').append(check).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 플로이드-와셜로 각 노드에서 다른 노드로 갈 수 있는 경로를 구한다.
	public static void floydWashall(int[][] adjMatrix) {
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (k == i || k == j || i == j)
						continue;

					adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
				}
			}
		}
	}
}
