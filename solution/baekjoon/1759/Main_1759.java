/**
 * 1. 입력받은 문자들을 오름차순으로 조합하여 패스워드를 생성한다. - O(nCr)
 * 2. 생성한 패스워드에 최소 모음 1개, 자음 2개가 존재하는지 확인한다. - O(r) 
 * 3. 최종적으로 가능한 패스워드를 사전순으로 정렬한다.
 * 
 * 시간복잡도 = O(nCr * r) (n: 알파벳 개수 <= 15, r: 암호 길이 <= 15) 
 */

import java.util.*;
import java.io.*;

public class Main_1759 {
	static char[] vowels = { 'a', 'e', 'i', 'o', 'u' };
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());

		int l = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());

		char[] input = new char[c];

		// 문자 입력
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < c; i++) {
			input[i] = st.nextToken().charAt(0);
		}

		// 조합을 위한 초기화
		Arrays.sort(input);

		int[] select = new int[c];
		for (int i = 0; i < l; i++)
			select[(c - 1) - i] = 1;

		List<String> passwords = new ArrayList<String>();

		// 조합으로 오름차순 패스워드 생성 - O(nCr)
		do {
			StringBuilder chkPassword = new StringBuilder();
			
			for (int i = c - 1; i >= 0; i--) {
				if (select[i] == 1)
					chkPassword.append(input[(c - 1) - i]);
			}
			// 생성한 패스워드의 유효성 확인 - O(r)
			if (isValid(chkPassword.toString()))
				passwords.add(chkPassword.toString());
			
		} while (nextPermutation(select));

		// 모든 패스워드 사전 순 정렬
		Collections.sort(passwords);
		
		for(int i=0;i<passwords.size();i++)
			sb.append(passwords.get(i)).append('\n');
			
		System.out.println(sb);
		br.close();
	}

	// 패스워드의 유효성 검사 함수 - O(r)
	public static boolean isValid(String password) {
		int vowelCnt = 0;
		int consonantCnt = 0;

		for (int i = 0; i < password.length(); i++) {
			boolean isVowel = false;			
			
			for (int j = 0; j < vowels.length; j++) {
				// 모음 갯수 세기
				if (password.charAt(i) == vowels[j]) {
					vowelCnt++;
					isVowel = true;
					break;
				}				
			}
			// 모음 아니면 자음 갯수 증가
			if(!isVowel)
				consonantCnt++;
		}
		// 최소 1개 이상의 모음, 2개 이상의 자음		
		return (vowelCnt >= 1 && consonantCnt >= 2) ? true : false;
	}

	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;

		int i = n - 1;
		while (i > 0 && arr[i - 1] >= arr[i])
			i--;

		if (i == 0)
			return false;

		int j = n - 1;
		while (arr[i - 1] >= arr[j])
			j--;

		swap(arr, i - 1, j);

		int k = n - 1;
		while (i < k)
			swap(arr, i++, k--);

		return true;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
