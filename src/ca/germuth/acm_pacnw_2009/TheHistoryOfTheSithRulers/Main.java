package ca.germuth.acm_pacnw_2009.TheHistoryOfTheSithRulers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The History of the Sith RUlers
 * ACM ICPC Pacific Northwest 2009 Regionals
 * Problem I
 * 
 * Because of such a low bound on the input, there were many 
 * ways to approach this problem. I sorted each ruler by their ruling
 * time and simply printed out the ones that matched
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		int numRulings = s.nextInt();
		s.nextLine();
		Ruler[] rulers = new Ruler[numRulings];
		for(int i = 0; i < numRulings; i++){
			String name = s.nextLine().trim();
			String[] startDate = s.next().split("[.]");
			String[] endDate = s.next().split("[.]");
			rulers[i] = new Ruler(Integer.parseInt(startDate[0]), Integer.parseInt(endDate[0]),
					Integer.parseInt(startDate[1]), Integer.parseInt(endDate[1]), name);
			if(s.hasNextLine()){
				s.nextLine();
			}
		}
		
		Arrays.sort(rulers, new Comparator<Ruler>(){
			@Override
			public int compare(Ruler o1, Ruler o2) {
				if(Integer.compare(o1.endYear, o2.endYear) == 0){
					return Integer.compare(o1.endMonth, o2.endMonth);
				}
				return Integer.compare(o1.endYear, o2.endYear);
			}});
		int numYears = s.nextInt();
		for(int a = 0; a < numYears; a++){
			int year = s.nextInt();
			
			String rulerNames = "";
			for(int i = 0; i < rulers.length; i++){
				Ruler curr = rulers[i];
				if(curr.endYear >= year && curr.startYear <= year){
					rulerNames += curr.name + ", ";
				}
			}
		
			if(rulerNames.isEmpty()){
				System.out.println("Galactic year " + year + ": None");
			} else{
				rulerNames = rulerNames.substring(0, rulerNames.length() - 2);
				System.out.println("Galactic year " + year + ": " + rulerNames);
			}
		}
		s.close();
	}
}class Ruler{
	int startYear;
	int endYear;
	int startMonth;
	int endMonth;
	String name;
	public Ruler(int sY, int eY, int sM, int eM, String na){
		startYear = sY;
		endYear = eY;
		startMonth = sM;
		endMonth = eM;
		name = na;
	}
}
