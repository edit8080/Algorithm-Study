/**
 * 1. 패턴 문자열에서 부분 접두사와 접미사 일치 배열인 실패 배열을 구성한다. - O(M)
 * 2. 구성한 실패 배열을 통해 타겟 문자열과 일치 여부를 파악한다. - O(N)
 * 3. 타겟 문자열이 일치하는 부분 문자열을 발견한다면 갯수를 카운팅하고 위치를 저장한다. 
 *    - 위치 : (일치여부를 판단한 마지막 위치) - (패턴 문자열 길이) + 1 (-> 1부터 시작)
 * 
 * 시간복잡도 = O(M+N) (M : 패턴 문자열의 길이, N : 타겟 문자열의 길이) 
 */

import java.util.*;
import java.io.*;

public class Main_1786 {
	static int cnt = 0;
	static List<Integer> pos = new ArrayList<Integer>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		char[] target = br.readLine().toCharArray();
		char[] pattern = br.readLine().toCharArray();

		// KMP 알고리즘으로 패턴 문자열이 일치하는 부분을 탐색한다. - O(M+N)
		KMP(target, pattern);

		sb.append(cnt).append('\n');
		for (int p : pos)
			sb.append(p).append('\n');

		System.out.println(sb);
		br.close();
	}

	public static void KMP(char[] target, char[] pattern) {
		int[] pi = new int[pattern.length];

		// 부분 접미사, 접두사 일치 배열(실패 배열) 만들기
		// i : 접미사 포인터, j : 접두사 포인터
		for (int i = 1, j = 0; i < pattern.length; i++) {
			// 두 문자가 동일하지 않다면 부분 배열을 통해 탐색 문자열 이동
			while (j > 0 && pattern[i] != pattern[j])
				j = pi[j - 1];

			// 접미사와 접두사가 일치하는 경우
			if (pattern[i] == pattern[j])
				pi[i] = ++j;
		}

		// i : 텍스트 포인터, j : 패턴 포인터
		for (int i = 0, j = 0; i < target.length; i++) {
			// 두 문자가 동일하지 않다면 부분 배열을 통해 탐색 문자열 이동
			while (j > 0 && target[i] != pattern[j])
				j = pi[j - 1];

			if (target[i] == pattern[j]) {
				// 패턴의 마지막까지 일치하는 경우
				if (j == pattern.length - 1) {
					cnt++;
					pos.add(i - j + 1);
					j = pi[j];
				}
				// 패턴 중간에서 일치하는 경우
				else {
					++j;
				}
			}
		}
	}
}
