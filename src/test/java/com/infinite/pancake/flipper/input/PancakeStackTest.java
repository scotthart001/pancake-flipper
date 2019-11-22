package com.infinite.pancake.flipper.input;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class PancakeStackTest {

	@Test
	public void testVanilla() {
		new PancakeStackSet(buildList("1", 1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSizeMismatchTooSmall() {
		new PancakeStackSet(buildList("1", 0));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSizeMismatchTooLarge() {
		new PancakeStackSet(buildList("1", 2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSizeTooSmall() {
		new PancakeStackSet(buildList("0", 1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSizeTooLarge() {
		new PancakeStackSet(buildList("101", 101));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGarbageInput() {
		new PancakeStackSet(Lists.newArrayList("1", "snarf"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyInput() {
		new PancakeStackSet(Lists.newArrayList());
	}

	private static List<String> buildList(String firstEntry, int count) {
		List<String> list = new ArrayList<>();
		list.add(firstEntry);
		for(int i = 0; i < count; i++) {
			list.add("++++");
		}
		return list;
	}

}
