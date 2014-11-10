package ca.germuth.acm_pacnw_2009.ThisCantGoOnForever;

import java.util.Scanner;

/**
 * This Can't Go On Forever
 * ACM ICPC Pacific Northwest Regionals 2009
 * Problem C
 * 
 * This problem is literally the exact same as this problem:
 * see the description there
 * 
 * Pissano Periods 
 * ACM ICPC Greater NY 2013 Regionals 
 * Problem D
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while(true){
			int mod = s.nextInt();
			
			if(mod == 0){
				break;
			}
			System.out.println(mod + " " + k(mod));
		}
	}

	// official solution:
	public static int k(int m) {
		/*
		 * int's are fine since max remainder is (x%1000000) <= 999999
		 */
		int nSeq, f1, f2;

		/* Special case is easy */
		if (m == 2) {
			return (3);
		}
		nSeq = 2;
		/* Prime Fibonacci pump */
		f1 = 1;
		f2 = 1;
		for (;; nSeq += 2) {
			/*
			 * we do 2 values at a time, since if m > 2, k(m) is even
			 */
			/* Next value in sequence remainder */
			f1 = (f1 + f2) % m;
			/* Value after that remainder */
			f2 = (f2 + f1) % m;
			/*
			 * Sequence repeats when next 2 values match first 2 which are
			 * always 1
			 */
			if (f1 == 1 && f2 == 1) {
				break;
			}
		}
		return (nSeq);
	}
}
