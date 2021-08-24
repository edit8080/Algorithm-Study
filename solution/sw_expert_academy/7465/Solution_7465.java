/**
 * 1. 각 인원에 해당하는 서로소 집합을 구성한다. - O(n)
 * 2. 입력받은 관계에 따라 구성된 서로소 집합을 합친다. - O(m * h) (h: 대표자 트리 높이) 
 * 3. 마지막에 남은 서로소 집합의 개수를 센다. 서로소 집합의 개수는 대표자의 수와 같다. - O(n)
 * 
 * 시간복잡도 - O(m * h) (n: 인원 수 <= 100, m: 관계 수 <= n(n-1)/2)
 */

import java.util.*;
import java.io.*;

public class Solution_7465 {
	static int n, m;
	static int[] parents;
	static boolean[] isChecked;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_7465.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			parents = new int[n + 1];

			// 서로소 집합 만들기 - O(n)
			make();

			// 관계에 따라 집합 합치기 - O(m * h)
			for (int j = 0; j < m; j++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				union(a, b);
			}

			// 집합 개수 세기 (대표자 수 체크) - O(n)
			int setCnt = 0;
			isChecked = new boolean[n + 1];

			for (int j = 1; j <= n; j++) {
				int boss = find(j);
				if (!isChecked[boss]) {
					setCnt++;
					isChecked[boss] = true;
				}
			}
			sb.append('#').append(testCnt).append(' ').append(setCnt).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 서로소 집합 만들기 - O(n)
	public static void make() {
		for (int i = 1; i <= n; i++)
			parents[i] = i;
	}

	// 대표자 찾기 - O(h)
	public static int find(int a) {
		if (a == parents[a])
			return a;
		return parents[a] = find(parents[a]); // 최적화 - path-compression
	}

	// 두 서로소 집합 합치기 - O(h)
	public static void union(int a, int b) {
		int aBoss = find(a);
		int bBoss = find(b);

		if (aBoss == bBoss)
			return;

		parents[bBoss] = aBoss;
	}
}
