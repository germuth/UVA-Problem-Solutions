/**
 * Ones
 * 5.9.4
 * PC/UVA ID: 11504/10127
 *
 * The solution to this ones means you need to know your modulus rules.
 * Basically 111 % 5 = ((10 % 5 + 1 % 5) * 10 % 5) % 5
 *
 * See here for a much better explanation than I could ever do:
 * http://www.mathblog.dk/uva-10127-ones/
 * @author Germuth
 */
#include <stdio.h>
int main(int argc, char *argv[]){
    int input = 0;
    while(scanf("%d", &input) != EOF){
        if(input == 0){
            printf("%d\n", input);
            continue;
        }
        int ones = 1;
        int digits = 1;
        while(ones % input){
            ones = (ones * 10 + 1) % input;

            digits++;
        }
        printf("%d\n", digits);
    }
}
