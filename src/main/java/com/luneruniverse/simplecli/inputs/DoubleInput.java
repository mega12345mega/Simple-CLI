package com.luneruniverse.simplecli.inputs;

import com.luneruniverse.simplecli.CommandParseException;

public class DoubleInput extends ArgumentOrFlagImpl<Double> {
	
	@Override
	protected Double parseUnfiltered(String str) throws CommandParseException {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			throw new CommandParseException("Invalid floating point number '" + str + "'");
		}
	}
	
	public DoubleInput min(double min, String failMsg) {
		addFilter(num -> num < min ? failMsg : null);
		return this;
	}
	public DoubleInput min(double min) {
		return min(min, "Must be at least " + min);
	}
	
	public DoubleInput max(double max, String failMsg) {
		addFilter(num -> num <= max ? null : failMsg);
		return this;
	}
	public DoubleInput max(double max) {
		return max(max, "Must be at most " + max);
	}
	
}
