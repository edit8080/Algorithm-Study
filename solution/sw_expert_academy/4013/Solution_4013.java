/**
 * 1. 톱니 자석 정보와 회전 정보를 입력받는다.
 * 2. 회전 정보를 입력받을 때마다 톱니 자석 회전을 시작한다. - O(K)
 * 3. 톱니 자석의 회전 가능 여부는 마주보는 2, 6 번째 톱니를 기준으로 확인한다.
 * 4. 모든 톱니 자석의 회전 여부를 결정하고 나면 결정된 방향에 따라 톱니 자석을 회전시킨다.
 * 
 * 시간복잡도 = O(K) (K : 회전 정보의 개수 <= 20)
 */

import java.io.*;
import java.util.*;

public class Solution_4013 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_모의_4013.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= T; testCnt++) {
			int K = Integer.parseInt(br.readLine());

			int[][] wheels = new int[4][8];

			// 톱니 자석 정보 입력
			for (int i = 0; i < 4; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < 8; j++) 
					wheels[i][j] = Integer.parseInt(st.nextToken());				
			}

			// 회전 정보 입력
			for (int i = 0; i < K; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				int wheelNum = Integer.parseInt(st.nextToken()) - 1;
				int dir = Integer.parseInt(st.nextToken());

				// 자석 회전
				rotateCheck(wheels, new boolean[4], wheelNum, dir);
			}
			// 점수 계산
			int sum = 0;
			for (int i = 0; i < 4; i++)
				sum += wheels[i][0] << i;

			sb.append('#').append(testCnt).append(' ').append(sum).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	public static void rotateCheck(int[][] wheels, boolean[] checked, int currWheel, int dir) {
		int[] adjDir = { -1, 1 }; // 왼쪽 톱니자석, 오른쪽 톱니자석

		// 6번 -> 왼쪽 톱니 자석과 맞닿아 있는 톱니
		// 2번 -> 우측 톱니 자석과 맞닿아 있는 톱니
		int[] adjIdx = { 6, 2 };
		int[] dirSet = new int[4];

		checked[currWheel] = true;

		// 왼쪽 톱니자석 확인, 오른쪽 톱니자석 확인
		for (int i = 0; i < 2; i++) {
			int adjWheel = currWheel + adjDir[i];

			if (adjWheel < 0 || adjWheel >= 4 || checked[adjWheel])
				continue;

			int currTeeth = wheels[currWheel][adjIdx[i]];
			int adjTeeth = wheels[adjWheel][adjIdx[(i + 1) % 2]];

			// 현재 톱니자석의 톱니와 인접한 톱니자석의 톱니가 동일하지 않다면 반대로 회전 -> 인접 톱니 회전 방향 결정
			if (currTeeth != adjTeeth)
				dirSet[adjWheel] = (dir == 1) ? -1 : 1;
		}

		// 인접 톱니 회전 -> 해당 톱니 기준 회전
		for (int i = 0; i < 4; i++) {
			if (dirSet[i] != 0)
				rotateCheck(wheels, checked, i, dirSet[i]);
		}
		// 일괄적으로 회전
		wheels[currWheel] = rotateWheel(wheels[currWheel], dir);
	}

	// 톱니 자석이 회전한 후의 모습
	public static int[] rotateWheel(int[] wheel, int dir) {
		int[] rWheel = new int[8];
		int idx = 0;

		// 시계 방향 회전
		if (dir == 1) {
			while (idx < 8) {
				rWheel[(idx + 1) % 8] = wheel[idx];
				idx++;
			}
		}
		// 반시계 방향 회전
		else if (dir == -1) {
			while (idx < 8) {
				rWheel[(idx + 7) % 8] = wheel[idx];
				idx++;
			}
		}
		return rWheel;
	}
}
