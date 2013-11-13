package cz.cuni.mff.spl.sortapp;

/**
 * Provides methods for sorting arrays of integers by quicksort algorithm.
 * 
 * @author Jaroslav Kotrc
 *
 */
public class QuickSort {
	
	/** 
	 * Wrapper for library quicksort method.
	 * 
	 * @param arr the array to be sorted
	 */
	public static void sort(int[] arr){
		java.util.Arrays.sort(arr);
	}

	/**
	 * Calls sorting of every row in provided matrix of integers.
	 * 
	 * @param arr matrix which rows will be sorted
	 */
	public static void sort(int[][] arr){
		for(int[] row : arr){
			sort(row);
		}
	}
}
