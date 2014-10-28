package ca.germuth.acm_pacnw_2012.E;



import java.util.ArrayList;
import java.util.Scanner;
/**
 * Magic Multiple 
 * ACM ICPC Pacific Northwest Regionals 2012
 * Problem E
 * 
 * This problem is the hidden 3-sat NP problem. Since number of rings
 * limited to 22, only ever 2^22 possible rings to try. This means we
 * can afford to simply iterate through 2^22 and try all possibilities.
 * @author Germuth
 */
class Main {
	static Scanner s;
	public static void main(String args[]) {
		s = new Scanner(System.in);

		int numRiddles = s.nextInt();
		
		for(int a = 0; a < numRiddles; a++) {
			int numRings = s.nextInt();
			int numRunes = s.nextInt();
			
			//to finish scanner at end of line
			s.nextLine();
			Answer ans = solveRiddle(numRings, numRunes);
			switch(ans){
				case NULL_RING:
					System.out.println("INVALID: NULL RING");
					break;
				case RING_MISSING:
					System.out.println("INVALID: RING MISSING");
					break;
				case REPEAT_RING:
					System.out.println("INVALID: RUNE CONTAINS A REPEATED RING");
					break;
				case NOT_POSSIBLE:
					System.out.println("RUNES UNSATISFIABLE! TRY ANOTHER GATE!");
					break;
				case POSSIBLE:
					System.out.println("RUNES SATISFIED!");
					break;
			}
		}

		s.close();
	}
	
	public static Answer solveRiddle(int numRings, int numRunes){
		ArrayList<Rune> allRunes = new ArrayList<Rune>();
		//records whether problem was found
		Answer problem = null;
		for(int i = 0;i< numRunes; i++){
			
			String line = s.nextLine();
			
			String[] ringStr = line.split(" ");
			int[] rings = new int[3];
			
			for (int j = 0; j < rings.length; j++){
				int num = Integer.parseInt(ringStr[j]);

				if (num == 0) {
					problem =  Answer.NULL_RING;
				}
				if (num < -numRings || num > numRings) {
					if(problem != Answer.NULL_RING){
						problem = Answer.RING_MISSING;
					}
				}
				rings[j] = num;

			}
			
			//check for duplicates
			if( ( Math.abs((double)rings[0]) == Math.abs((double)rings[1]))
				|| ( Math.abs((double)rings[0]) == Math.abs((double)rings[2]))
				|| ( Math.abs((double)rings[1]) == Math.abs((double)rings[2]))
				){
				if(problem != Answer.NULL_RING && problem != Answer.RING_MISSING){
					problem = Answer.REPEAT_RING;
				}
			}
			
			Rune r = new Rune(rings[0], rings[1], rings[2]);
			allRunes.add(r);
			
		}
		
		if(problem != null){
			return problem;
		}
		
		int[] ringsToTry = new int[numRings];
		
		int iteration = 0;
		//use bitarray as power set, 1 means true, 0 means false
		for(int i = 0; i < (1 << (numRings)); i++){
			
			//start rings as all true
			for(int j = 0; j < ringsToTry.length; j++){
				ringsToTry[j] = (j+1);
			}
			//turns rings false one by one
			
			//iterate over each bit in i
			//j goes {1, 2, 4, 8, 16, etc}
			for(int j = 1; j < (1 << numRings); j *= 2){
				if((i & j) == 0){
					//try this ring as true
					ringsToTry[iteration] = ringsToTry[iteration] * -1;
				}
				iteration++;
			}
			iteration = 0;
			
			//now we have arrangement of rings to try
			//iterate over each rune and see if it works
			boolean satisfied = true;
			for(int j = 0; j < allRunes.size(); j++){
				Rune curr = allRunes.get(j);
				
				if(!((curr.ring1 == ringsToTry[Math.abs(curr.ring1) - 1]) || (curr.ring2 == ringsToTry[Math.abs(curr.ring2) - 1]) || (curr.ring3 == ringsToTry[Math.abs(curr.ring3) - 1]))){
					satisfied = false;
					break;
				}
			}
			
			if(satisfied){
				return Answer.POSSIBLE;
			}
		}
		return Answer.NOT_POSSIBLE;
	}
}class Rune{
	int ring1;
	int ring2;
	int ring3;
	public Rune(int r1, int r2, int r3){
		this.ring1 = r1;
		this.ring2 = r2;
		this.ring3 = r3;
	}
}enum Answer{
	NULL_RING, RING_MISSING, REPEAT_RING, POSSIBLE, NOT_POSSIBLE
}