package ca.germuth.uva_10110;

/**
 * Light, more light
 * 7.6.1
 * PC/UVA 110901/10110
 * 
 * Start by listing out all numbers and when they are divisible. You
 * are basically listing all factors for a given number. When
 * the amount of factors they have is even, the light will be off.
 * When odd, the light will be on. But because factors come in pairs:
 * 1*12, 2*6, 3*4, etc, there will always be an even amount. Unless
 * that the number is a perfect square, for ex.
 * 16 = 1*16, 2*8, 4*4
 * But 4 and 4 are same number, so the light is only switched once 
 * and remains on. You can easily test for perfect square by
 * if(n == sqrt(n)*sqrt(n) )
 * 
 * In java, the Integer goes up to 2^31 - 1, which is less than the input, 
 * so longs must be used. Be especially carefull when performing
 * sqrt(n)*sqrt(n) since that value may also overflow integers
 * meaning sqrt(n) must be a long before the calculation because
 * long l = int*int, the ints will overflow and then be 
 * converted to a long with overflow value
 */
import java.util.Scanner;

class Main {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        while (true) {
            long n = s.nextLong();
            if (n == 0) {
                break;
            }
            
            long root = (long)Math.sqrt(n);
            if(root * root == n){
            	System.out.println("yes");
            }else{
            	System.out.println("no");
            }
        }
        s.close();
    }
}
