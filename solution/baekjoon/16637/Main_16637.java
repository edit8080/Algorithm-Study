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

	// 괄호를 씌워서 수식 계산
	public static void setBracket(int idx, int val) {
		if (idx >= N) {
			max = Math.max(max, val);
			return;
		}
		// 1) (A+B)+C+...
		setBracket(idx + 2, calculate(val, input.charAt(idx), input.charAt(idx + 1) - '0'));

		if (idx + 3 < N) {
			// 2) A+(B+C)+...
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
