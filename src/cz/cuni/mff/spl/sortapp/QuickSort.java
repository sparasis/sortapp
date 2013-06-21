package cz.cuni.mff.spl.sortapp;

public class QuickSort {
	
	public static void sort(int[] arr){
		java.util.Arrays.sort(arr);
	}
	
	public static void sort(int[][] arr){
		for(int[] row : arr){
			sort(row);
		}
	}
}
