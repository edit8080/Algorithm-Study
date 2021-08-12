/**
 * 1. n개의 데이터의 부분집합을 구한다.
 * 2. 부분집합 중 신맛 곱, 쓴맛 합의 최소 차이를 구한다.
 * 3. 신맛 곱을 계산하는 중 long형 overflow가 발생할 수 있으므로 최대 bound를 설정한다.
 *    ->  최대 bound = 10^18
 * 
 * 시간복잡도 = O(2^n)
 */
import java.io.*;
import java.util.*;

public class Main_2961 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		int[] sour = new int[n];
		int[] bitter = new int[n];

		// 정보 입력
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			sour[i] = Integer.parseInt(st.nextToken());
			bitter[i] = Integer.parseInt(st.nextToken());
		}
		System.out.println(subSet(n, sour, bitter));
	}

	// 부분 집합 - O(2^n)
	public static long subSet(int n, int[] sour, int[] bitter) {
		long bound = (long) Math.pow(10, 18);
		long minDiff = bound;
		
		for (int i = 1; i < (1 << n); i++) {
			long sourMul = 1;
			long bitterSum = 0;
			boolean check = true;

			for (int j = 0; j < n; j++) {
				if ((i & (1 << j)) != 0) {
					sourMul *= sour[j];
					bitterSum += bitter[j];
					
					// long 범위 초과 방지
					if (Math.abs(sourMul - bitterSum) > bound) {
						check = false;
						break;
					}
				}
			}			
			
			// 정상적 결과면 최소 차이 비교
			if (check)
				minDiff = Math.min(minDiff, Math.abs(sourMul - bitterSum));			
		}
		return minDiff;
	}
}
