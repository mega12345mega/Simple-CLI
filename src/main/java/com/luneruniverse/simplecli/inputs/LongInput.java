package com.luneruniverse.simplecli.inputs;

import com.luneruniverse.simplecli.CommandParseException;

public class LongInput extends ArgumentOrFlagImpl<Long> {
	
	@Override
	protected Long parseUnfiltered(String str) throws CommandParseException {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			throw new CommandParseException("Invalid long '" + str + "'");
		}
	}
	
	public LongInput min(long min, String failMsg) {
		addFilter(num -> num < min ? failMsg : null);
		return this;
	}
	public LongInput min(long min) {
		return min(min, "Must be at least " + min);
	}
	
	public LongInput max(long max, String failMsg) {
		addFilter(num -> num <= max ? null : failMsg);
		return this;
	}
	public LongInput max(long max) {
		return max(max, "Must be at most " + max);
	}
	
}
