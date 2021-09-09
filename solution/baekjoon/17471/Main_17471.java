/**
 * 1. 인원수 정보와 그래프 정보를 저장한다.
 * 2. 부분집합을 통해 2개의 부분집합을 구성한다. - O(2^n)
 * 3. 구성된 부분집합 내 원소가 0개가 아니라면 DFS를 통해 부분집합 내 원소가 모두 연결되어있는지 확인한다. - O(n/2 * n/2) * 2
 * 
 * 주의) DFS 탐색 때 구성된 부분집합에 포함된 원소끼리만 움직인다.
 * 
 * 시간복잡도 = O(2^n * (n^2) / 2) (n: 정점의 개수 <= 10)
 */

import java.util.*;
import java.io.*;

public class Main_17471 {
	static class Node {
		int to;
		Node link;

		Node(int to, Node link) {
			this.to = to;
			this.link = link;
		}
	}

	static int N; // 정점의 개수
	static int[] pCnt; // 인원 수
	static Node[] graph; // 그래프 정보
	static List<Integer> aSet, bSet; // 두 부분집합
	static int aCnt, bCnt; // 두 부분집합의 인원 수 합
	static int minDiff = Integer.MAX_VALUE; // 최소 차이

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		pCnt = new int[N + 1];

		// 각 정점의 인구수 설정
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			pCnt[i] = Integer.parseInt(st.nextToken());
		}

		// 간선 정보 입력
		graph = new Node[N + 1];

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken());

			for (int j = 0; j < t; j++) {
				int to = Integer.parseInt(st.nextToken());
				graph[i] = new Node(to, graph[i]);
			}
		}

		// 부분집합을 통해 두 영역으로 나눌 수 있는 모든 경우를 탐색 - O(2^n) (n: 정점의 개수 <= 10)
		subset(new boolean[N + 1], 1);

		// 두 부분으로 나눌 수 없다면 -1을 출력
		System.out.println(minDiff == Integer.MAX_VALUE ? -1 : minDiff);
	}

	// 부분집합을 통해 두 영역으로 나눌 수 있는 모든 경우를 탐색 - O(2^n)
	public static void subset(boolean[] set, int curr) {
		if (curr == N + 1) {
			aSet = new ArrayList<>();
			bSet = new ArrayList<>();
			aCnt = bCnt = 0;

			int aStart = -1, bStart = -1;

			// 두 영역으로 나누기
			for (int i = 1; i <= N; i++) {
				if (set[i]) {
					aSet.add(i);
					aStart = i;
					aCnt += pCnt[i];
				} else {
					bSet.add(i);
					bStart = i;
					bCnt += pCnt[i];
				}
			}

			// 두 영역으로 나눌 수 없다면 탐색 종료
			if (aSet.size() == 0 || bSet.size() == 0)
				return;

			// DFS 로 영역이 모두 연결되어 있는지 확인 - O(n/2 * n/2) (n: 정점의 개수 <= 10)
			boolean[] visited = new boolean[N + 1];
			dfs(aSet, visited, aStart);

			// 모두 연결되어 있지 않다면 탐색 종료
			for (int i = 0; i < aSet.size(); i++) {
				if (!visited[aSet.get(i)])
					return;
			}

			// 나머지 영역도 동일하게 탐색
			visited = new boolean[N + 1];
			dfs(bSet, visited, bStart);
			for (int i = 0; i < bSet.size(); i++) {
				if (!visited[bSet.get(i)])
					return;
			}
			minDiff = Math.min(minDiff, Math.abs(aCnt - bCnt));
			return;
		}
		// 선택 o
		set[curr] = true;
		subset(set, curr + 1);
		set[curr] = false;

		// 선택 x
		subset(set, curr + 1);
	}

	// DFS로 방문 여부 표기 - O(n/2 * n/2)
	public static void dfs(List<Integer> set, boolean[] visited, int x) {
		visited[x] = true;

		for (Node tmp = graph[x]; tmp != null; tmp = tmp.link) {
			if (set.contains(tmp.to) && !visited[tmp.to])
				dfs(set, visited, tmp.to);
		}
	}
}
