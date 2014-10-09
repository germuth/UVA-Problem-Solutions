package ca.germuth.acm_pacnw_2012.H;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Seating Chart Problem H
 * 
 * If you know the algorithm for counting inverted pairs, you can solve this problem.
 * @author Germuth
 */
class Main {
	static HashMap<Character, Long> duration = new HashMap<Character, Long>();
	static long[][] graph;
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in); 
//		Scanner s = null;
//		try {
//			s = new Scanner(new File("test.txt"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int n = s.nextInt();
		while(n != 0){
			HashMap<String, Integer> first = new HashMap<String, Integer>();
			for(int i = 0; i < n; i++){
				first.put(s.next(), i);
			}
			int[] arr = new int[n];
			//create second array with the indices the strings are found in the first array
			//therefore, sorting this string is equivalent to sorting the 2nd set of names
			//to the first set of names
			for(int i = 0; i < n; i++){
				arr[i] = first.get(s.next());
			}
			
			System.out.println(inversionCount(arr, 0, n));
			
			n = s.nextInt();
		}
		s.close();
	}

	//we are effectively trying to count the number of inversions it takes
	//to sort the array. we can recursively break down the array in halfs, and 
	//then merge each half, just like merge-sort. 
	//when merging, if there was no inversions, we would simply add the first array
	//and then the second to merge. Everytime we have to grab the next smallest value
	//from the second array, it represents multiple inversions. One inversion for each 
	//value left in the left array, which equals the number of swaps it would take to 
	//sort that value
	public static long inversionCount(int[] arr, int start, int end) {
		if (end - start <= 1)
			return 0;

		long answer = 0;
		int mid = (start + end) / 2;
		answer += inversionCount(arr, start, mid) + inversionCount(arr, mid, end);

		int index1 = 0, index2 = 0;
		//index2 counts the number of arrL > arrR
		
		while(start + index1 < mid && mid + index2 <= end - 1){
			if(arr[start + index1] > arr[mid + index2]){
				//number at mid+index2 is smaller than start+index1 and everyother element in left array
				answer += (mid - start - index1);
				index2++;
			}else{
				index1++;
			}
		}


		Arrays.sort(arr, start, end);

		return answer;
	}
}