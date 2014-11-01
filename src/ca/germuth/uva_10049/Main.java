package ca.germuth.uva_10049;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Self Describing Sequence 
 * 6.6.7 
 * PC/UVA ID: 110607/10049
 *
 * The first observation is that f(n) = f(n-1) OR f(n-1) + 1. This gives you the
 * idea to try and find all the transition points( where f(n) = f(n-1) + 1 )
 * rather than the array itself. For example, we can construct g(n) where each
 * number in g(n) is the n at which f(n) increments. g() = [1, 2, 4, 6, 9, 12,
 * .... ] f(n) is 1 at n=1. f(n)=2 at n=2. f(n)=3 at n=4; f(n)=4 at n = 6.
 * f(n)=5 at n = 9. etc.
 * 
 * With this list, given n, we can iterate through g(n) counting how many
 * changes have occurred. Since each change represents + 1, the number of changes
 * is the value of f(n).
 * 
 * Now how can we create g(n). Well the first three values are 1, 2, 4. We can
 * calculate these by hand. We can then use g(n-1) to create g(n).
 * 
 * In order to calculate g(3) which is g(n==4), we need to ask how many 3s there
 * was. We can find this by search g(n) as before, and returning the n at which
 * that element was found. Therefore g(n) = g(n-1) + (number of n-1s in the
 * list)
 * 
 * @author germuth
 */
class Main {
	public static void main(String[] args) {
		// precompute g
		ArrayList<Integer> g = new ArrayList<Integer>();
		g.add(1);
		g.add(2);
		g.add(4);
		for (int indexOfG = 3; indexOfG < 673400; indexOfG++) {
			// since arrays are 0 indexed, but k is one indexed
			int k = indexOfG + 1;

			// try to find how many k-1 s there are in the list
			// can use g() since it contains the indicies for when numbers
			// changed
			// search g for k-1, and get index it was found on. Since indexs are
			// 0 based
			// we must add one.
			// If the element is not found, the (-(insertionPoint) -1) is
			// returned by binary search
			// meaning we must return (index+1)*-1

			int index = Collections.binarySearch(g, k - 1);
			if (index < 0) {
				// if negative then it wasn't contained in the list
				// but we can
				index = (index + 1) * -1;
			} else {
				index++;
			}
			int kMinusOnes = index;

			g.add(g.get(indexOfG - 1) + kMinusOnes);
		}

		Scanner s = new Scanner(System.in);
		while (s.hasNext()) {
			int d = s.nextInt();

			if (d == 0) {
				break;
			}
			int ans = 0;
			for (int i = 0; i < g.size(); i++) {
				if (g.get(i) <= d) {
					ans++;
				} else {
					break;
				}
			}

			System.out.println(ans);

		}
		s.close();
	}
}
