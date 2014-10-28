package ca.germuth.uva_110601;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

/**
 * How Many Fibs?
 * 6.6.1
 * PC/UVA ID: 110601/10183
 * 
 * At first 10^100 seems like a massive number and the really simple way won't work. 
 * However the fibonacci series grows really fast, and fib(500) is already way above
 * 10^100. Therefore you can just calculate the fibonacci numbers directly. 
 * One "ambiguity" within the problem statement is that f(0) is NOT considered a fibonacci number.
 * Also use BigIntegers
 * @author Germuth
 */
class Main {
	static HashMap<Integer, BigInteger> fibs = new HashMap<Integer, BigInteger>();
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		while(true){
			String first = s.next();
			String second = s.next();
			
			BigInteger a = new BigInteger(first);
			BigInteger b = new BigInteger(second);
			
			if(first.trim().equals("0") && second.trim().equals("0")){
				break;
			}
			
			int count = 0;
			int n = 1;
			BigInteger fn = new BigInteger("1");
			
			//loop through each fibonnacci number starting from n = 1
			//once we reach or exceed a, start counting
			//and count until we reach b
			while(fn.compareTo(b) <= 0){
				if(fn.compareTo(a) >= 0){
					count++;
				}
				
				n++;
				fn = fib(n);
			}
			System.out.println(count);
			
		}
		s.close();
	}
	
	static BigInteger fib(int n){
		if(fibs.containsKey(n)){
			return fibs.get(n);
		}else if(n == 1){
			return new BigInteger("1");
		}else if(n == 2){
			return new BigInteger("2");
		}else{
			BigInteger ans = fib(n-1).add(fib(n-2));
			fibs.put(n, ans);
			return ans;
		}
	}
}
