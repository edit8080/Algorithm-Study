import java.util.*;
import java.io.*;

class Chemical implements Comparable<Chemical> {
	int min, max;

	Chemical(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public int compareTo(Chemical o) {
		return (this.min < o.min ? -1 : this.min > o.min ? 1 : this.max < o.max ? -1 : o.max > o.max ? 1 : 0);
	}
}

public class Main_1828 {
	static Chemical[] cinfo; // 온도 정보 저장
	static int refridgeCnt = 0; // 냉장고 개수
	static final int INF = -273; // 초기 냉장고 온도(영향 x)
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		cinfo = new Chemical[n];
		
		// 온도 정보 입력
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int min = Integer.parseInt(st.nextToken());
			int max = Integer.parseInt(st.nextToken());

			cinfo[i] = new Chemical(min, max);
		}

		// 최소 온도 기준 정렬
		Arrays.sort(cinfo);

		// 냉장고 개수 카운트
		refridgeCount();

		System.out.println(refridgeCnt);
		br.close();
	}

	// 냉장고 온도 정보를 통해 냉장고 개수 설정
	public static void refridgeCount() {
		int minR = INF;
		int maxR = INF;

		for (int i = 0; i < cinfo.length; i++) {
			// 1. 화학물질이 냉장고 온도 범위 내에서 저장할 수 없음 => 새로운 냉장고 세팅
			if(maxR < cinfo[i].min){
				minR = cinfo[i].min;
				maxR = cinfo[i].max;
				refridgeCnt++;
			}
			
			// 2. 화학물질이 냉장고 온도 범위 내에서 저장 가능 => 냉장고 온도 정보 업데이트 
			else {
				minR = cinfo[i].min;
				maxR = Math.min(maxR, cinfo[i].max);				
			}
		}
	}
}
