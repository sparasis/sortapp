package cz.cuni.mff.spl.sortapp;

/**
 * Provides methods for sorting arrays of integers by mergesort algorithm.
 * 
 * @author Jaroslav Kotrc
 *
 */
public class MergeSort {
	
	/** 
	 * Wrapper for library mergesort method.
	 * 
	 * @param arr the array to be sorted
	 */
	public static void sort(Integer[] arr){
		java.util.Arrays.sort(arr);
	}

	/**
	 * Calls sorting of every row in provided matrix of Integers.
	 * 
	 * @param arr matrix which rows will be sorted
	 */
	public static void sort(Integer[][] arr){
		for(Integer[] row : arr){
			sort(row);
		}
	}
}
