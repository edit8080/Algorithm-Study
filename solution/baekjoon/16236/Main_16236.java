/**
 * 1. 가장 가까이있는 물고기를 탐색하기 위해 BFS를 수행한다. - O(n^2)
 * 2. 이후 먹을 수 있는 물고기가 있다면 거리, 상, 좌의 위치를 기준으로 정렬한다. - O(n log n)
 * 3. 정렬하여 선택한 최적의 물고기를 먹고 난 이후 상어와 공간의 상태를 처리한다.
 * 4. 위 과정을 모든 물고기를 먹을 수 있을 때까지 반복한다. O(n^2)
 * 
 * 시간복잡도 = O(n^4) (n : 공간의 크기 <= 20)
 * 
 */

package com.ssafy.baekjoon;

import java.io.*;
import java.util.*;

public class Main_16236 {
	static int N;
	static int[][] area;
	static boolean[][] visited;
	static int[][] dir = { { -1, 0, 0, 1 }, { 0, -1, 1, 0 } }; // 상, 좌, 우, 하 탐색
	static int sharkX, sharkY, sharkSize = 2, sharkEat = 0; // 상어위치, 상어크기, 상어가 먹은 물고기 수
	static int time = 0; // 걸린 시간

	static class Cords implements Comparable<Cords> {
		int x, y, dist;

		Cords(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Cords(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		// 거리, 높이, 좌우 순 정렬
		@Override
		public int compareTo(Cords o) {
			int dComp = Integer.compare(this.dist, o.dist);
			int xComp = Integer.compare(this.x, o.x);
			return dComp != 0 ? dComp : (xComp != 0 ? xComp : Integer.compare(this.y, o.y));
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		area = new int[N][N];

		// 공간 정보 입력
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				area[i][j] = Integer.parseInt(st.nextToken());

				// 아기 상어 위치 찾기
				if (area[i][j] == 9) {
					sharkX = i;
					sharkY = j;
				}
			}
		}

		// 물고기 잡아먹기 - O(n^2 * n^2) (BFS탐색을 모든 물고기를 먹기 전까지 수행)
		while (eating());

		System.out.println(time);

	}

	// 가장 가까이있는 물고기를 탐색하기 위해 BFS를 수행한 후 정렬하여 물고기 섭취 - O(n^2)
	public static boolean eating() {
		visited = new boolean[N][N];
		Queue<Cords> q = new LinkedList<>();

		visited[sharkX][sharkY] = true;
		q.offer(new Cords(sharkX, sharkY));

		int dist = 0;
		List<Cords> fish = new ArrayList<>();
		
		// BFS 수행 - O(n^2)
		while (!q.isEmpty()) {
			int qSize = q.size();

			// 너비 기준 각 단계 탐색
			for (int i = 0; i < qSize; i++) {
				int x = q.peek().x;
				int y = q.poll().y;

				// 먹을 수 있는 물고기 찾은 경우
				if (area[x][y] > 0 && area[x][y] < sharkSize)
					fish.add(new Cords(x, y, dist));

				else {
					// 다음 단계 노드 탐색
					for (int j = 0; j < 4; j++) {
						int nextX = x + dir[0][j];
						int nextY = y + dir[1][j];

						if (inArea(nextX, nextY) && !visited[nextX][nextY] && area[nextX][nextY] <= sharkSize) {
							visited[nextX][nextY] = true;
							q.offer(new Cords(nextX, nextY));
						}
					}
				}
			}
			dist++;
		}
		// 먹을 수 있는 물고기를 찾지 못한 경우
		if (fish.size() == 0)
			return false;

		// 먹을 수 있는 물고기를 찾은 경우
		// -> 가장 가까이 있고, 가장 위쪽과 왼쪽에 있는 물고기를 찾기 위해 정렬 O(n log n)
		Collections.sort(fish);

		area[sharkX][sharkY] = 0; // 기존 상어 위치 초기화

		sharkX = fish.get(0).x;
		sharkY = fish.get(0).y; // 상어 이동
		time += fish.get(0).dist; // 이동 시간

		if (sharkSize == ++sharkEat) { // 물고기 섭취
			sharkSize++;
			sharkEat = 0;
		}
		area[fish.get(0).x][fish.get(0).y] = 0; // 공간에서 이미 먹은 물고기 제거

		return true;
	}

	// 공간 내에 있는지 확인
	public static boolean inArea(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}

}
