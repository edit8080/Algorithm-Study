/**
 * 1. 절댓값이 가장 큰 값 두개를 곱하여 최대 합을 계산한다.
 * 2. 절댓값이 가장 큰 두 값을 구할 때 우선순위 큐를 활용한다.
 * 주의) (음수*음수), (0*음수),  (n*1 vs n+1)처리를 주의한다. 
 * 
 * 시간복잡도 = O(n log n) (n: 입력 숫자 개수 <= 10000)
 */

import java.io.*;
import java.util.*;

public class Main_1744 {
	static PriorityQueue<Integer> plus = new PriorityQueue<>(Collections.reverseOrder()); // 양수 - 가장 큰 수로 정렬
	static PriorityQueue<Integer> minus = new PriorityQueue<>(); // 음수 - 가장 작은 수로 정렬

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());

		// 입력 - 우선순위큐 정렬 O(n log n)
		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(br.readLine());

			if (num > 0) // 양수
				plus.offer(num);
			else // 0, 음수 (마지막 남았을 때 0 * 음수가 더 큼)
				minus.offer(num);
		}
		System.out.print(highestSum());
		br.close();
	}

	// 가장 큰 합을 계산 - O(n)
	public static int highestSum() {
		int num1;
		int sum = 0;

		// 양수 처리
		while (!plus.isEmpty()) {
			num1 = plus.poll();

			// 하나 남은 경우 break
			if (plus.isEmpty()) {
				sum += num1;
				break;
			}
			// 남은 하나가 1인 경우 곱하는 것보다 더하는게 더 큰 합 구성
			else if (plus.peek() == 1) {
				sum += num1 + plus.poll();
				continue;
			}
			// 가장 작은 음수 2개 곱하기
			sum += num1 * plus.poll();
		}

		// 0, 음수 처리
		while (!minus.isEmpty()) {
			num1 = minus.poll();

			// 하나 남은 경우 break
			if (minus.isEmpty()) {
				sum += num1;
				break;
			}

			// 가장 작은 음수 2개 곱하기
			sum += num1 * minus.poll();
		}

		return sum;
	}
}
