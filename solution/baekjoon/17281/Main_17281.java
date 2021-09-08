/**
 * 1. 순열을 통해 타자 순서를 결정한다. - O((p-1)!)
 * 2. 지정한 타자 순서에 따라 모든 이닝을 진행한다. - O(n*p)
 * 3. 모든 이닝이 끝나면 최대 점수를 비교한다.
 * 
 * 시간복잡도 = O(n*p*(p-1)!) (n: 이닝 수 <= 50, p: 선수 수 <= 9)
 */

import java.util.*;
import java.io.*;

public class Main_17281 {
	static final int pCnt = 9; // 야구 인원수
	static int idx; // 이닝 시작 선수 위치
	static int maxScore; // 가능한 최대 점수

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		int[][] inning = new int[n][pCnt];

		// 이닝 정보 입력
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int j = 0; j < pCnt; j++)
				inning[i][j] = Integer.parseInt(st.nextToken());
		}

		int[] pOrder = new int[pCnt]; // 타자 순서
		pOrder[0] = 3; // 1번 선수는 4번 타자

		int order = 0; // 순열을 위한 초기화
		for (int i = 1; i < pCnt; i++) {
			if (order == 3)
				order++;
			pOrder[i] = order++;
		}

		do {
			idx = 0;						
			
			// 타자 순서 결정 (ex : 4번째 치는 선수는 1번 선수)
			int[] player = new int[pCnt];
			for (int i = 0; i < pCnt; i++)
				player[pOrder[i]] = i;		
			
			// 지정한 타자 순서에 따라 모든 이닝 진행 - O(n*p) (n: 이닝 수 <= 50, p: 선수 수 <= 9)
			int total = 0;
			for (int i = 0; i < n; i++)
				total += baseball(inning[i], player);			

			// 모든 이닝이 끝나면 최대 점수 비교
			maxScore = Math.max(maxScore, total);
		}while (nextPermutaion(pOrder) && pOrder[0] == 3); // O((p-1)!) (p: 선수 수 <= 9 (1번 타자는 순서가 정해졌으므로))

		System.out.println(maxScore);
		br.close();
	}

	// 주어진 이닝 정보와 선수 순서에 따라 게임 진행 - O(3*p) (3진 아웃 * 한 번씩 진행)
	public static int baseball(int[] inning, int[] player) {
		int score = 0; // 해당 이닝 점수
		int out = 0; // 아웃 횟수
		boolean[] base = new boolean[4]; // 1루,2루,3루 정보

		while (true) {
			switch (inning[player[idx]]) {
			case 1: // 안타
				if (base[3]) { // 3루수 점수
					score++;
					base[3] = false;
				}
				for (int i = 2; i >= 1; i--) { // 1, 2루수 진출
					if (base[i]) {
						base[i] = false;
						base[i + 1] = true;
					}
				}
				base[1] = true; // 현재 타자 1루 진출
				break;
			case 2: // 2루타
				for (int i = 2; i <= 3; i++) { // 2, 3루수 점수
					if (base[i]) {
						score++;
						base[i] = false;
					}
				}
				if (base[1]) { // 1루수 진출
					base[1] = false;
					base[3] = true;
				}
				base[2] = true; // 현재 타자 2루 진출
				break;
			case 3: // 3루타
				for (int i = 1; i <= 3; i++) { // 1, 2, 3루수 점수
					if (base[i]) {
						score++;
						base[i] = false;
					}
				}
				base[3] = true;// 현재 타자 3루 진출
				break;
			case 4: // 홈런
				for (int i = 1; i <= 3; i++) { // 1, 2, 3루수 점수
					if (base[i]) {
						score++;
						base[i] = false;
					}
				}
				score++; // 현재 타자도 점수
				break;
			case 0: // 아웃
				out++;
				break;
			}
			
			// 다음 선수 설정
			idx = (idx + 1) % pCnt;

			// 3진 아웃시 종료
			if (out == 3) 
				return score;			
		}
	}

	// 다음 순열 생성
	public static boolean nextPermutaion(int[] arr) {
		int n = arr.length;

		int i = n - 1;
		while (i > 0 && arr[i - 1] >= arr[i])
			i--;

		if (i == 0)
			return false;

		int j = n - 1;
		while (arr[i - 1] >= arr[j])
			j--;

		swap(arr, i - 1, j);

		int k = n - 1;
		while (i < k)
			swap(arr, i++, k--);

		return true;
	}

	// 배열 원소 교환
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
}
