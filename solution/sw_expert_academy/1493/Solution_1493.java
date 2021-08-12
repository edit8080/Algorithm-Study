/**
 * 1. 배열을 주어진 방향대로 초기화한다.
 * 2. 초기화 시 <값, 좌표> 쌍으로 HashMap에 저장한다.
 * 3. 입력받은 p, q에 대해 &연산은 HashMap을 통해 좌표를 구한다.
 * 4. #연산은 배열을 통해 좌표값을 구한다.
 * 
 * 시간복잡도 = O(n^2) (n: 배열의 크기)
 */

import java.io.*;
import java.util.*;

class CordsPoint {
	int x, y;

	CordsPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Solution_1493 {
	static final int cordsSize = 301; // 300 * 301 / 2 = 45150 (> p+q 범위의 2배 크기)
	static Map<Integer, CordsPoint> cordsMap = new HashMap<Integer, CordsPoint>(); // <좌표값, 좌표> 쌍 저장
	static int[][] cords = new int[cordsSize][cordsSize]; // 좌표값 저장 (p, q <= 10000)

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d3_1493.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		cordsSetting();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());

			sb.append("#").append(testCnt).append(" ").append(customSum(p, q)).append("\n");
		}
		System.out.println(sb);
		br.close();
	}

	// 좌표의 값과 좌표쌍 세팅 -> 각 테스트 케이스 공통
	public static void cordsSetting() {
		int val = 1;
		int sum = 2;

		// 평행 이동
		loop: while (true) {
			int row = 1;
			int col = sum - 1;

			// 대각선 확인
			while (row < sum) {
				cords[row][col] = val;
				cordsMap.put(val, new CordsPoint(row, col));

				if (val == ((cordsSize / 2) * cordsSize))
					break loop;

				row++;
				col--;
				val++;
			}
			sum++;
		}
	}

	// 사용자 정의 덧셈
	public static int customSum(int p, int q) {
		CordsPoint cordsp = cordsMap.get(p);
		CordsPoint cordsq = cordsMap.get(q);

		int sumX = cordsp.x + cordsq.x;
		int sumY = cordsp.y + cordsq.y;

		return cords[sumX][sumY];
	}
}
