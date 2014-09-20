package ca.germuth.acm_pacnw_2012.A;

import java.util.Scanner;
/**
 * Good Versus Evil
 * Problem A
 * 
 * Simple freebie question
 * @author Germuth
 */
public class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		int numBattles = s.nextInt();
		
		for(int i = 0; i < numBattles; i++){
			int goodTotal = 0;
			
			goodTotal += s.nextInt();
			goodTotal += (s.nextInt() * 2);
			goodTotal += (s.nextInt() * 3);
			goodTotal += (s.nextInt() * 3);
			goodTotal += (s.nextInt() * 4);
			goodTotal += (s.nextInt() * 10);
			
			int badTotal = 0;
			
			badTotal += (s.nextInt() * 1);
			badTotal += (s.nextInt() * 2);
			badTotal += (s.nextInt() * 2);
			badTotal += (s.nextInt() * 2);
			badTotal += (s.nextInt() * 3);
			badTotal += (s.nextInt() * 5);
			badTotal += (s.nextInt() * 10);
			
			if(goodTotal > badTotal){
				System.out.println("Battle " + (i+1) + ": Good triumphs over Evil");
			}else if(goodTotal < badTotal){
				System.out.println("Battle " + (i+1) + ": Evil eradicates all trace of Good");
			}else{
				System.out.println("Battle " + (i+1) + ": No victor on this battle field");
			}
		}
	}
}
