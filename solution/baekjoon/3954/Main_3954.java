/**
 * 1. 입력받은 명령어 중 괄호 짝 위치를 빠르게 탐색하기 위해 여는 괄호와 닫는 괄호의 짝의 위치를 파악한다.
 * 2. 전체 명령어의 기능에 맞게 동작을 수행한다.
 * 3. 만약 제한 횟수 이상으로 연산을 수행중이라면 무한루프이고, 그에 맞는 괄호의 위치를 함께 출력한다.
 * 
 * 주의) - 문제의 제한횟수는 5*10^7 번 이지만 무한루프 내 유한루프의 연산횟수가 정확히 5*10^7 번 이라면 이를 무한루프로 탐지할 가능성이 있음
 *       따라서, 무한루프내 유한루프가 있어 동작할 최대가 5*10^7 * 2번이므로 10^8 번까지 탐색하여 무한루프 여부를 확인한다.
 *       - 무한루프에 해당하는 괄호에서 여는 괄호는 프로그램 인덱스의 최솟값이다. 
 *       
 * 시간복잡도 = O(n) (n : 명령어 수행 시간 <= 10^8) 
 */


import java.util.*;
import java.io.*;

public class Main_3954 {
	static int M, C, I;
	static String instruct;
	static String input;
	static int[] openBracket; // 현 위치에서 닫는 괄호의 위치
	static int[] closeBracket; // 현 위치에서 여는 괄호의 위치
	static int bracketIdx;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());

		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			I = Integer.parseInt(st.nextToken());

			instruct = br.readLine();
			input = br.readLine();

			openBracket = new int[C];
			closeBracket = new int[C];

			// 괄호 위치 미리 세팅
			for (int i = 0; i < C; i++) {
				if (instruct.charAt(i) == '[')
					closeBracket[i] = findEndPoint(i);
				else if (instruct.charAt(i) == ']')
					openBracket[i] = findStartPoint(i);
			}

			// 명령어 실행 후 무한루프 여부에 따라 출력 형식 저장
			sb.append(runInstruct() ? "Terminates" : "Loops " + bracketIdx + " " + closeBracket[bracketIdx])
					.append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 명령어 실행
	public static boolean runInstruct() {
		int[] memory = new int[M]; // 메모리;
		int currPointer = 0; // 현재 포인터 위치;
		int currInputIdx = 0; // 읽어들일 입력문자 위치;

		int limit = 50000000; // 루프 내부 연산이 50000000번 이상 실행되면 무한루프로 간주 -> 나머지 다 합해도 5000000번 이하
		int operCnt = 0;
		bracketIdx = Integer.MAX_VALUE;

		for (int i = 0; i < C; i++) {
			char ins = instruct.charAt(i);

			switch (ins) {
			case '+': // 포인터가 가리키는 수 1 증가 (overflow 처리)
				memory[currPointer] = (memory[currPointer] == 255) ? 0 : ++memory[currPointer];
				break;
			case '-': // 포인터가 가리키는 수 1 감소 (underflow 처리)
				memory[currPointer] = (memory[currPointer] == 0) ? 255 : --memory[currPointer];
				break;
			case '<': // 포인터를 왼쪽으로 한 칸 움직임 (배열 왼쪽 범위를 벗어나면 배열 끝으로 이동)
				currPointer = currPointer == 0 ? M - 1 : currPointer - 1;
				break;
			case '>': // 포인터를 오른쪽으로 한 칸 움직임 (배열 오른쪽 범위를 벗어나면 배열 처음으로 이동)
				currPointer = currPointer == M - 1 ? 0 : currPointer + 1;
				break;
			case '[':
				// 현재 포인터가 가리키는 수가 0 이라면 ']' 다음 명령으로 점프(루프 종료)
				if (memory[currPointer] == 0) 
					i = closeBracket[i];
				break;
			case ']':
				// 현재 포인터가 가리키는 수가 0 이 아니라면 '[' 다음 명령으로 점프
				if (memory[currPointer] != 0)
					i = openBracket[i];
				break;
			case '.': // 출력 동작은 무시
				break;
			case ',': // 한 문자를 읽어 포인터가 가리키는 곳에 저장 (입력 마지막이면 255 저장)
				memory[currPointer] = currInputIdx == I ? 255 : input.charAt(currInputIdx++);
				break;
			}
			operCnt++;

			// 무한루프 괄호 탐색 -> 끝나는 루프까지의 연산 횟수가 정확히 5*10^7번이라면 무한 루프가 아닐 수 있음
			if (operCnt > limit)
				bracketIdx = Math.min(bracketIdx, i);

			// 무한루프라면 5*10^7 번 이후에도 돌고 있을것임
			if (operCnt > 2 * limit)
				return false;			
		}
		return true;
	}

	// '['와 매칭되는 ']'의 위치 찾기
	public static int findEndPoint(int start) {
		int open = 1;

		for (int i = start + 1; i < C; i++) {
			if (instruct.charAt(i) == '[')
				open++;
			else if (instruct.charAt(i) == ']')
				open--;

			if (open == 0)
				return i;
		}
		return -1;
	}
	// ']'와 매칭되는 '['의 위치 찾기
	public static int findStartPoint(int end) {
		int close = 1;

		for (int i = end - 1; i >= 0; i--) {
			if (instruct.charAt(i) == ']')
				close++;
			else if (instruct.charAt(i) == '[')
				close--;

			if (close == 0)
				return i;
		}
		return -1;
	}

}
