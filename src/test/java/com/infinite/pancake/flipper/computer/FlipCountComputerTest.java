package com.infinite.pancake.flipper.computer;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.infinite.pancake.flipper.PancakeFlipper;
import com.infinite.pancake.flipper.input.PancakeStackSet;

public class FlipCountComputerTest {

	FlipCountComputer computer = new FlipCountComputer();
	PancakeFlipper flipper = new FunctionalFlipper();

	PancakeStackSet stacks = null;

	@Test
	public void testNoops() {
		assertFlipsAndStacks(buildStacks(Lists.newArrayList("4", "+", "++", "+++", "++++")), 0, 0, 0, 0);
	}

	@Test
	public void testSingles() {
		assertFlipsAndStacks(buildStacks(Lists.newArrayList("4", "-", "--", "---", "----")), 1, 1, 1, 1);
	}

	@Test
	public void testProvidedCases() {
		assertFlipsAndStacks(buildStacks(Lists.newArrayList("5", "-", "-+", "+-", "+++", "--+-")), 1, 1, 2, 0, 3);
	}

	@Test
	public void testExtraCases() {
		assertFlipsAndStacks(buildStacks(Lists.newArrayList("4", "----", "++++", "----+", "++++-")), 1, 0, 1, 2);
	}

	@Test
	public void testAlmostWorstCase() {
		assertFlipsAndStacks(buildStacks(Lists.newArrayList("1", "-+-+-+-+")), 7);
	}

	@Test
	public void testWorstCase() {
		assertFlipsAndStacks(buildStacks(Lists.newArrayList("1", "+-+-+-+-")), 8);
	}

	private PancakeStackSet buildStacks(List<String> inputs) {
		this.stacks = new PancakeStackSet(inputs);
		return stacks;
	}

	private void assertFlipsAndStacks(PancakeStackSet stackSet, int... expectedFlips) {
		List<Integer> actualFlips = computer.computePancakeFlips(stackSet, flipper);

		// Basic checking
		Assert.assertNotNull("Flip results may not be null", actualFlips);

		// Make sure we actually computed the flips for each provided stack
		Assert.assertEquals("Unexpected number of flip results.", expectedFlips.length, actualFlips.size());

		// Make sure each of the stacks had the expected number of flips
		for(int i = 0; i < expectedFlips.length; i++) {
			Assert.assertEquals("Unexpected number of flips for set " + (i+1) + ".",
					expectedFlips[i], actualFlips.get(i).intValue());
		}

		// Make sure the final state of each stack is happy-side up
		for(List<Boolean> stack : stacks.getPancakeStacks()) {
			for(Boolean pancake : stack) {
				Assert.assertTrue(pancake.booleanValue());
			}
		}
	}

	private class FunctionalFlipper implements PancakeFlipper {

		@Override
		public int flip(int flipCount, List<Boolean> stack, int toIndexInclusive) {

			System.out.println("Before flip " + flipCount + ": " + stack);

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

			return flipCount + 1;
		}
	}
}
