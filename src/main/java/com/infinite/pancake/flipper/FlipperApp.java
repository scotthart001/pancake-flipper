package com.infinite.pancake.flipper;

import com.infinite.pancake.flipper.computer.FlipCountComputer;
import com.infinite.pancake.flipper.input.PancakeStackSet;

public class FlipperApp {

	public static void main(String[] args) {
		new FlipCountComputer().computePancakeFlips(new PancakeStackSet(args), new FastFlipper());
	}

}
