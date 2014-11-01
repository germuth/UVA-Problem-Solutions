package ca.germuth.acm_southeastusa_2012.div2.A;

import java.util.Scanner;

/**
 * Candy Store
 * ACM ICPC SouthEast USA 2012 Regionals
 * Division 2 Problem A
 * 
 * Pretty sure this is correct but I can't find the judge i used
 * 
 * This can be solved using one dimensional dynamic programming.
 * Start at money = 0, and solve for the best candy configuration, then
 * slowly increment money until you equal the input amount.
 * Make sure you use cents, not dollars.
 * Also the best possible configuration might not be with the most amount of money
 * (since at each money amount, you only solve for candy amounts which equal exactly it)
 * 
 * @author germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (true) {
			int candies = s.nextInt();
			double monies = s.nextDouble();

			if (candies == 0) {
				break;
			}
			Candy[] candy = new Candy[candies];
			for (int i = 0; i < candies; i++) {
				candy[i] = new Candy(s.nextInt(), s.nextDouble());
			}

			// we will deal with money in terms of integers
			int money = ((int) (monies * 100));

			// need position for every cent from 0.00 to money, including 0
			int[] bestCaloriesSoFar = new int[money + 1];

			// start from cost 0.00, go up by one cent each time
			for (int cost = 0; cost <= money; cost++) {

				// go through each candy option
				for (int j = 0; j < candy.length; j++) {
					Candy currCandy = candy[j];

					int caloriesNewSolution = bestCaloriesSoFar[cost]
							+ currCandy.calories;
					int newMoneyValue = cost + (int) (currCandy.cost * 100);

					// only consider if it is buyable
					if (newMoneyValue <= money) {
						// update best solution so far if better
						if (caloriesNewSolution > bestCaloriesSoFar[newMoneyValue]) {
							bestCaloriesSoFar[newMoneyValue] = caloriesNewSolution;
						}
					}
				}
			}

			int maxCalories = Integer.MIN_VALUE;
			// find which money we can afford the most calories at
			for (int i = 0; i < bestCaloriesSoFar.length; i++) {
				if (maxCalories < bestCaloriesSoFar[i]) {
					maxCalories = bestCaloriesSoFar[i];
				}
			}
			System.out.println(maxCalories);
		}

		s.close();
	}
}

class Candy {
	int calories;
	double cost;

	public Candy(int cal, double c) {
		this.calories = cal;
		this.cost = c;
	}
}
