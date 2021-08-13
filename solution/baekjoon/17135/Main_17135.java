/**
 * 1. 세 궁수의 y 자리를 조합을 통해 설정한다. - O(m^2)
 * 2. 각 조합의 경우에서 궁수의 행을 기준으로 적을 탐색한다. 탐색은 궁수의 행이 끝까지 도달할 때(> 0)까지 탐색한다. - O(n)
 *    (적들이 성으로 전진 = 궁수들이 적으로 전진) 
 * 3. 궁수와 적과의 거리가 d 이내이고 가장 작은 거리를 가진 적을 타겟으로 설정한다. - O(n * m)
 * 4. 타겟 설정이 완료되었다면 해당 적을 사살하였다고 표기한다. (iskilled = true) -> 중복 타겟 설정이 가능하여 이와 같이 작업
 * 5. 다음 탐색에 영향을 주지 않기 위해 사살한 적과 지나친 적 정보를 제거한다.
 * 6. 2조건을 만족할 때까지 3~5 과정을 반복한다.
 * 
 * 시간복잡도 = O(n^2 * m^3) (n: 행 길이 <=15, m: 열 길이 <=15)
 */

import java.util.*;
import java.io.*;

public class Main_17135 {
	static int n, m, d;
	static int[][] field;
	static boolean[][] iskilled;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 입력
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());

		field = new int[n][m];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				field[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 자리 조합 준비
		final int archerCnt = 3;
		int[] selected = new int[m];

		for (int i = 0; i < archerCnt; i++)
			selected[(m - 1) - i] = 1;

		// 각 궁수의 자리 조합에 대한 최대 킬 수
		int maxSumKill = 0;
		do {
			int archerRow = n;
			int sumKill = 0;
			iskilled = new boolean[n][m];

			// 적 위치 복사
			int[][] fieldCopy = new int[n][m];
			for (int i = 0; i < n; i++)
				fieldCopy[i] = Arrays.copyOf(field[i], m);

			// 적이 성으로 전진 = 궁수가 적으로 전진 (끝까지 도달할 때까지) - O(n)
			while (archerRow > 0) {
				List<Integer> archerPos = new ArrayList<>(); // 궁수 y 위치

				for (int i = 0; i < m; i++) {
					if (selected[i] == 1)
						archerPos.add(i);
				}
				// 현 위치에서 적 사살 - O(n * m)
				killEnemy(fieldCopy, archerPos, archerRow--);
			}

			// 죽은 적 카운팅
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (iskilled[i][j])
						sumKill++;
				}
			}
			// 최대 킬 수 비교
			maxSumKill = Math.max(maxSumKill, sumKill);
		} while (nextPermutation(selected)); // 궁수 자리 조합 - O(m^2) (m : 열 길이)

		System.out.println(maxSumKill);
	}

	// 현 위치에서 적 사살 - O(n * m) (n * m : 필드 넓이)
	public static void killEnemy(int[][] field, List<Integer> archerPos, int archerRow) {
		// 각 궁수 시점 적 타겟 설정
		for (int i = 0; i < archerPos.size(); i++) {
			int ePosX = -1;
			int ePosY = -1;
			int minDist = Integer.MAX_VALUE;

			// 필드 탐색 (왼쪽부터 탐색) - O(n * m)
			for (int y = 0; y < m; y++) {
				for (int x = 0; x < n && x < archerRow; x++) {
					// 적 존재 시
					if (field[x][y] == 1) {
						int distance = Math.abs(x - archerRow) + Math.abs(y - archerPos.get(i));
						// d 이내에 있는 적 중 최소 거리에 있는 적이 타겟 (중복 설정 가능)
						if (distance <= d && distance < minDist) {
							ePosX = x;
							ePosY = y;
							minDist = distance;
						}
					}
				}
			}
			// 타겟 존재시 사살
			if (ePosX != -1 || ePosY != -1)
				iskilled[ePosX][ePosY] = true;
		}

		// 죽은 적 제거
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (iskilled[i][j])
					field[i][j] = 0;
			}
		}

		// 지나친 적 제거
		for (int y = 0; y < m; y++)
			field[archerRow - 1][y] = 0;
	}

	// 다음 순열 - O(m^2) (m: 열 길이)
	public static boolean nextPermutation(int[] arr) {
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

	// 교환
	public static void swap(int arr[], int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
