/**
 * 1. M개의 번호로 N자리의 비밀번호를 만든다.
 *    -> 비밀번호를 만들 때 중복되는 경우를 빼고 더한다. 
 *    -> mCm*mΠn - mC(m-1) * (m-1)Πn + mC(m-2) * (m-2)Πn ... mC1 * 1Πn
 * 2. 값을 계산할 때마다 1000000007의 나머지를 계산한다.
 * 
 * 주의) 경우의 수가 음수가 되면 1000000007를 더한다.
 * 
 * 시간복잡도 = O(m * n sqrt(n)) (m: 버튼 수 <= 100, n: 비밀번호 자리수 <= 100)
 */

import java.io.*;
import java.util.*;

public class Solution_6026 {
	static final int MOD = 1000000007;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d4_6026.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= T; testCnt++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int M = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());

			long cnt = 0;
			int toggle = 1;

			// mCm*mΠn - mC(m-1) * (m-1)Πn + mC(m-2) * (m-2)Πn ... mC1 * 1Πn
			for (int i = M; i >= 1; i--) {
				cnt += (combination(M, i) * power(i, N) % MOD) * toggle;

				// 음수가 발생하면 MOD 추가
				if (cnt < 0)
					cnt += MOD;

				toggle = ~toggle + 1;
			}
			sb.append('#').append(testCnt).append(' ').append(cnt % MOD).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// nCr 계산 - O(n)
	public static long combination(int n, int r) {
		if (r == 0)
			return 1;

		long[] fact = new long[n + 1];
		fact[0] = 1;

		for (int i = 1; i <= n; i++)
			fact[i] = fact[i - 1] * i % MOD;

		// 페르마 소정리 이용
		// a^p ≡ a => a^(p-1) ≡ 1 => a^(p-2) ≡ 1/p
		// nCr = n!/(r!(n-r)!) % MOD = n! * (r! ^ (MOD-2) % MOD) * ((n-r)! ^ (MOD-2) %
		// MOD) % MOD
		return ((fact[n] * power(fact[r], MOD - 2) % MOD) * power(fact[n - r], MOD - 2) % MOD) % MOD;
	}

	// 제곱 계산 - O(sqrt(n))
	public static long power(long x, long y) {
		long res = 1;
		x = x % MOD;

		while (y > 0) {
			// 마지막 홀수 처리
			if (y % 2 == 1)
				res = (res * x) % MOD;

			y = y >> 1;
			x = (x * x) % MOD;
		}
		return res;
	}

}
