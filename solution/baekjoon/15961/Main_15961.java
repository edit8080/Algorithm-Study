/**
 * 1. 초밥 정보를 입력받는다.
 * 2. 처음에 먹을 수 있는 회전초밥의 종류를 센다. 
 * 3. 슬라이딩 윈도우를 통해 먹을 수 있는 초밥의 최대 종류 개수를 구한다.
 * 4. 만약 선택된 초밥들이 쿠폰에 적힌 초밥과 다르다면 (종류)+1 한다.
 * 5. 2 ~ 4 과정동안 계산한 초밥의 최대 종류 개수를 출력한다.
 * 
 * 주의) - 슬라이딩 윈도우를 사용하지 않으면 시간초과 발생
 *       - 회전 초밥이므로 원형으로 처리
 * 
 * 시간복잡도 = O(N) (N: 회전 초밥 접시 수 <= 3000000)
 */

import java.util.*;
import java.io.*;

public class Main_15961 {
	static int N, d, k, c;
	static int[] sushi;
	static int[] select;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		sushi = new int[N];
		select = new int[d + 1];
		Queue<Integer> item = new LinkedList<>();

		for (int i = 0; i < N; i++)
			sushi[i] = Integer.parseInt(br.readLine());

		// 초기값 설정 - 처음 k개 선택한 상황
		int cnt = 0;
		for (int i = 0; i < k; i++) {
			// 처음에 먹을 수 있는 회전초밥의 종류 세기
			if (select[sushi[i]] == 0)
				cnt++;
			select[sushi[i]]++;
			item.offer(sushi[i]);
		}

		int max = cnt;
		int currCnt = cnt;

		// 쿠폰 처리
		if (select[c] == 0)
			max = cnt + 1;

		// 슬라이딩 윈도우를 통해 먹을 수 있는 초밥의 최대 종류 개수 구하기 - O(N)
		// 회전 초밥이므로 원형 체크
		for (int i = k; i < N + k; i++) {
			// 앞의 초밥 제거
			if (--select[item.poll()] == 0)
				currCnt--;

			// 새로운 초밥 삽입
			item.offer(sushi[i % N]);
			if (select[sushi[i % N]] == 0)
				max = Math.max(max, ++currCnt);
			select[sushi[i % N]]++;

			// 쿠폰 처리
			if (select[c] == 0)
				max = Math.max(max, currCnt + 1);
		}
		System.out.println(max);
	}
}
