package com.infinite.pancake.flipper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.infinite.pancake.flipper.input.PancakeStackSet;

public class FastFlipperTest {

	FastFlipper flipper = new FastFlipper(true);

	PancakeStackSet stacks = null;

	@Test
	public void testNoops() {
		assertFlipsAndStacks(flipper.computePancakeFlips(buildStacks(Lists.newArrayList("4", "+", "++", "+++", "++++"))), 0, 0, 0, 0);
	}

	@Test
	public void testSingles() {
		assertFlipsAndStacks(flipper.computePancakeFlips(buildStacks(Lists.newArrayList("4", "-", "--", "---", "----"))), 1, 1, 1, 1);
	}

	@Test
	public void testProvidedCases() {
		assertFlipsAndStacks(flipper.computePancakeFlips(buildStacks(Lists.newArrayList("5", "-", "-+", "+-", "+++", "--+-"))), 1, 1, 2, 0, 3);
	}

	@Test
	public void testExtraCases() {
		assertFlipsAndStacks(flipper.computePancakeFlips(buildStacks(Lists.newArrayList("4", "----", "++++", "----+", "++++-"))), 1, 0, 1, 2);
	}

	@Test
	public void testAlmostWorstCase() {
		assertFlipsAndStacks(flipper.computePancakeFlips(buildStacks(Lists.newArrayList("1", "-+-+-+-+"))), 7);
	}

	@Test
	public void testWorstCase() {
		assertFlipsAndStacks(flipper.computePancakeFlips(buildStacks(Lists.newArrayList("1", "+-+-+-+-"))), 8);
	}

	private PancakeStackSet buildStacks(List<String> inputs) {
		this.stacks = new PancakeStackSet(inputs);
		return stacks;
	}

	private void assertFlipsAndStacks(List<Integer> actualFlips, int... expectedFlips) {

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
}
