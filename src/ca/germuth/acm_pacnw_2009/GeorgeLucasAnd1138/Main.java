package ca.germuth.acm_pacnw_2009.GeorgeLucasAnd1138;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
/**
 * George Lucas and 1138
 * ACM ICPC Pacific Northwest Regionals 2009
 * Problem G
 * 
 * NOT YET SOLVED
 * 
 * This problem is also brute force, but it isn't the easiest solution to come up with. 
 * My first approach was to generate all possible permutations of the input string. 
 * And then for each one, do the following:
 * lets the input numbers are [1, 1, 3, 8]
 * you would take each adjacent pair and combine them with each operation and continue
 * once the size of this array gets to 1, add that number to some list. for ex.
 * [1, 1, 3, 8] 
 * ------------
 * [1+1, 3, 8]
 * [1-1, 3, 8]
 * [1*1, 3, 8]
 * [1/1, 3, 8]
 * then again for the next two adjacent position
 * [1, 1+3, 8]
 * [1, 1-3, 8]
 * [1, 1*3, 8]
 * [1, 1/3, 8]
 * 
 * The actual array combining was somewhat fast, but since there are 5040 permutations
 * for a string of length 7, it had to be repeated 5000 times. This was far to slow, but
 * lended towards the idea of choosing all possible pairs of integers out of the
 * array and doing the same thing. 
 * You must also, For each pair selected, perform the operation in both directions, i.e
 * x - y AND y - x
 * x / y AND y / x
 * This means we will have to create two arrays for each subtraction and division.
 *
 * However, This was still too slow. 
 * TODO
 * come back and solve this one
 * 
 * @author Aaron
 */
class Main {
	static HashMap<String, Integer> visited = new HashMap<String, Integer>();
	static Set<Integer> answers = new HashSet<Integer>();
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);

		while(true){
			String in = s.next();
			
			if(in.equals("0")){
				break;
			}
			
			//translate string into int array
			int[] integers = new int[in.length()];
			for(int i = 0; i < in.length(); i++){
				integers[i] = Integer.parseInt(in.charAt(i) + "");
			}
			//get all numbers from all combinations
			//store in static answers
			getAllNumbers(integers);
			
			//find lowest positive integer that we don't have
			HashMap<Integer, Integer> seen = new HashMap<Integer, Integer>();
			Iterator<Integer> iter2 = answers.iterator();
			while(iter2.hasNext()){
				seen.put(iter2.next(), 0);
			}
			
			int min = -1;
			for(int i = 1; i < Integer.MAX_VALUE; i++){
				if(!seen.containsKey(i)){
					min = i;
					break;
				}
			}
			
			System.out.println(min);
			
			answers.clear();
//			visited.clear();
		}
		
		s.close();
	}
	
	public static void getAllNumbers(int[] num){
//		Arrays.sort(num);
//		if(visited.containsKey(num.toString())){
//			return;
//		}else{
//			visited.put(num.toString(), 1);
//		}
		
		int n = num.length;
		if(n == 1){
			answers.add(num[0]);
			return;
		}else if(n == 2){
//			if(num[0] + num[1] == 29 ||
//					num[0] - num[1] == 29 || 
//					(num[1] != 0 &&
//					num[0] / num[1] == 29)){
//				System.out.println("");
//			}
			answers.add(num[0] + num[1]);
			
			answers.add(num[0] - num[1]);
			answers.add(num[1] - num[0]);
			
			answers.add(num[0] * num[1]);
			if(num[1] != 0){
				answers.add(num[0] / num[1]);
			}
			if(num[0] != 0){
				answers.add(num[1] / num[0]);
			}
			return;
		}
		
		//all possible pairs of integers (first and second variable)
		for(int i = 0; i < n; i++){
			for(int k = i + 1; k < n; k++){
				int first = num[i];
				int second = num[k];
				
				//for each operation, +,-,*,/
				for(int operation = 0; operation < 4; operation++){
					//create new array
					int[] newNumbers = new int[n-1];
					int ind = 0;
					//add all elements except first and second
					for(int j = 0; j < n; j++){
						if(j != i && j != k){
							newNumbers[ind++] = num[j];							
						}
					}
					//add combined result to end of array
					//need to create second array if subtraction or division
					switch(operation){
						case 0: newNumbers[n-2] = first + second; 
								break;
						case 1: newNumbers[n-2] = first - second; 
								int[] subtraction = Arrays.copyOf(newNumbers, n-1);
								subtraction[n-2] = second - first;
								getAllNumbers(subtraction); 
								break;
						case 2: newNumbers[n-2] = first * second; 
								break;
						case 3: if(second != 0){
									newNumbers[n-2] = (first / second);
								}
								if(first != 0){
									int[] division = Arrays.copyOf(newNumbers, n-1);
									division[n-2] = second / first;
									getAllNumbers(division);
								}	
								break;
					}
					
					if(operation != 3 && second != 0){
						getAllNumbers(newNumbers);						
					}
				}
			}
		}
	}
	
	//First Solution, that was too slow
//	public static void main(String[] args){
//		Scanner s = new Scanner(System.in);
//
//		while(true){
//			String in = s.next();
//			
//			if(in.equals("0")){
//				break;
//			}
//			//generate all permutations
//			permutation(in);
//			
//			//1, 2, 4, 5, 10, 19, 20, 38, 76, 95, 190, 380
//			
//			Iterator<String> iter = perms.iterator();
//			while(iter.hasNext()){
//				String permute = iter.next();
//				int[] integers = new int[permute.length()];
//				for(int i = 0; i < permute.length(); i++){
//					integers[i] = Integer.parseInt(permute.charAt(i) + "");
//				}
//				getAllNumbers(integers);
//			}
//		
//			HashMap<Integer, Integer> seen = new HashMap<Integer, Integer>();
//			Iterator<Integer> iter2 = answers.iterator();
//			while(iter2.hasNext()){
//				seen.put(iter2.next(), 0);
//			}
//			
//			int min = -1;
//			for(int i = 1; i < Integer.MAX_VALUE; i++){
//				if(!seen.containsKey(i)){
//					min = i;
//					break;
//				}
//			}
//			
//			System.out.println(min);
//			
//			answers.clear();
//			perms.clear();
//		}
//		
//		s.close();
//	}
//	
//	static Set<Integer> answers = new HashSet<Integer>();
//	public static void getAllNumbers(int[] num){
//		int n = num.length;
//		if(n == 1){
//			answers.add(num[0]);
//			return;
//		}else if(n == 2){
//			answers.add(num[0] + num[1]);
//			answers.add(num[0] - num[1]);
//			answers.add(num[0] * num[1]);
//			if(num[1] != 0){
//				answers.add((num[0] / num[1]));
//			}
//			return;
//		}
//		
//		for(int i = 0; i < n - 1; i++){
//			int first = num[i];
//			int second = num[i+1];
//			
//			for(int operation = 0; operation < 4; operation++){
//				int[] newNumbers = new int[n-1];
//				for(int j = 0; j < n-1; j++){
//					if(j < i){
//						newNumbers[j] = num[j];
//					}else if(j == i){
//						switch(operation){
//						case 0: newNumbers[j] = first + second; break;
//						case 1: newNumbers[j] = first - second; break;
//						case 2: newNumbers[j] = first * second; break;
//						case 3: if(second != 0){
//									newNumbers[j] = (first / second);
//								} break;
//						}
//					}else{
//						newNumbers[j] = num[j+1];
//					}
//				}				
//				getAllNumbers(newNumbers);
//			}
//		}
//	}
//	
//	//WITHOUT DUPLICATES
//	static Set<String> perms = new HashSet<String>();
//	public static void permutation(String str) { 
//	    permutation("", str); 
//	}
//
//	private static void permutation(String prefix, String str) {
//	    int n = str.length();
//	    if (n == 1) {
//	    	perms.add(prefix + str);
//	    }
//	    else {
//	        for (int i = 0; i < n; i++){
//	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
//	        }
//	    }
//	}
}
