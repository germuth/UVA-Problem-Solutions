package ca.germuth.acm_pacnw_2013.G;

import java.util.HashMap;
import java.util.Scanner;
/**
 * Generations of Tribbles Problem G
 *   
 * As long as you store answers you have already calculated, it is super fast
 * to simply use the formula they provide
 * @author Germuth
 */
class Main {
	static HashMap<Integer, Long> answerMap = new HashMap<Integer, Long>();
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);

		int numCases = s.nextInt();
		for(int a = 0; a < numCases; a++){
			int n = s.nextInt();
			
			System.out.println(answer(n));
		}
		s.close();
	}
	private static long answer(int n){
		if(answerMap.containsKey(n)){
			return answerMap.get(n);
		}else{
			if(n < 2){
				return 1;
			}else if(n ==2){
				return 2;
			}else if(n ==3){
				return 4;
			}else{
				long ans = answer(n-1) + answer(n-2)+answer(n-3)+answer(n-4);
				answerMap.put(n, ans);
				return ans;
			}
		}
	}
}
