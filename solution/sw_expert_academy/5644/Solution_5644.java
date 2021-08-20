/**
 * 1. 이동 시간에 대한 사용자(A, B) 걸음 정보와 BC 정보를 입력받는다.
 * 2. 각 시간에 대한 사용자 위치를 기반으로 충전할 수 있는 BC를 저장한다 
 * 3. 충전할 수 있는 BC 의 충전량을 토대로 최대 충전량을 계산한다.
 * 	  -> A만 충전 가능한 경우 / B만 충전 가능한 경우 / A, B 모두 충전 가능한 경우 
 * 4. A, B 모두 충전 가능할 때 동일한 BC에서 충전하는지 판단하여 처리한다.
 * 
 * 시간복잡도 = O(M*A^2) (M: 이동 시간 <= 100, A: BC의 개수 <= 8)
 */

import java.util.*;
import java.io.*;

class BC {
	int id;
	int x, y;
	int coverage;
	int perform;

	BC(int id, int x, int y, int coverage, int perform) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.coverage = coverage;
		this.perform = perform;
	}
}

public class Solution_5644 {
	static int[][] walk;
	static BC[] bc;
	static int[][] dir = { { 0, 0, 1, 0, -1 }, { 0, -1, 0, 1, 0 } }; // 원위치, 상, 우, 하, 좌

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_모의_5644.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= t; testCase++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int walkCnt = Integer.parseInt(st.nextToken());
			int bcCnt = Integer.parseInt(st.nextToken());

			walk = new int[2][walkCnt + 1];
			bc = new BC[bcCnt];

			// 사용자 이동 입력 (제자리 추가)
			for (int i = 0; i < 2; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 1; j <= walkCnt; j++)
					walk[i][j] = Integer.parseInt(st.nextToken());
			}
			// BC 정보 입력
			for (int i = 0; i < bcCnt; i++) {
				st = new StringTokenizer(br.readLine());

				int x = Integer.parseInt(st.nextToken()) - 1;
				int y = Integer.parseInt(st.nextToken()) - 1;
				int coverage = Integer.parseInt(st.nextToken());
				int perform = Integer.parseInt(st.nextToken());

				bc[i] = new BC(i, x, y, coverage, perform);
			}
			sb.append('#').append(testCase).append(' ').append(maxCharge()).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// 최대 충전량 계산
	public static int maxCharge() {
		int sum = 0;

		int aX = 0, aY = 0;
		int bX = 9, bY = 9;

		// 이동에 대한 충전량 계산 - O(M) (M: 이동 시간 <= 100)
		for (int i = 0; i < walk[0].length; i++) {
			// 이동
			aX += dir[0][walk[0][i]];
			aY += dir[1][walk[0][i]];
			bX += dir[0][walk[1][i]];
			bY += dir[1][walk[1][i]];

			// A,B 이동 경로에서 가능한 BC 저장
			List<BC> aBC = new ArrayList<>();
			List<BC> bBC = new ArrayList<>();

			for (int j = 0; j < bc.length; j++) {
				if (dist(aX, aY, bc[j].x, bc[j].y) <= bc[j].coverage)
					aBC.add(bc[j]);

				if (dist(bX, bY, bc[j].x, bc[j].y) <= bc[j].coverage)
					bBC.add(bc[j]);
			}
			sum += maxCharge(aBC, bBC);
		}
		return sum;
	}

	// 충전이 가능할 경우 최대 충전량 반환 - O(A^2) (A: BC의 개수 <= 8)
	public static int maxCharge(List<BC> aBC, List<BC> bBC) {
		int maxRet = 0;

		// A만 충전할 수 있을 경우
		if (bBC.size() == 0) {
			for (int i = 0; i < aBC.size(); i++)
				maxRet = Math.max(maxRet, aBC.get(i).perform);

			return maxRet;
		}
		// B만 충전할 수 있을 경우
		if (aBC.size() == 0) {
			for (int i = 0; i < bBC.size(); i++)
				maxRet = Math.max(maxRet, bBC.get(i).perform);

			return maxRet;
		}
		// A, B 모두 충전할 수 있는 경우
		for (int i = 0; i < aBC.size(); i++) {
			int aId = aBC.get(i).id;

			for (int j = 0; j < bBC.size(); j++) {
				// 동일한 곳에서 충전하는 경우
				if (aId == bBC.get(j).id)
					maxRet = Math.max(maxRet, aBC.get(i).perform);
				// 서로 다른 곳에서 충전하는 경우
				else
					maxRet = Math.max(maxRet, aBC.get(i).perform + bBC.get(j).perform);
			}
		}
		return maxRet;
	}

	// 사용자와 BC 간의 거리
	public static int dist(int x, int y, int bcX, int bcY) {
		return Math.abs(x - bcX) + Math.abs(y - bcY);
	}
}
