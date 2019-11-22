package com.infinite.pancake.flipper.input;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Represents a set of pancake stacks needing to be flipped happy-side-up.
 * @author shart
 */
public class PancakeStackSet {

	private int stackCount = 0;
	private List<List<Boolean>> stacks = new ArrayList<>();

	/**
	 * Convenience constructor allowing direct invocation from command line arguments
	 * @param input
	 */
	public PancakeStackSet(String[] input) {
		this(Lists.newArrayList(input));
	}

	/**
	 * Constructs a new PancakeStackSet from the provided string inputs. The first string
	 * should contain a numeric value matching the count of other provided values, and the remainder
	 * should be strings composed solely of '-' or '+' characters.
	 * <p>Enforced limits:
	 * <ul><li>Minimum stack size: 1</li>
	 * <li>Maximum stack size: 100</li>
	 * </ul>
	 * @param input
	 */
	public PancakeStackSet(List<String> input) {

		// Basic validation on the input
		if(null == input) throw new IllegalArgumentException("Input may not be null");

		// Enforce size limits
		if(input.size() < 2) throw new IllegalArgumentException("Input must have at least a size entry and one stack input");
		if(input.size() > 101) throw new IllegalArgumentException("Input may not exceed a size entry and 100 items");

		// Retrieve the number of inputs as the first item in the list of provided inputs
		try {
			this.stackCount = Integer.parseInt(input.get(0));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input size was not a valid number.");
		}

		// Pop off the size from the input list
		input.remove(0);

		// Validate that the count actually matches the input size
		if(stackCount != input.size()) throw new IllegalArgumentException("Input size does not match the number of provided inputs.");

		// Validate that the input strings are legal characters, and convert to easier to understand boolean states
		for(String inputValue : input) {
			List<Boolean> stackState = new ArrayList<>();
			for(char inputChar : inputValue.toCharArray()) {
				switch(inputChar) {
				case '-':
					stackState.add(Boolean.FALSE);
					break;
				case '+':
					stackState.add(Boolean.TRUE);
					break;
				default:
					throw new IllegalArgumentException("Illegal input character '" + inputChar + "'");
				}
			}
			stacks.add(stackState);
		}
	}

	/**
	 * Get the state of the pancake stacks. Boolean.TRUE represents a pancake happy-side-up, the desired state.
	 */
	public List<List<Boolean>> getPancakeStacks() {
		return stacks;
	}

}
