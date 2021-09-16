import java.util.*;
import java.io.*;

public class Solution_3124_Kruskal {
	static class Edge {
		int from, to;
		long weight;

		Edge(int from, int to, long weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}

	static int V, E;
	static int[] parents; // 서로소 집합 정보
	static Edge[] edges; // 간선 정보

	public static void main(String[] args) throws Exception {
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_3124.txt")));
		StringBuffer sb = new StringBuffer();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			edges = new Edge[E];
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				long weight = Long.parseLong(st.nextToken());

				edges[i] = new Edge(from, to, weight);
			}

			// 가중치에 대해 간선 정렬
			Arrays.sort(edges, (a, b) -> Long.compare(a.weight, b.weight));

			// 서로소 집합 초기화
			make();

			// 크루스칼 수행
			long cost = 0;
			int cnt = 0;
			for (Edge edge : edges) {
				if (union(edge.from, edge.to)) {
					cnt++;
					cost += edge.weight;
				}
				if (cnt == V - 1)
					break;
			}
			sb.append('#').append(testCnt).append(' ').append(cost).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 서로소 집합 초기화
	public static void make() {
		parents = new int[V + 1];

		for (int i = 0; i <= V; i++)
			parents[i] = i;
	}

	// 서로소 집합 부모 찾기
	public static int find(int a) {
		if (a == parents[a])
			return a;

		return parents[a] = find(parents[a]);
	}

	// 서로소 집합 합치기
	public static boolean union(int a, int b) {
		int aBoss = find(a);
		int bBoss = find(b);

		if (aBoss == bBoss)
			return false;

		parents[bBoss] = aBoss;
		return true;
	}
}
