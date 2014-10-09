package ca.germuth.acm_pacnw_2013.test;

//Binary search on all-pairs shortest paths.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	  // side effect: sort(arr, start, end)
	  static long inversionCount(int[] arr, int start, int end) {
	    if (end - start <= 1) return 0;
	    
	    long answer = 0;
	    int mid = (start + end) / 2;
	    answer += inversionCount(arr, start, mid) + inversionCount(arr, mid, end);
	    
	    int index1 = 0, index2 = 0;
	    while (start + index1 < mid) {
	      if (mid + index2 == end || arr[start + index1] < arr[mid + index2]) {
	        index1++;
	        answer += index2;
	      } else {
	        index2++;
	      }
	    }
	    
	    Arrays.sort(arr, start, end);
	    
	    return answer;
	  }
	  
	  public static void main(String[] args) {
//	    Scanner s = new Scanner(System.in);
		  Scanner s = null;
		  try {
			s = new Scanner(new File("test.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    while (true) {
	      int n = s.nextInt();
	      if (n == 0) break;
	      
	      HashMap<String, Integer> map = new HashMap<String, Integer>();
	      for (int i = 0; i < n; i++) map.put(s.next(), i);
	      
	      int[] arr = new int[n];
	      for (int i = 0; i < n; i++) arr[i] = map.get(s.next());
	      
	      System.out.println(inversionCount(arr, 0, n));
	    }
	  }
	

}
