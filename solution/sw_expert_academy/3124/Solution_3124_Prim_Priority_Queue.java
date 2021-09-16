import java.util.*;
import java.io.*;

public class Solution_3124_Prim_Priority_Queue {
	static class Node {
		int to, weight;
		Node link;

		Node(int to, int weight, Node link) {
			this.to = to;
			this.weight = weight;
			this.link = link;
		}
	}

	static int V, E;
	static Node[] nodelist;

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

			// 정점 리스트 입력
			nodelist = new Node[V + 1];
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());

				nodelist[from] = new Node(to, weight, nodelist[from]);
				nodelist[to] = new Node(from, weight, nodelist[to]);
			}

			boolean[] visited = new boolean[V + 1]; // 방문 여부
			PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return Long.compare(o1[1], o2[1]); // weight를 기준으로 정렬
				}
			}); // 다음 노드 정보 (next Vertex, weight)

			int[] minDist = new int[V + 1];
			Arrays.fill(minDist, Integer.MAX_VALUE);
			
			long cost = 0;
			int cnt = 0;
			pq.offer(new int[] { 1, 0 });

			// 다음 탐색할 노드를 우선순위 큐를 통해 판단 - O(E log E) = O(E log V) (E <= V*(V-1)/2)
			while (!pq.isEmpty()) {
				int curr = pq.peek()[0]; // 현재 기준으로 삼을 정점
				int weight = pq.poll()[1]; // 선택한 정점의 가중치

				if (visited[curr])
					continue;

				cost += weight;
				visited[curr] = true;

				// 신장트리 구성 완료
				if (cnt++ == V - 1)
					break;

				// 정점과 연결된 다음 정점을 판단 (간선 기준)
				for (Node tmp = nodelist[curr]; tmp != null; tmp = tmp.link) {
					if (!visited[tmp.to]) 
						pq.offer(new int[] { tmp.to, tmp.weight });					
				}
			}
			sb.append('#').append(testCnt).append(' ').append(cost).append('\n');
		}
		System.out.println(sb);
		br.close();
	}
}
