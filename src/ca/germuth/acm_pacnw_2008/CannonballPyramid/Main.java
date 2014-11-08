package ca.germuth.acm_pacnw_2008.CannonballPyramid;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Cannonball Pyramids
 * ACM IPCC Pacific NorthWest Regionals 2008
 * Prbolem A
 * 
 * Fairly simply recurrence relationship 
 * each layer has sum of i from 1 to n spheres. 
 * this sum has closed form of n(n+1)/2 
 * And to make tetrahedron of 5 height, you need to make one of 4 height,
 * and then add the bottom layer. This gives
 * f(n) = f(n-1) + n(n+1)/2
 * 
 * @author germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();

		for (int a = 0; a < cases; a++) {
			int n = s.nextInt();
			System.out.println((a + 1) + ": " + n + " " + f(n));
		}
		s.close();
	}

	static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	public static int f(int n) {
		if (map.containsKey(n)) {
			return map.get(n);
		} else {
			if (n == 0) {
				return 0;
			} else if (n == 1) {
				return 1;
			}
			int ans = f(n - 1) + n * (n + 1) / 2;
			map.put(n, ans);
			return ans;
		}
	}
}
