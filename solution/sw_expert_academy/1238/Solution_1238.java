package com.ssafy.swea;

import java.io.*;
import java.util.*;

public class Solution_1238 {
	static int testCnt = 0;
	static int studentsNum = 101;
	static List<List<Integer>> graph;
	static int[] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_1238.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String line;
		while ((line = br.readLine()) != null) {
			testCnt++;
			StringTokenizer st = new StringTokenizer(line);
			int inputLen = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken());

			// 그래프 초기화
			graph = new ArrayList<>();
			for (int i = 0; i < studentsNum; i++)
				graph.add(new ArrayList<>());

			visited = new int[studentsNum];

			// 간선 정보 입력
			st = new StringTokenizer(br.readLine());			
			for (int i = 0; i < inputLen / 2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				// 간선 중복 제거
				if (!graph.get(from).contains(to))
					graph.get(from).add(to);
			}
			
			// 가장 멀리 떨어져있는 노드 중 최대 번호 탐색
			sb.append("#").append(testCnt).append(' ').append(maxNum(bfs(start))).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// BFS를 통해 최장 노드까지의 거리 탐색
	public static int bfs(int start) {
		Queue<Integer> q = new LinkedList<>();

		q.offer(start);
		visited[start] = 1;

		int maxDist = 0;

		while (!q.isEmpty()) {
			int curr = q.poll();
			int currDist = visited[curr];

			maxDist = Math.max(maxDist, currDist);

			// 다음 노드 탐색
			for (int i = 0; i < graph.get(curr).size(); i++) {
				int next = graph.get(curr).get(i);

				// 미방문 노드 이동
				if (visited[next] == 0) {
					visited[next] = currDist + 1;
					q.offer(next);
				}
			}
		}
		return maxDist;
	}

	// 최대 거리에 속한 노드 중 최대 번호값 반환
	public static int maxNum(int maxDist) {
		int ret = 0;

		for (int i = 1; i < studentsNum; i++) {
			if (visited[i] == maxDist)
				ret = Math.max(ret, i);
		}
		return ret;
	}

}
