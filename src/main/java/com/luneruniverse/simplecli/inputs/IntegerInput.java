package com.luneruniverse.simplecli.inputs;

import com.luneruniverse.simplecli.CommandParseException;

public class IntegerInput extends ArgumentOrFlagImpl<Integer> {
	
	@Override
	protected Integer parseUnfiltered(String str) throws CommandParseException {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new CommandParseException("Invalid integer '" + str + "'");
		}
	}
	
	public IntegerInput min(int min, String failMsg) {
		addFilter(num -> num < min ? failMsg : null);
		return this;
	}
	public IntegerInput min(int min) {
		return min(min, "Must be at least " + min);
	}
	
	public IntegerInput max(int max, String failMsg) {
		addFilter(num -> num <= max ? null : failMsg);
		return this;
	}
	public IntegerInput max(int max) {
		return max(max, "Must be at most " + max);
	}
	
}
