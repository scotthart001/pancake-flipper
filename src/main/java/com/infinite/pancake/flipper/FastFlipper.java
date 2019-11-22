package com.infinite.pancake.flipper;

import java.util.List;

/**
 * Implementation of a fast pancake flipper, with x-ray vision, that puts all pancakes happy-side up as fast
 * as possible.
 * @author shart
 */
public class FastFlipper implements PancakeFlipper {

	@Override
	public int flip(int flipCount, List<Boolean> stack, int toIndexInclusive) {
		return flipCount + 1;
	}
}
