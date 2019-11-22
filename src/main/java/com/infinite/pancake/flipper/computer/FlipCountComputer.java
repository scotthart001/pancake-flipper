package com.infinite.pancake.flipper.computer;

import java.util.ArrayList;
import java.util.List;

import com.infinite.pancake.flipper.PancakeFlipper;
import com.infinite.pancake.flipper.input.PancakeStackSet;

public class FlipCountComputer {

	/**
	 * Method that computes the number of flips necessary to get all the pancake stacks happy-side-up
	 * <p>Basic algorithm: Look top to bottom. Each time we hit a blank, flip the stack above if happy. Then flip the
	 * stack at the blank unless the next is also blank.
	 * @param stackSet - {@link PancakeStackSet} representing the stacks of pancakes needing to be flipped
	 * to all happy-side up.
	 * @return List<Integer> containing the number of flips for each provided stack
	 */
	public List<Integer> computePancakeFlips(PancakeStackSet stackSet, PancakeFlipper flipper) {
		List<Integer> flipResults = new ArrayList<>();

		for(List<Boolean> stack : stackSet.getPancakeStacks()) {

			int flipCount = 0;

			Boolean last = null;
			for(int i = 0; i < stack.size(); i++) {

				Boolean current = stack.get(i);
				if(!Boolean.TRUE.equals(current)) {

					// Current pancake is blank. We need to flip either once or twice.

					if(Boolean.TRUE.equals(last)) {

						// Previous pancake was happy. Flip the stack one higher than this one to invert the above happy pancakes, so
						// when we flip again on this one they all end up happy again
						flipCount = flipper.flip(flipCount, stack, i-1);

						// Flip the stack with the current blank one on the bottom of the portion flipped
						flipCount = flipper.flip(flipCount, stack, i);
					} else {

						// Previous pancake was blank (or we are on the first pancake). We might want to skip this one so we can flip a whole stack
						// of blanks.

						// If we have another pancake deeper than the current one, peek at it
						if((i + 1) < stack.size()) {
							if(Boolean.TRUE.equals(stack.get(i + 1))) {
								// Next pancake is happy, so we are at the deepest of a stack of blanks. Flip.
								flipCount = flipper.flip(flipCount, stack, i);
							} else {
								// Next pancake is blank = move deeper without flipping
							}
						} else {
							// There are no deeper pancakes, and the bottom is blank. Flip.
							flipCount = flipper.flip(flipCount, stack, i);
						}
					}
				} else {
					// Current pancake is showing happy - move on to the next one down
				}

				last = stack.get(i);
			}

			flipResults.add(Integer.valueOf(flipCount));
		}

		return flipResults;
	}

}
