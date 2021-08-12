/**
 * 1. 규영이와 인영이의 카드를 저장한다.
 * 2. 인영이의 카드를 통해 나올 수 있는 순열을 계산한다. - O(nPr)
 * 3. 각 순열에 대해 규영이가 승리하는 경우와 패배하는 경우를 계산한다. - O(n)
 * 
 * 시간복잡도 = O(n * nPr)
 */

package com.ssafy.swea;

import java.io.*;
import java.util.*;

public class Solution_6808 {
	static final int cardSize = 18;
	static boolean[] cards;
	static int[] aCards;
	static int[] bCards;
	static int[] permB;
	static int winCnt;
	static int loseCnt;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("res/input/input_d3_6808.txt")));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int t = Integer.parseInt(br.readLine());

		for (int testCnt = 1; testCnt <= t; testCnt++) {
			cards = new boolean[cardSize + 1];
			aCards = new int[cardSize / 2];
			bCards = new int[cardSize / 2];
			permB = new int[cardSize / 2];
			
			winCnt = 0;
			loseCnt = 0;
			
			// 규영(A) 카드 입력
			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int i = 0; i < cardSize / 2; i++) {
				int aCard = Integer.parseInt(st.nextToken());
				aCards[i] = aCard;
				cards[aCard] = true;
			}

			// 인영(B) 카드 입력
			int idx = 0;
			for (int i = 1; i <= cardSize; i++) {
				if (!cards[i])
					bCards[idx++] = i;
			}

			permutationB(0, 0);

			sb.append("#").append(testCnt).append(" ").append(winCnt).append(" ").append(loseCnt).append('\n');
		}
		System.out.println(sb);
		br.close();
	}

	// B의 카드 순열 - O(nPr)
	public static void permutationB(int cnt, int flag) {
		if (cnt == cardSize / 2) {
			checkWinner();
			return;
		}
		for (int i = 0; i < cardSize / 2; i++) {
			if ((flag & 1 << i) != 0)
				continue;
			permB[cnt] = bCards[i];
			permutationB(cnt + 1, flag | 1 << i);
		}
	}

	// A의 승리여부 확인 - O(n)
	public static void checkWinner() {
		int aScore = 0;
		int bScore = 0;

		// 각 라운드 점수 (무승부 없음)
		for (int i = 0; i < cardSize / 2; i++) {
			if (aCards[i] > permB[i])
				aScore += aCards[i] + permB[i];
			else
				bScore += aCards[i] + permB[i];
		}
		// 최종 결과
		if (aScore > bScore)
			winCnt++;
		else if (aScore < bScore)
			loseCnt++;
	}

}
