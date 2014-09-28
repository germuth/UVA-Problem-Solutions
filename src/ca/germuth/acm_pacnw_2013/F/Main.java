package ca.germuth.acm_pacnw_2013.F;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Federation Favorites F
 *   
 * How do we find all divisors for a given number?
 * Every number has unique prime factorization. And every divisor for a given
 * number is a product of these prime factors. for example 28, has prime factors
 * of {1 x 2 x 2 x 7}.
 * Following this, the divisors of 28 are
 * 1 = 1
 * 2 = 2
 * 4 = 2x2
 * 7 = 7
 * 14 = 2x7
 * 
 * @author Germuth
 */
class Main {
	static HashMap<Integer, Long> answerMap = new HashMap<Integer, Long>();
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in); 

		
		int n = s.nextInt();
		while(n != -1){
			HashMap<Long, Long> divisors = new HashMap<Long, Long>();
			ArrayList<Long> primeFactors = primeFactorization(n);
			//construct all subsets of of this set and multiply to obtain each divisor
			//i is bitarray
			for(long i = 1; i < (1<<primeFactors.size()) - 1; i++){
				long divisor = 1;
				//get members of primeFactors based on i
				for(int k = 0; k < primeFactors.size(); k++){
					long bit = 1 << k;
					//k goes 1, 2, 4, 8, 16, etc
					if((bit & i) != 0){
						divisor *= primeFactors.get(k);
					}
				}
				divisors.put(divisor, 0l);
			}
			//now we have all divisors
			ArrayList<Long> divis = new ArrayList<Long>(divisors.keySet());
			Collections.sort(divis);
			//include 1
			long sum = 1;
			for(int i = 0; i < divis.size(); i++){
				sum += divis.get(i);
			}
			String st = null;
			if(sum == n){
				st = n + " = 1 +";
				for(int i = 0; i < divis.size(); i++){
					st += " " + divis.get(i);
					if(i != divis.size() - 1){
						st += " +";
					}
				}
			}else{
				st = n + " is NOT perfect.";
			}
			System.out.println(st);
			
			n = s.nextInt();
		}
		s.close();
	}
	private static ArrayList<Long> primeFactorization(long m) {
		ArrayList<Long> allFactors = new ArrayList<Long>();
		for (long i = 2; i <= m / i; i++) {
			while (m % i == 0) {
				allFactors.add(i);
				m /= i;
			}
		}
		if (m > 1) {
			allFactors.add(m);
		}
		return allFactors;
	}
}


