/**
 * 1. 입력받은 좌표를 통해 각 정점까지의 거리를 계산하고 해당 간선 정보를 간선 리스트에 저장한다.
 * 2. 크루스칼 알고리즘을 위해 간선 정보를 정렬한다. - O(E log E)
 * 3. 최소 가중치 간선 정보에 기반해 정점을 합친다.
 * 4. 간선의 개수가 N-1개이면 신장 트리 구성이 완료된 것으로 크루스칼 수행을 종료한다.
 * 
 * 시간복잡도 = O(E log E) = O(N^2 log N) (N : 정점 수 <= 1000, E : 간선 수(=N*(N-1))) = 10^7
 */

import java.io.*;
import java.util.*;

public class Solution_1251_하나로_Kruskal {
	static class Edge implements Comparable<Edge> {
		int from, to;
		double weight;

		public Edge(int from, int to, double weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return Double.compare(this.weight, o.weight);
		}
	}

	static int N; // 노드 수
	static double E; // 세율
	static Edge[] edgeList; // 간선리스트
	static int[] parents; // 대표자 배열

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

			edgeList = new Edge[N * (N - 1) / 2]; // 모든 간선 수 E = N * (N - 1)

			// 좌표값 저장
			long[][] cords = new long[2][N];
			
			for (int i = 0; i < 2; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					cords[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			E = Double.parseDouble(br.readLine());

			// 모든 간선 정보 저장
			int idx = 0;
			for (int i = 0; i < N; i++) {
				for (int j = i + 1; j < N; j++) {
					double d = dist(cords[0][i], cords[1][i], cords[0][j], cords[1][j]);
					edgeList[idx++] = new Edge(i, j, d);
				}
			}

			// 서로소 집합 생성
			make();

			// 크루스칼을 위해 모든 간선 정렬 - O(E log E)
			Arrays.sort(edgeList);

			// 신장트리 구성(크루스칼)
			int cnt = 0; // 신장트리 구성 노드 수
			double cost = 0; // 구성 비용
			
			for (int i = 0; i < edgeList.length; i++) {
				// 신장트리 구성 완료
				if (cnt == N - 1)
					break;

				// 두 지점 연결
				if (union(edgeList[i].from, edgeList[i].to)) {
					cnt++;
					cost += edgeList[i].weight * edgeList[i].weight;
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

	/* 유니온 파인드 */

	// 서로소 집합 생성
	public static void make() {
		parents = new int[N];

		for (int i = 0; i < N; i++)
			parents[i] = i;
	}

	// 대표자 찾기 - O(N)
	public static int find(int a) {
		if (a == parents[a])
			return a;

		return parents[a] = find(parents[a]); // path-compression
	}

	// 두 서로소 집합 합치기
	public static boolean union(int a, int b) {
		int aBoss = find(a);
		int bBoss = find(b);

		if (aBoss == bBoss)
			return false;

		parents[bBoss] = aBoss;
		return true;
	}
}
