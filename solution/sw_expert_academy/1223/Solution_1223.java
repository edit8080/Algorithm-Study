/**
 * 1. 연산자 종류와 연산자에 대한 우선순위를 설정한다.
 * 2. 중위 표기식을 후위 표기식으로 변환한다. - O(n) (n: 중위 표기식의 길이)
 *    - 연산자인 경우
 *    	- 연산자 스택이 비어있거나 현재 연산자의 우선순위가 연산자 스택 top의 우선순위보다 높다면 연산자 스택에 저장한다.
 *      - 연산자 스택 top의 우선순위가 현재 연산자보다 같거나 높으면 해당 조건을 만족하지 않을때까지 pop하면서 후위 표기식에 추가한다.
 *        이후 현재 연산자를 연산자 스택에 저장한다.
 *    - 피연산자인 경우 후위 표기식에 추가
 *    - 최종적으로 마지막에 연산자 스택에 남은 연산자들 모두를 후위 표기식에 추가한다.   
 *    
 * 3. 변환한 후위 표기식을 계산한다. - O(n) (n: 중위(=후위) 표기식의 길이)
 *    - 피연산자는 피연산자 스택에 저장한다.
 *    - 연산자를 만나면 피연산자 스택의 상위 2개의 피연산자를 꺼내어 연산한 후 결과를 피연산자 스택에 저장한다.
 * 
 * 4. 마지막에 피연산자 스택에 남은 값을 출력한다.
 *  
 * 시간복잡도 = O(n) (n: 중위 표기식의 길이)
 */

import java.util.*;
import java.io.*;

public class Solution_1223 {
	static String oper = "+*"; // 연산자 종류
	static Map<Character, Integer> operPriority = new HashMap<Character, Integer>() {
		{
			put('+', 0);
			put('*', 1);
		}
	}; // 연산자 우선순위

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_1223.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int t = 10;

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			br.readLine();
			String express = br.readLine();

			String postfixExpress = postfix(express);
			sb.append("#").append(testCnt).append(' ').append(operation(postfixExpress)).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 중위 표기식을 후위 표기식으로 변환 - O(n) (n: 중위 표기식의 길이)
	public static String postfix(String express) {
		Stack<Character> s = new Stack<>();
		StringBuilder postfixExpress = new StringBuilder();

		for (int i = 0; i < express.length(); i++) {
			char currChar = express.charAt(i);
			// 연산자
			if (oper.indexOf(currChar) != -1) {
				// 1. 스택이 비어있거나 현재 연산자의 우선순위가 높으면 스택에 삽입
				if (s.isEmpty() || operPriority.get(s.peek()) < operPriority.get(currChar)) {
					s.push(currChar);
					continue;
				}
				// 2. 스택에 저장된 값이 현재 연산자의 우선순위보다 높거나 동일하면 표기식에 추가
				while (!s.isEmpty() && operPriority.get(s.peek()) >= operPriority.get(currChar))
					postfixExpress.append(s.pop());
				
				// 이후 현재 연산자를 스택에 저장
				s.push(currChar);
			}

			// 피연산자
			else
				postfixExpress.append(currChar);
		}
		// 남아있는 연산자를 표기식에 추가
		while (!s.isEmpty())
			postfixExpress.append(s.pop());

		return postfixExpress.toString();
	}

	// 후위 표기식 계산 - O(n) (n: 후위 표기식의 길이)
	public static int operation(String postfixExpress) {
		Stack<Integer> s = new Stack<>();

		for (int i = 0; i < postfixExpress.length(); i++) {
			char currChar = postfixExpress.charAt(i);
			
			// 연산자 - 계산
			if (oper.indexOf(currChar) != -1)
				s.push(operator(currChar, s.pop(), s.pop()));
			// 피연산자 - 스택에 삽입
			else
				s.push(currChar - '0');
		}
		return s.pop();
	}

	// 연산자 계산
	public static int operator(char oper, int num1, int num2) {
		switch (oper) {
		case '+':
			return num1 + num2;
		case '*':
			return num1 * num2;
		}
		return -1;
	}

}
