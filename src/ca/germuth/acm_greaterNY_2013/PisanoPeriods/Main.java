package ca.germuth.acm_greaterNY_2013.PisanoPeriods;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Pissano Periods
 * ACM ICPC Greater NY 2013 Regionals
 * Problem D
 * 
 * TODO add explanation 
 */
class Main {
        public static void main(String[] args){
                Scanner s = new Scanner(System.in);
                
//              fib(1000);
//              fib(2000);
//              fib(3000);
//              fib(4000);
//              fib(5000);
//              fib(6000);
//              fib(7000);
//              fib(8000);
//              fib(9000);
//              fib(10000);
//              fib(11000);
//              fib(12000);
//              fib(13000);
//              fib(14000);
//              fib(15000);
//              fib()
//              System.out.println(fib(15454));
//              System.out.println(fib(15455));
//              System.out.println(fib(15456));
                
                int cases = s.nextInt();
                
                for(int a = 0; a < cases; a++){
                        int caseNum = s.nextInt();
                        
                        int m = s.nextInt();
                        
//                      System.out.println(caseNum + " " + solve(m));
                        System.out.println(caseNum + " " + k(m));
                        
                }
                s.close();
        }
        
        //official solution:
        public static int k(int m)
        {
                /*
                 * int's are fine since max remainder is (x%1000000) <= 999999
                 */
                int nSeq, f1, f2;
                
                /* Special case is easy */
                if(m == 2){
                        return(3);
                }
                nSeq = 2;
                /* Prime Fibonacci pump */
                f1 = 1;
                f2 = 1;
                for(;; nSeq += 2){
                        /*
                         * we do 2 values at a time, since if m > 2, k(m) is even
                         */
                        /* Next value in sequence remainder */
                        f1 = (f1 + f2) % m;
                        /* Value after that remainder */
                        f2 = (f2 + f1) % m;
                        /* Sequence repeats when next 2 values match first 2 which
                         * are always 1
                         */
                        if(f1 == 1 && f2 == 1){
                                break;
                        }
                }
                return(nSeq);
        }
        
        //failure solution
        static HashMap<Long, BigInteger> map = new HashMap<Long, BigInteger>();
        public static BigInteger fib(long n){
                if(map.containsKey(n)){
                        
                        return map.get(n);
                }else{
                        if(n == 0 || n == 1){
                                return BigInteger.ONE;
                        }
                        BigInteger ans = fib(n-1).add((fib(n-2)));
                        map.put(n, ans);
                        return ans;
                }
        }
        public static long solve(int m){
                long n = 2;
                long seqAmount = 2;
                while(true){
                        BigInteger next = fib(n).mod(new BigInteger(m + ""));
                        BigInteger next2 = fib(n+1).mod(new BigInteger(m + ""));
                        
                        if(next.equals(BigInteger.ONE) && next2.equals(BigInteger.ONE)){
                                break;
                        }
                        
                        n += 2;
                        seqAmount +=2;
                }
                return seqAmount;
        }
        
}
