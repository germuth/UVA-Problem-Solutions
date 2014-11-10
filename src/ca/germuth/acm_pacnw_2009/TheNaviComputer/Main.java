package ca.germuth.acm_pacnw_2009.TheNaviComputer;

import java.util.Scanner;
/**
 * The Navi-Computer is Down
 * ACM ICPC Pacific Northwest Regionals 2009
 * Problem A
 * 
 * Simple IO and calculation.
 * I was getting extra spaces, so i added the trim call just in case
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		int cases = s.nextInt();
		
		for(int a = 0; a < cases; a++){
			s.nextLine();
			String name = s.nextLine().trim();
			double x = s.nextDouble();
			double y = s.nextDouble();
			double z = s.nextDouble();
			s.nextLine();
			String name2 = s.nextLine().trim();
			double x2 = s.nextDouble();
			double y2 = s.nextDouble();
			double z2 = s.nextDouble();
					
			double distance = Math.sqrt( 
					Math.pow(x2-x, 2) +
					Math.pow(y2 - y, 2) +
					Math.pow(z2 - z, 2));
			System.out.printf(name + " to " + name2 + ": " + "%.2f\n", distance);
		}
		s.close();
	}
}
