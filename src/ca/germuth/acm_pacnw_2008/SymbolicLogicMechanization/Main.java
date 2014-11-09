package ca.germuth.acm_pacnw_2008.SymbolicLogicMechanization;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Symbolic Logic Mechanization
 * ACM ICPC Pacific Northwest Regionals 2008
 * Problem G
 * 
 * Since the input size is quite small, only 10 possible variables. This means to figure
 * out whether it is a tautology, or contradiction, we can simply try all possible
 * values of true and false for each variable. This can be done with bit array, for example
 * With three variables
 * 0 = 000 = a-false, b-false, c-false
 * 1 = 001 = a-false, b-false, c-true
 * 2 = 010 = a-F, b-T, c-F
 * 3 = 011 = a-F, b-T, c-T, 
 * 4 = 100 = ...
 * 5 = 101
 * 6 = 110
 * 7 = 111
 * 
 * Therefore we can increment a number from 0 -> 1 * 2 ^ (variableAmount) - 1 or
 * 1 << variableAmount  -1. This is 1*2^(3)-1 = 8-1 = 7 in the above example.
 * We can record whether it is true or false for each one and detect whether
 * there was a change. 
 * The only other trick was to make sure you output the correct order in the 
 * right orders, when the expressions have 2 errors. 
 * @author Aaron
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		while(true){
			String in = s.nextLine().trim();
			
			if(in.isEmpty()){
				break;
			}
			
			Result r = Result.GOOD;
			ArrayList<Character> operands = new ArrayList<Character>();
			
			int charactersNeeded = 1;
			//find out if it is correct
			for(int i = 0; i < in.length(); i++){
				char c = in.charAt(i);
				//is valid character
				if((c + "").matches("[a-z|C|N|K|A|D|E|J]")){
					//operator
					if((c + "").matches("[A-Z]")){
						if(c != 'N'){
							charactersNeeded++;
						}							
					}
					//operand
					else{
						charactersNeeded--;
						if(!operands.contains(c)){
							operands.add(c);							
						}
						if(in.length() == 1){
							r = Result.GOOD;
							break;
							
						}
					}
				}else {
					r = Result.BAD;
					break;
				}
				//ERROR checkisg is bad, read description
				if(charactersNeeded < 0 || (charactersNeeded == 0 && i != in.length() - 1)){
					r = Result.EXTRA;
					break;
				}
				
			}
			
			if(charactersNeeded > 0 && r == Result.GOOD){
				//not enough characters
				r = Result.INSUFF;
			}
			
			boolean conditional = false;
			boolean result = false;
			if(r == Result.GOOD){
				
				int possibilities = 1 << operands.size();
				//test whether always true or false
				for(int bitmap = 0; bitmap <= possibilities; bitmap++){
					String current = in;
					//for each bit of bitmap
					for(int i = 0; i < operands.size(); i++){
						//1, 2, 4, 8, etc
						int bit = 1 << i;
						
						//if bitmap has one
						if((bitmap & bit) > 0){
							current = current.replace(operands.get(i), '1');
						}else{
							current = current.replace(operands.get(i), '0');
						}
					}
					
					//string now fully replaced with 1s and 0s
					//evaluate it
					boolean thisResult = evaluate(current);
					if(bitmap != 0){
						if(thisResult == result){
							continue;
						}else{
							conditional = true;
							break;
						}
					}
					result = thisResult;
				}
			}
			
			if(r != Result.GOOD){
				in += " is invalid: ";
				switch(r){
				case BAD: in += "invalid character"; break;
				case EXTRA: in += "extraneous text"; break;
				case INSUFF: in += "insufficient operands"; break;
				}
			}
			else{
				in += " is valid: ";
				if(conditional){
					in += "contingent";
				}else if(result){
					in += "tautology";
				}else{
					in += "contradiction";
				}				
			}
			
			System.out.println(in);
			
		}
		s.close();
	}
	
	public static boolean evaluate(String curr){
		while(curr.length() >= 2){
			
			//grab second last character
			char secondLast = curr.charAt(curr.length() - 2);
			
			if((secondLast + "").matches("[A-Z]")){
				//we are dealing with Not
				char operator = secondLast;
				char operand1 = curr.charAt(curr.length() - 1);
				
				curr = curr.substring(0, curr.length() -2) + eval(operator, operand1, '!');
			}else{
				//grab 3 last characters
				char operator = curr.charAt(curr.length() - 3);
				char operand1 = secondLast;
				char operand2  = curr.charAt(curr.length() - 1);
				
				//if operator not operator, then shift down one position
				int shift = 0;
				String end = null;
				while(!Character.isAlphabetic(operator)){
					shift++;
					operator = curr.charAt(curr.length() - (3 + shift));
					operand1 = curr.charAt(curr.length() - (2 + shift));
					operand2  = curr.charAt(curr.length() - (1 + shift));
					
					end = curr.substring(curr.length() - shift, curr.length());
				}
				
				//perform operation
				if(operator == 'N'){
					curr = curr.substring(0, curr.length() - (3 + shift)) + eval(operator, operand1, operand2) + operand2;
				}else{
					curr = curr.substring(0, curr.length() - (3 + shift)) + eval(operator, operand1, operand2);									
				}	
				
				if(end != null){
					curr += end;
				}
			}
		}
		
		return bool(curr.charAt(0));
	}
	
	public static char eval(char operator, char operand1, char operand2){
		boolean oper1 = bool(operand1);
		boolean oper2 = bool(operand2);
		
		switch(operator){
		case 'C':
			if(!oper1){
				return '1';
			}else{
				if(oper2){
					return '1';
				}
			}
			return '0';
		case 'N':
			return charr(!oper1);
		case 'K':
			return charr(oper1 && oper2);
		case 'A':
			return charr(oper1 || oper2);
		case 'D':
			return charr(!(oper1 && oper2));
		case 'E':
			return charr(!(oper1 || oper2) || (oper1 && oper2));
		case 'J':
			return charr((oper1 && !oper2) || (!oper1 && oper2));
		}	
		return '0';
	}
	
	public static boolean bool(char c){
		if(c == '1'){
			return true;
		}else{
			return false;
		}
	}
	public static char charr(boolean b){
		if(b){
			return '1';
		}else{
			return '0';
		}
	}
}enum Result{
	INSUFF, EXTRA, BAD, GOOD
}