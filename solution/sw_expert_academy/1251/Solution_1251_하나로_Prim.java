/**
 * 1. 입력받은 좌표를 통해 각 정점까지의 거리를 계산하고 해당 간선 정보를 인접 리스트에 저장한다.
 * 2. 프림 알고리즘을 위해 최소 거리 배열과 방문 여부 배열을 초기화한 후 시작점을 초기화한다.
 * 3. 각 탐색동안 최소 거리 배열을 탐색하면서 최소 거리를 가지는 정점을 선택한 후 방문 표시 및 비용(= 최소 거리 * 최소 거리)을 추가한다. - O(N)
 * 4. 선택한 최소 정점을 기반으로 인접한 정점간의 최소 거리 정보를 업데이트한다. - O(N)
 * 5. 각 정점은 모두 연결되어있으므로 3~4 과정을 N번 반복하면 신장 트리를 구성할 수 있다. - O(N^2)
 * 
 * 시간복잡도 = O(N^2) (N : 정점 수 <= 1000)
 */

import java.io.*;
import java.util.*;

public class Solution_1251_하나로_Prim {
	static class Node {
		int vertex;
		double dist;
		Node link;

		public Node(int vertex, double dist, Node link) {
			this.vertex = vertex;
			this.dist = dist;
			this.link = link;
		}
	}

	static int N; // 노드 수
	static double E; // 세율
	static Node[] adjList; // 인접 리스트
	static double[] minDist; // 최소 거리
	static boolean[] visited; // 방문 여부

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_1251.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			N = Integer.parseInt(br.readLine());
			if (N == 1) {
				sb.append('#').append(testCnt).append(0).append("\n");
				continue;
			}

			// 좌표값 저장
			long[][] cords = new long[2][N];

			for (int i = 0; i < 2; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					cords[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// 정점 정보 저장
			adjList = new Node[N];
			for (int from = 0; from < N; from++) {
				for (int to = from + 1; to < N; to++) {
					adjList[from] = new Node(to, dist(cords[0][from], cords[1][from], cords[0][to], cords[1][to]),
							adjList[from]);
					adjList[to] = new Node(from, dist(cords[0][to], cords[1][to], cords[0][from], cords[1][from]),
							adjList[to]);
				}
			}

			E = Double.parseDouble(br.readLine());

			minDist = new double[N];
			visited = new boolean[N];

			// 프림 알고리즘 초기화 (거리배열, 시작점 초기화)
			Arrays.fill(minDist, Double.MAX_VALUE);
			minDist[0] = 0;
			double cost = 0;

			// 신장트리 구성(프림) - O(V^2) 
			for (int i = 0; i < N; i++) { // 모든 정점이 연결되어있으므로 종료 조건이 필요하지 않음
				double min = Double.MAX_VALUE;
				int minVertex = -1;

				// 1. 최소 거리 정점 선택
				for (int j = 0; j < N; j++) {
					if (!visited[j] && minDist[j] < min) {
						min = minDist[j];
						minVertex = j;
					}
				}

				// 2. 방문 체크 및 비용 계산
				visited[minVertex] = true;
				cost += minDist[minVertex] * minDist[minVertex];

				// 3. 선택한 최소 정점을 기반으로 인접 정점까지의 거리 업데이트
				for (Node adj = adjList[minVertex]; adj != null; adj = adj.link) {
					if (!visited[adj.vertex] && adj.dist < minDist[adj.vertex])
						minDist[adj.vertex] = adj.dist;
				}
			}
			sb.append('#').append(testCnt).append(' ').append(Math.round(cost * E)).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 두 지점 사이의 거리 계산
	public static double dist(long x1, long y1, long x2, long y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
