/**
 * 1. 각 집합의 대표자를 자기 자신으로 초기화한다. - O(n) (n : 전체 원소 수 ≤ 1000000)
 * 2. 0번 연산에 대해 union 연산을 수행한다. - O(h)
 * 3. 1번 연산에 대해 find 연산을 통해 대표자를 서로 비교한다. - O(h)
 * 주의) path-compression 최적화 기법을 사용하지 않으면 시간복잡도가 O(m * n)이 되어 시간 초과가 발생한다. 
 * 
 * 시간복잡도 = O(m * h) (m : 연산 횟수 ≤ 100,000, h : 서로소 집합 트리 높이) 
 */

package com.ssafy.swea;

import java.io.*;
import java.util.*;

public class Solution_3289 {
	static int n, m; // 전체 원소 수, 연산 횟수
	static int[] parents;

	// 각 원소에 대해 서로소 집합 생성 - O(n)
	public static void make() {
		for (int i = 1; i <= n; i++)
			parents[i] = i;
	}
	
	// 대표자 찾기 - O(h) (h : a부터 대표자까지 높이)
	public static int find(int a) {
		if (parents[a] == a)
			return a;
		return parents[a] = find(parents[a]); // path-compression으로 높이 최적화
	}

	// 두 집합 합치기 (대표자 연결) - O(h) (h : a, b부터 대표자까지 높이)
	public static void union(int a, int b) {
		int aBoss = find(a);
		int bBoss = find(b);
		
		if(aBoss == bBoss)
			return;
		
		parents[bBoss] = aBoss;		
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_3289.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			parents = new int[n + 1];
			
			// 각 원소 대표자 초기화
			make();

			sb.append("#").append(testCnt).append(' ');
			
			// 연산 수행 - O(m)
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());

				int oper = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				switch (oper) {
				case 0:
					union(a, b);
					break;
				case 1:
					sb.append(find(a) == find(b) ? 1 : 0);						
					break;
				}			
			}
			sb.append('\n');
		}
		System.out.println(sb);
		br.close();
	}
}
