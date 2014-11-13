package ca.germuth.acm_pacnw_2011.GoodOrBad;

import java.util.Scanner;
/**
 * Good or Bad
 * ACM ICPC Pacific Northwest Regionals 2011
 * Problem A
 * 
 * Simple Input and Output. 
 * 
 * @author Aaron
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		int n = Integer.parseInt(s.nextLine());
		for(int i = 0; i < n; i++){
			String name = s.nextLine();
			int gs = 0;
			int bs = 0;
			for(int j = 0; j < name.length(); j++){
				char curr = name.charAt(j);
				if((curr + "").equalsIgnoreCase("g")){
					gs++;
				}
				if((curr + "").equalsIgnoreCase("b")){
					bs++;
				}
			}
			if(gs > bs){
				System.out.println(name + " is GOOD");
			}else if( gs < bs){
				System.out.println(name + " is A BADDY");
			}else{
				System.out.println(name + " is NEUTRAL");
			}
		}
		s.close();
	}
}
