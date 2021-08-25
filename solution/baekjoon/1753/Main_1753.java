/**
 * 1. 간선 정보를 입력받아 인접리스트에 저장한다. 
 * 2. 다익스트라 알고리즘을 통해 시작점에서부터의 최단 거리를 계산한다.
 * 
 * 주의) 중복 간선을 처리하기 위해 루프를 돌며 체크하면 시간 초과가 발생한다.
 * 
 * 시간복잡도 = O(V^2) (V: 노드의 수 <= 20000)
 */

import java.io.*;
import java.util.*;

public class Main_1753 {
	static class Node {
		int vertex;
		int weight;
		Node link;

		public Node(int vertex, int weight, Node link) {
			this.vertex = vertex;
			this.weight = weight;
			this.link = link;
		}
	}

	static int V, E;
	static Node[] adjList;
	static boolean[] visited;
	static int[] dist;

	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());

		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		int start = Integer.parseInt(br.readLine());

		// 간선 정보 입력
		adjList = new Node[V + 1];

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjList[from] = new Node(to, weight, adjList[from]);
		}

		// 시작점에서부터의 최단 거리 계산
		dijkstra(start);

		// 시작점에서 각 지점까지의 최단 거리 경로값 출력
		for (int i = 1; i <= V; i++)
			sb.append(dist[i] == INF ? "INF" : dist[i]).append('\n');

		System.out.println(sb);
		br.close();
	}

	// 다익스트라 알고리즘을 통해 시작점에서부터의 최단 거리 계산 - O(V^2)
	public static void dijkstra(int start) {
		dist = new int[V + 1];
		visited = new boolean[V + 1];

		Arrays.fill(dist, INF);
		dist[start] = 0;

		for (int i = 1; i <= V; i++) {
			int min = INF;
			int minVertex = -1;

			// 1. 시작점에서 최단 거리를 갖는 정점 찾기
			for (int j = 1; j <= V; j++) {
				if (!visited[j] && dist[j] < min) {
					min = dist[j];
					minVertex = j;
				}
			}
			
			// 연결된 간선이 더 이상 존재하지 않음
			if(minVertex == -1)
				break;

			// 2. 경유지 설정 완료 표시
			visited[minVertex] = true;

			// 3. 최단 정점을 경유지로 하여 나머지 정점간의 거리 업데이트
			for (Node next = adjList[minVertex]; next != null; next = next.link) {
				if (!visited[next.vertex] && dist[next.vertex] > min + next.weight)
					dist[next.vertex] = min + next.weight;
			}
		}
	}
}
