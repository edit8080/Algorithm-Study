/**
 * 1. 인접행렬을 입력받는다.
 * 2. 입력받은 인접행렬과 방문 여부를 통해 다음 노드로 이동한다.
 * 3. 탐색 도중의 합이 이미 결정된 최소값보다 크면 최소 후보가 될 수 없다. (가지치기)
 * 4. 모두 다 방문했고 회사로 가는 길이 있는 경우 최소값을 비교한다.
 * 
 * 주의) 모두 정점을 다 방문한 이후 해당 정점에서 회사(0)로 가는 길이 있는지 확인해야한다.
 */

import java.util.*;
import java.io.*;

public class Main_1681 {
	static int N;
	static int[][] adjMatrix;
	static boolean[] visited;
	static int minDist = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		adjMatrix = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; j++)
				adjMatrix[i][j] = Integer.parseInt(st.nextToken());
		}

		visited = new boolean[N];
		visited[0] = true;
		backtracking(0, 1, 0, new int[N+1]);
		
		System.out.println(minDist);
		br.close();
	}

	public static void backtracking(int curr, int cnt, int sum, int[] target) {
		// 모두 다 방문했고 회사로 가는 길이 있는 경우 최소값 비교
		if (cnt == N && adjMatrix[curr][0] != 0) {
			// 해당 지점에서 0으로 가는 경로의 가중치를 더함 -> 회사로 돌아오기	
			minDist = Math.min(minDist, sum+adjMatrix[curr][0]);
			return;
		}
		
		// 이미 결정된 최소값보다 크면 최소 후보가 될 수 없음
		if(minDist < sum) 
			return;		

		// 다음 노드 방문
		for (int i = 0; i < N; i++) {
			if (adjMatrix[curr][i] != 0 && !visited[i]) {
				visited[i] = true;
				target[cnt] = i;
				backtracking(i, cnt + 1, sum+adjMatrix[curr][i], target);
				visited[i] = false;
			}
		}
	}
}
