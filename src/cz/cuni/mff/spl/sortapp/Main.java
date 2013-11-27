/*
 * Copyright (c) 2013, Jaroslav Kotrc
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package cz.cuni.mff.spl.sortapp;

import java.util.Random;

/**
 * Class with main method to run sorting.
 * 
 * @author Jaroslav Kotrc
 *
 */
public class Main {
	
	/**
	 * Class for storing data for sorting.
	 * 
	 * @author Jaroslav Kotrc
	 *
	 */
	private static class SortHolder{
		
		/** The numbers. */
		public int numbers = 1000;
		
		/** The cycles. */
		public int cycles = 10;
		
		/** The arr1. */
		public int[][] arr1;
		
		/** The arr2. */
		public Integer[][] arr2;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
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
	 * Number of recursion calls of method run which should be called before
	 * sorting is made.
	 *  */
	private static int recursionDepth = 0;
	
	/**
	 * Runs quicksort and mergesort on rows of two identical copies of integer
	 * matrices.
	 *
	 * @param holder class for storing data
	 * @param recursion the recursion
	 */
	private static void run(SortHolder holder, int recursion){
		if(recursion < recursionDepth){
			++recursion;
			run(holder, recursion);
		} else {
			QuickSort.sort(holder.arr1);
			MergeSort.sort(holder.arr2);
		}
	}
	
	/**
	 * Class for thread where sorting is made.
	 * 
	 * @author Jaroslav Kotrc
	 *
	 */
	private static class Sorter extends Thread{
		
		/** data to sort. */
		private SortHolder holder = new SortHolder();
		
		/**
		 * Creates thread class and initialize its data holder.
		 * @param cycles number of arrays to sort
		 * @param numbers number of integers in one array
		 */
		public Sorter(int cycles, int numbers){
			holder.numbers = numbers;
			holder.cycles = cycles;
			generate(holder);
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			Main.run(holder, 0);
		}
		
	}

	/**
	 * Generates data of default or specified length and runs quicksort
	 * and mergesort on them.
	 * The command line arguments means:
	 * 		<ol>
	 * 		<li>number of integers in one array</li>
	 * 		<li>number of arrays to sort</li>
	 * 		<li>number of recursion calls before sorting begins</li>
	 * 		<li>number of threads for sorting</li>
	 * 		<li>if any value is present then information about sorting will be printed</li>
	 * 		</ol>
	 * 	Arguments can be omitted from back to front.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		int threadCnt = 1;
		int numbers = 1000;
		int cycles = 10;
		try{
			if(args.length >= 1){
				numbers = Integer.parseInt(args[0]);
			}
			if(args.length >= 2){
				cycles = Integer.parseInt(args[1]);
			}
			if(args.length >= 3){
				recursionDepth = Integer.parseInt(args[2]);
			}
			if(args.length >= 4){
				threadCnt = Integer.parseInt(args[3]);
			}
			if(args.length > 4){
				System.out.printf("Sorting %d integers in %d cycles with %d recursion depth " +
						"and %d threads.\n", numbers, cycles, recursionDepth, threadCnt);
			}
			
			Sorter[] sorters = new Sorter[threadCnt];
			for(int cnt = 0; cnt < threadCnt; ++cnt){
				sorters[cnt] = new Sorter(cycles, numbers);
			}
			for(int cnt = 0; cnt < threadCnt; ++cnt){
				sorters[cnt].start();
			}
			for(int cnt = 0; cnt < threadCnt; ++cnt){
				try {
					sorters[cnt].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
						
			//System.out.println(holder.toString());
			
		} catch(NumberFormatException ex){
			System.out.println("Usage:\n\t " +
					"main <number of sorting cycles> <number of integers to sort>" +
					" <number of recursion calls before sorting> <number of threads for sorting>" +
					" <anything if information about sorting should be printed>");
		}
		

	}

}
