package ca.germuth.acm_greaterNY_2013.VonNeumannsFly;

import java.util.Scanner;

/**
 * Von Neumann's Fly
 * ACM ICPC Greater Nex York 2013 Regionals
 * Problem B
 * 
 * The problem statement is a trap. The fly is moving at constant
 * velocity the entire time. Therefore, to find out how far it travelled
 * you just have to find out how long it has been travelling for according
 * to V = d / t.
 * The time the fly travels for is the time until bike A and bike B crash.
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();

		for (int a = 0; a < cases; a++) {
			int caseNum = s.nextInt();

			// fly will be moving at constant speed the entire time
			// just calculate how long until a and b crash
			double dis = s.nextDouble();
			double v1 = s.nextDouble();
			double v2 = s.nextDouble();
			double vF = s.nextDouble();

			// V = d / t
			// d = V*t
			// d1 + d2 = d
			// v1*t1 + v2*t2 = d
			// but t1 == t2
			// v1*t + v2*t = d
			// t(v1 + v2) = d
			// t = d / (v1 + v2)
			double time = dis / (v1 + v2);

			double totalDis = vF * time;

			System.out.printf("%d %.2f\n", caseNum, totalDis);
		}
		s.close();
	}
}