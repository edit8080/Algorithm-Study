
/**
 * 1. 초기값을 설정하기 위해 0번째 값을 기본 합(val)으로 설정하고 1번째 인덱스를 기준으로 탐색한다.
 * 	  A+B
 *     ↑
 *   초기idx
 *     
 * 2. 탐색은 val <- (val+B) 와 같은 형태(이전 결과 포함해 연산)와 val <- val+(B+C) 형태(이전 결과 + 다음 연산 결과)로 진행한다.
 * 3. 탐색을 마치고 이후의 연산을 수행하기 위해 인덱스 위치를 알맞게 변경한다. => 첫 번째 연산은 idx+2, 두 번째 연산은 idx+4 위치
 * 4. 인덱스의 위치가 전체 식을 벗어날 때까지 위 과정을 반복한다.
 * 
 * 주의) 1. (A+B)+C와 A+(B+C) 두 가지 형태로 탐색하면 (A+B)+(C+D) 는 1->2 탐색에 의해 확인할 수 있다.
 *       2. 연산자 우선순위가 없고 앞에서 부터 탐색하는 것에 유의한다. 
 *       3. 순차탐색은 (((A+B)+C)+D) 로 나타낼 수 있고 이것은 1번 탐색을 계속 반복하는 것과 동일하다.
 *       4. A*B-C+D*E 의 탐색은 ((((A*B)-C)+D)*E), ((A*((B-C)+D))*E), ((A*(B-C))+(D*E)), (((A*B)-(C+D))*E), (((A*B)-C)+(D*E)) 으로 총 5가지 경우다.
 *          => ★★ A*B-(C+D)*E 는 사실 (A*B)-(C+D)*E와 같다.           
 * 
 * 시간복잡도 = O(2^n) (n: 수식의 길이 <= 19)
 */

import java.io.*;

public class Main_16637 {
	static int N;
	static String input;
	static int max = Integer.MIN_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		input = br.readLine();

		setBracket(1, input.charAt(0) - '0');
		System.out.println(max);
	}

	// 괄호를 씌워서 수식 계산 - O(2^n)
	public static void setBracket(int idx, int val) {
		if (idx >= N) {			
			max = Math.max(max, val);
			return;
		}
		// 1) (A+B)+C+...: 현재 자신을 포함해 연산
		setBracket(idx + 2, calculate(val, input.charAt(idx), input.charAt(idx + 1) - '0'));

		if (idx + 3 < N) {
			// 2) A+(B+C)+...: 현재 자신과 다음 연산과 연산
			setBracket(idx + 4, calculate(val, input.charAt(idx),
					calculate(input.charAt(idx + 1) - '0', input.charAt(idx + 2), input.charAt(idx + 3) - '0')));
		}
	}

	// 수식 계산
	public static int calculate(int num1, char oper, int num2) {
		switch (oper) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		}
		return Integer.MIN_VALUE;
	}
}
