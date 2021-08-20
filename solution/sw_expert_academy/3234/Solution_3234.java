/**
 * 1. nextPermutation을 통해 무게추(weight)의 순열을 생성한다. 
 * 2. 생성한 순열을 기반으로 무게추 전부에 대해 왼쪽에 배치할지, 오른쪽에 배치할지에 따라 부분집합을 구성한다.
 * 3. 부분집합을 구성할 때 오른쪽 무게추들의 무게가 왼쪽 무게추들의 무게보다 커지면 부분집합 구성을 중단한다. - 백트래킹
 */

import java.util.*;
import java.io.*;

public class Solution_3234 {
	static int n, cnt; // 무게추 개수, 경우의 수

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_3234.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			n = Integer.parseInt(br.readLine());
			cnt = 0;
			int[] weight = new int[n];

			// 무게추 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++)
				weight[i] = Integer.parseInt(st.nextToken());

			Arrays.sort(weight);

			// 1. nextPermutation을 통한 다음 순열 생성 - 총 O(n!)
			do {
				subSet(weight, 0, 0, 0);
			} while (nextPermutation(weight));			
			
			sb.append("#").append(testCnt).append(' ').append(cnt).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 다음 순열 생성 - O(n)
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
		while (i < k) {
			swap(arr, i++, k--);
		}

		return true;
	}
	
	// 배열 원소 교환
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	/**
	 * weight 순열에 대해 부분집합 구성
	 * 
	 * @param weight : nextPermutation으로 구성된 weight의 순열
	 * @param curr : 판단할 무게추 위치
	 * @param leftSum : 양팔 저울의 왼쪽 합
	 * @param rightSum:  양팔 저울의 오른쪽 합
	 */
	public static void subSet(int[] weight, int curr, int leftSum, int rightSum) {
		// 3. 오른쪽 위에 올라가는 무게의 총합이 더 커져서는 안됨 - 백트래킹
		if (leftSum < rightSum)
			return;

		// 서브셋 구성 가능 
		if (curr == n) {
			cnt++;
			return;
		}

		// 2-1. 무게추를 왼쪽에 배치
		subSet(weight, curr + 1, leftSum + weight[curr], rightSum);

		// 2-2. 무게추를 오른쪽으로 배치
		subSet(weight, curr + 1, leftSum, rightSum + weight[curr]);
	}
}
