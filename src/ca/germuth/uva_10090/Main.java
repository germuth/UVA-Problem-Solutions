package ca.germuth.uva_10090;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		while(s.hasNext()){
			long items = s.nextLong();
			
			if(items == 0){
				System.exit(0);
			}
			
			long box1Cost = s.nextLong();
			long box1Size = s.nextLong();
			
			long box2Cost = s.nextLong();
			long box2Size = s.nextLong();
			
			//determine if possible
			boolean sucess = false;
			long fakeTotal = items;
			for(int i = 0; i < box1Size; i++){
				if(fakeTotal % box1Size == 0){
					sucess = true;
					break;
				}else{
					fakeTotal += box2Size;
				}
			}
			if(fakeTotal % box1Size != 0 && sucess == false){
				System.out.println("failed");
				continue;
			}
			
			
			//determine which box is less efficient 
			double box1Ratio = ((double)box1Size / (double)box1Cost);
			double box2Ratio = ((double)box2Size / (double)box2Cost);
			
			boolean isFirstBoxGood = false;
			long goodBoxSize;
			long goodBoxCost;
			long badBoxSize;
			long badBoxCost;
			if( box1Ratio > box2Ratio ){
				isFirstBoxGood = true;
				
				goodBoxSize = box1Size;
				goodBoxCost = box1Cost;
				badBoxSize = box2Size;
				badBoxCost = box2Cost;
			}else{
				goodBoxSize = box2Size;
				goodBoxCost = box2Cost;
				badBoxSize = box1Size;
				badBoxCost = box1Cost;
			}
			
			long total = items;
			long cost = 0;
			boolean solveable = true;
			while( total % goodBoxSize != 0){
				cost += badBoxCost;
				total -= badBoxSize;
				
				if(total < 0){
					solveable = false;
					break;
				}
			}
			
			if(solveable){
				long numGoodBox = (total / goodBoxSize);
				if(isFirstBoxGood){
					System.out.println(numGoodBox + " " + cost / badBoxCost);
				}else{
					System.out.println(cost/badBoxCost + " " + numGoodBox);
				}
				
			}else{
				System.out.println("failed");
			}
		}
	}
}
