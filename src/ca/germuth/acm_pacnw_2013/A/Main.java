package ca.germuth.acm_pacnw_2013.A;

import java.util.Scanner;
/**
 * Assignments Problem A
 * 
 * Simple arithmetic.  
 * @author Germuth
 */
class Main {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);

		int numCases = s.nextInt();
		for(int a = 0; a < numCases; a++){
			int numShip = s.nextInt();
			long distance = s.nextLong();
			
			int goodShips = 0;
			for(int i = 0; i < numShip; i++){
				int speed = s.nextInt();
				int fuel = s.nextInt();
				int consumption = s.nextInt();
				
//				double tripDuration = ((double)distance/(double)speed);
//				
//				double totalFuelNeeded = tripDuration*(double)consumption;
				double totalFuelNeeded = ((double)distance*(double)consumption)/(double)speed;
				
				if(totalFuelNeeded <= fuel){
					goodShips++;
				}
			}
			System.out.println(goodShips);
		}
		s.close();
	}
	
}
