package cz.cuni.mff.spl.sortapp;

public class MergeSort {
	
	public static void sort(Integer[] arr){
		java.util.Arrays.sort(arr);
	}

	
	public static void sort(Integer[][] arr){
		for(Integer[] row : arr){
			sort(row);
		}
	}
}
