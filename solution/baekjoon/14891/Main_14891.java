/**
 * 1. 4개의 톱니바퀴 정보를 입력받는다.
 * 2. 각 회전에 대해 기준 바퀴의 좌우 톱니바퀴를 탐색한다. - O(K)
 * 3. 다음 톱니바퀴에 대해 트리거되는 회전 여부를 탐색한다. (1->2->3->4)
 * 4. 결정된 회전방향에 대해 톱니바퀴를 회전시킨다.
 * 
 * 주의) 회전한 후 다음 톱니바퀴에 대해 회전 여부를 판단하는 것이 아니라
 *       회전하기 전의 모습에서 일괄적으로 판단하는 것에 유의한다. 
 * 
 * 시간복잡도 = O(K) (K: 회전 횟수 <= 100)
 */

import java.io.*;
import java.util.*;

public class Main_14891 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] wheels = new String[4];

		// 톱니바퀴 정보 입력
		for (int i = 0; i < 4; i++)
			wheels[i] = br.readLine();

		// 회전 횟수
		int k = Integer.parseInt(br.readLine());

		for (int i = 0; i < k; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int wheelNum = Integer.parseInt(st.nextToken()) - 1;
			int dir = Integer.parseInt(st.nextToken());

			boolean[] checked = new boolean[4];
			// 바퀴 회전
			rotateCheck(wheels, checked, wheelNum, dir);
		}
		// 점수 계산
		int sum = 0;
		for (int i = 0; i < 4; i++)
			sum += score(i, wheels[i]);

		System.out.println(sum);
		br.close();
	}

	public static void rotateCheck(String[] wheels, boolean[] checked, int currWheel, int dir) {
		int[] adjDir = { -1, 1 }; // 왼쪽 톱니바퀴, 오른쪽 톱니바퀴

		// 2번 -> 우측 톱니바퀴와 맞닿아 있는 톱니
		// 6번 -> 왼쪽 톱니바퀴와 맞닿아 있는 톱니
		int[] adjIdx = { 6, 2 };
		int[] dirSet = new int[4];
		
		checked[currWheel] = true;
		
		// 왼쪽 톱니바퀴 확인, 오른쪽 톱니바퀴 확인
		for (int i = 0; i < 2; i++) {
			int adjWheel = currWheel + adjDir[i];

			if (adjWheel < 0 || adjWheel >= 4 || checked[adjWheel])
				continue;

			char currTeeth = wheels[currWheel].charAt(adjIdx[i]);
			char adjTeeth = wheels[adjWheel].charAt(adjIdx[(i + 1) % 2]);

			// 현재 톱니바퀴의 톱니와 인접한 톱니바퀴의 톱니가 동일하지 않다면 반대로 회전 -> 인접 톱니 회전 방향 결정
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

	// 톱니바퀴가 회전한 후의 모습
	public static String rotateWheel(String wheel, int dir) {
		String rWheel = "";

		// 시계 방향 회전
		if (dir == 1) {
			rWheel += wheel.charAt(wheel.length() - 1);
			for (int i = 0; i < wheel.length() - 1; i++)
				rWheel += wheel.charAt(i);
		}
		// 반시계 방향 회전
		else if (dir == -1) {
			for (int i = 1; i < wheel.length(); i++)
				rWheel += wheel.charAt(i);
			rWheel += wheel.charAt(0);
		}
		return rWheel;
	}

	// 점수 획득
	public static int score(int n, String s) {
		if (s.charAt(0) == '0')
			return 0;
		return (int) (Math.pow(2, n));
	}
}
