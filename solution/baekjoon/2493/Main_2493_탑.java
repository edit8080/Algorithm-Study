/*
 * 1. 스택의 top 값이 현재 tower값보다 작으면 pop한다. (스택이 비기 전까지)
 * 2. 1번 과정 이후 top에 저장된 스택의 값이 신호를 수신하는 탑이므로 정보를 저장한다.
 * 3. 이후 현재 tower값을 idx와 Pair로 스택에 저장한다. 
 * 4. 모든 탑에 대해 1 ~ 3과정을 반복한다.
 *  
 * 시간복잡도 = O(n)
 */

import java.io.*;
import java.util.*;

class Pair {
	int idx;
	int value;

	Pair(int idx, int value) {
		this.idx = idx;
		this.value = value;
	}
}

public class Main_2493_탑 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		int towerCnt = Integer.parseInt(br.readLine());
		int[] tower = new int[towerCnt];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < towerCnt; i++)
			tower[i] = Integer.parseInt(st.nextToken());
		
		int[] receiveIdx = receiveTowerIdx(tower);
		
		// 출력
		StringBuilder sb = new StringBuilder();
		for(int idx : receiveIdx)
			sb.append(idx).append(" ");
		System.out.println(sb.toString());
	}

	// 신호를 수신받는 탑의 번호
	public static int[] receiveTowerIdx(int[] tower) {
		Stack<Pair> s = new Stack<Pair>();
		int[] receiveIdx = new int[tower.length];
		
		for (int i = 0; i < tower.length; i++) {
			// 저장된 타워의 값이 작으면 pop(), 아니면 break
			while(!s.empty()) {
				if(s.peek().value < tower[i])
					s.pop();
				else
					break;
			}
			receiveIdx[i] = s.empty() ? 0 : s.peek().idx + 1;
			s.push(new Pair(i, tower[i]));
		}
		
		return receiveIdx;
	}
}
