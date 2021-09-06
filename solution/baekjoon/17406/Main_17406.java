/**
 * 1. 모든 회전 연산의 인덱스를 기반으로 순열 생성 - O(k!)
 * 2. 순열을 통한 인덱스 순서에 따라 설정한 회전 연산과 입력받은 배열을 기반으로 배열 회전 시작 - O(n*m) 
 * 3. 모든 회전 연산에 대해 배열 회전을 완료하였다면 각 행의 최소값을 탐색 - O(n*m)
 * 
 * 시간복잡도 : O(k! * n*m) (k: 회전 연산 개수 <= 6, n: 배열 행 길이 <= 50, m: 배열 열 길이 <= 50) 
 */

import java.io.*;
import java.util.*;

public class Main_17406 {
	// 회전 연산
	static class rotate {
		int cX, cY, width;

		rotate(int cX, int cY, int width) {
			this.cX = cX;
			this.cY = cY;
			this.width = width;
		}
	}

	static int N, M, K;
	static int arr[][];
	static rotate rotArr[];
	static int minVal = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 행 크기
		M = Integer.parseInt(st.nextToken()); // 열 크기
		K = Integer.parseInt(st.nextToken()); // 회전 연산 개수

		arr = new int[N][M];

		// 배열 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++)
				arr[i][j] = Integer.parseInt(st.nextToken());
		}

		// 회전 연산 입력
		rotArr = new rotate[K];

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			rotArr[i] = new rotate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
		}

		int[] rotIdx = new int[K];
		for (int i = 0; i < K; i++)
			rotIdx[i] = i;

		do {
			// 원본 배열 복사
			int[][] copyArr = new int[N][M];
			for (int i = 0; i < N; i++)
				copyArr[i] = Arrays.copyOf(arr[i], M);

			// 회전 연산에 대한 인덱스 순열에 따라 배열 회전
			for (int i = 0; i < K; i++)
				rotateArr(copyArr, rotArr[rotIdx[i]]);

			// 최종 연산의 최소값 계산 - O(n*m)
			minRow(copyArr);

		} while (nextPermutation(rotIdx)); // 회전 연산에 대한 인덱스 순열 생성 - O(k!)

		// 출력
		System.out.println(minVal);
		br.close();
	}

	// 회전 연산의 순열 - O(k)
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

	// 배열 내 원소 교환
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 회전 연산을 기반으로 배열 회전 - O(n*m)
	public static void rotateArr(int[][] arr, rotate r) {
		int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

		for (int wid = 1; wid <= r.width; wid++) {
			int startX = r.cX - wid - 1;
			int startY = r.cY - wid - 1; // 시작 좌표
			int endX = r.cX + wid - 1;
			int endY = r.cY + wid - 1; // 끝 좌표

			// 시작 정보
			int currX = startX;
			int currY = startY + 1;
			int curr;

			int prev = arr[startX][startY]; // 이전 값
			int dirIdx = 0; // 탐색방향 인덱스

			while (true) {
				// 다음 노드가 기존 범위를 벗어나면 방향 전환
				if (currX + dir[dirIdx][0] < startX || currX + dir[dirIdx][0] > endX || currY + dir[dirIdx][1] < startY
						|| currY + dir[dirIdx][1] > endY)
					dirIdx += 1;

				// 4방향 회전 종료
				if (dirIdx == 4) {
					arr[startX][startY] = prev;
					break;
				}
				
				// 이전 값을 현재 위치에 저장
				curr = arr[currX][currY];
				arr[currX][currY] = prev;
				prev = curr;

				// 다음 탐색 방향 변경
				currX += dir[dirIdx][0];
				currY += dir[dirIdx][1];
			}

		}
	}

	// 배열 행의 최소값 계산 - O(n*m)
	public static void minRow(int[][] arr) {
		for (int i = 0; i < N; i++) {
			int sum = 0;
			// 행의 합 계산
			for (int j = 0; j < M; j++) 
				sum += arr[i][j];
			
			// 행의 최소값 비교
			minVal = Math.min(minVal, sum);
		}
	}
}
