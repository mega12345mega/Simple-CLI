package com.luneruniverse.simplecli.inputs;

import com.luneruniverse.simplecli.CommandParseException;

public class StringInput extends ArgumentOrFlagImpl<String> {
	
	@Override
	protected String parseUnfiltered(String str) throws CommandParseException {
		return str;
	}
	
	public StringInput matches(String regex, String failMsg) {
		addFilter(str -> str.matches(regex) ? null : failMsg);
		return this;
	}
	public StringInput matches(String regex) {
		return matches(regex, "Failed to match " + regex);
	}
	
	public StringInput minLength(int min, String failMsg) {
		addFilter(str -> str.length() < min ? failMsg : null);
		return this;
	}
	public StringInput minLength(int min) {
		return minLength(min, "Must be at least " + min + " characters long");
	}
	
	public StringInput maxLength(int max, String failMsg) {
		addFilter(str -> str.length() <= max ? null : failMsg);
		return this;
	}
	public StringInput maxLength(int max) {
		return maxLength(max, "Must be at most " + max + " characters long");
	}
	
}
