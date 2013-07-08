package cz.cuni.mff.spl.sortapp;

import java.util.Random;

public class Main {
	
	/**
	 * Class for storing data for sorting.
	 * 
	 * @author Jaroslav Kotrc
	 *
	 */
	private static class SortHolder{
		public int numbers = 1000;
		public int cycles = 10;
		public int[][] arr1;
		public Integer[][] arr2;
		
		public String toString(){
			StringBuffer strb = new StringBuffer();
			strb.append("\nnumbers = ");
			strb.append(numbers);
			strb.append("\ncycles = ");
			strb.append(cycles);
			
			if(arr1!=null){
				strb.append("\nArray 1: \n");
				for(int[] row : arr1){
					strb.append('\n');
					for(int i : row){
						strb.append(i);
						strb.append(", ");
					}
				}
			}
			
			if(arr2!=null){
				strb.append("\nArray 2: \n");
				for(Integer[] row : arr2){
					strb.append('\n');
					for(Integer i : row){
						strb.append(i);
						strb.append(", ");
					}
				}
			}
			return strb.toString();
			
		}
	}
	
	/** Generates arrays for sorting and store it in provided holder.
	 * 
	 * @param holder class for storing data
	 */
	private static void generate(SortHolder holder){		
		Random rnd = new Random(77);
		
		holder.arr1 = new int[holder.cycles][holder.numbers];
		holder.arr2 = new Integer[holder.cycles][holder.numbers];
		
		for(int idxCycle = 0; idxCycle < holder.cycles; ++idxCycle){
			for(int idxNumber = 0; idxNumber < holder.numbers; ++idxNumber){
				int i = rnd.nextInt();
				holder.arr1[idxCycle][idxNumber] = i;
				holder.arr2[idxCycle][idxNumber] = i;
			}
		}
	}

	/**
	 * Generates data of default or specified length and runs quicksort
	 * and mergesort on them.
	 * @param args command line arguments can specify number of generated 
	 * 		integers and sorting cycles. If there are more than 2 arguments
	 * 		then information about sorting will be printed.
	 */
	public static void main(String[] args) {
		try{
			SortHolder holder = new SortHolder();
			if(args.length >= 1){
				holder.cycles = Integer.parseInt(args[0]);
			}
			if(args.length >= 2){
				holder.numbers = Integer.parseInt(args[1]);
			}
			if(args.length > 2){
				System.out.printf("Sorting %d integers in %d cycles.\n", holder.numbers, holder.cycles);
			}
			
			generate(holder);
			QuickSort.sort(holder.arr1);
			MergeSort.sort(holder.arr2);
			
			//System.out.println(holder.toString());
			
		} catch(NumberFormatException ex){
			System.out.println("Usage:\n\t " +
					"main <number of sorting cycles> <number of integers to sort>");
		}
		

	}

}
