package uk.co.lukestevens.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiConsumer;

public class Utils {
	
	public static List<List<Integer>>  findPermutations(List<Integer> items) {
		List<List<Integer>> combinations = new ArrayList<>();
		findPermutations(new HashSet<>(items), new Stack<Integer>(), items.size(), combinations);
		return combinations;
	}
	
	static void findPermutations(Set<Integer> items, Stack<Integer> permutation, int size, List<List<Integer>> combinations) {

	    /* permutation stack has become equal to size that we require */
	    if(permutation.size() == size) {
	        combinations.add(Arrays.asList(permutation.toArray(new Integer[0])));
	    }

	    /* items available for permutation */
	    Integer[] availableItems = items.toArray(new Integer[0]);
	    for(Integer i : availableItems) {
	        /* add current item */
	        permutation.push(i);

	        /* remove item from available item set */
	        items.remove(i);

	        /* pass it on for next permutation */
	        findPermutations(items, permutation, size, combinations);

	        /* pop and put the removed item back */
	        items.add(permutation.pop());
	    }
	}
	
	public static int getGreatestCommonDivisor(int a, int b) {
		return b==0 ? a : getGreatestCommonDivisor(b, a%b);
	}
	
	public static <T> void forEachPair(List<T> list, BiConsumer<T, T> method) {
		for(int i = 0; i<list.size(); i++) {
			for(int j = i+1; j<list.size(); j++) {
				method.accept(list.get(i), list.get(j));
			}
		}
	}
	
	// TODO Terribly inefficient, pls improve
	public static long getLowestCommonMultiple(long...nums) {
		long result = 1;
		while(true) {
			if(isDivisible(result, nums)) {
				return result;
			}
			result++;
		}
	}
	
	public static boolean isDivisible(long toCheck, long...nums) {
		for(long num : nums) {
			if(toCheck % num != 0) {
				return false;
			}
		}
		return true;
	}

}
