package com.infinite.pancake.flipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.infinite.pancake.flipper.input.PancakeStackSet;

/**
 * Implementation of a fast pancake flipper, with x-ray vision, that puts all pancakes happy-side up as fast
 * as possible.
 * @author shart
 */
public class FastFlipper {

	public static void main(String[] args) {
		new FastFlipper().reportComputedPancakeFlips(new PancakeStackSet(args));
	}

	private boolean mutateStacks = false;

	/**
	 * Construct a {@link FastFlipper} that only computes the number of flips to make
	 * for each stack, but does not actually flip the stacks.
	 */
	public FastFlipper() { }

	/**
	 * Construct a {@link FastFlipper} that optionally performs the flips on the stacks,
	 * and reports the state of each stack as it performs the flips.
	 * @param mutateStacks - Boolean indicating whether the stacks should actually be
	 * flipped when computing the number of flips to make.
	 */
	public FastFlipper(boolean mutateStacks) {
		this.mutateStacks = mutateStacks;
	}

	/**
	 * Method that computes the number of flips necessary to get all the pancake stacks happy-side-up
	 * <p>Basic algorithm: Look top to bottom. Each time we hit a blank, flip the stack above if happy. Then flip the
	 * stack at the blank unless the next is also blank.
	 * @param stackSet - {@link PancakeStackSet} representing the stacks of pancakes needing to be flipped
	 * to all happy-side up.
	 * @return List<Integer> containing the number of flips for each provided stack
	 */
	public List<Integer> computePancakeFlips(PancakeStackSet stackSet) {
		List<Integer> flipResults = new ArrayList<>();

		for(List<Boolean> stack : stackSet.getPancakeStacks()) {

			if(mutateStacks) System.out.println("Initial state: " + stack);

			int flipCount = 0;

			Boolean last = null;
			for(int i = 0; i < stack.size(); i++) {

				Boolean current = stack.get(i);
				if(!Boolean.TRUE.equals(current)) {

					// Current pancake is blank. We need to flip either once or twice.

					if(Boolean.TRUE.equals(last)) {

						// Previous pancake was happy. Flip the stack one higher than this one to invert the above happy pancakes, so
						// when we flip again on this one they all end up happy again
						flipCount = flip(flipCount, stack, i-1);

						// Flip the stack with the current blank one on the bottom of the portion flipped
						flipCount = flip(flipCount, stack, i);
					} else {

						// Previous pancake was blank (or we are on the first pancake). We might want to skip this one so we can flip a whole stack
						// of blanks.

						// If we have another pancake deeper than the current one, peek at it
						if((i + 1) < stack.size()) {
							if(Boolean.TRUE.equals(stack.get(i + 1))) {
								// Next pancake is happy, so we are at the deepest of a stack of blanks. Flip.
								flipCount = flip(flipCount, stack, i);
							} else {
								// Next pancake is blank = move deeper without flipping
							}
						} else {
							// There are no deeper pancakes, and the bottom is blank. Flip.
							flipCount = flip(flipCount, stack, i);
						}
					}
				} else {
					// Current pancake is showing happy - move on to the next one down
				}

				last = stack.get(i);
			}

			flipResults.add(Integer.valueOf(flipCount));

			if(mutateStacks) System.out.println("");
		}

		return flipResults;
	}

	/**
	 * Method that outputs the number of flips necessary to get all the pancake stacks happy-side-up
	 * @param stackSet - {@link PancakeStackSet} representing the stacks of pancakes needing to be flipped
	 * to all happy-side up
	 */
	public void reportComputedPancakeFlips(PancakeStackSet stackSet) {
		List<Integer> flipResults = computePancakeFlips(stackSet);
		for(int i = 0; i < flipResults.size(); i++) {
			System.out.println("Case #" + (i+1) + ": " + flipResults.get(i));
		}
	}

	private int flip(int flipCount, List<Boolean> stack, int toIndexInclusive) {

		if(mutateStacks) {

			// Grab the portion of the stack to flip and reverse the order
			List<Boolean> portionToFlip = stack.subList(0, toIndexInclusive + 1);
			Collections.reverse(portionToFlip);

			// Now invert the state of the flipped pancakes
			for(int i = 0; i < portionToFlip.size(); i++) {
				Boolean currentState = stack.get(i);
				if(Boolean.TRUE.equals(currentState)) {
					portionToFlip.set(i, Boolean.FALSE);
				} else {
					portionToFlip.set(i, Boolean.TRUE);
				}
			}

			System.out.println("After flip " + (flipCount + 1) + ", turning over top " + (toIndexInclusive + 1) + " pancakes: " + stack);
		}

		return flipCount + 1;
	}
}
