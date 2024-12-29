package com.luneruniverse.simplecli.inputs;

import java.util.Collection;

import com.luneruniverse.simplecli.CommandParseException;

public class StringOptionsInput extends ArgumentOrFlagImpl<String> {
	
	private final Collection<String> options;
	
	public StringOptionsInput(Collection<String> options) {
		if (options.size() == 0)
			throw new IllegalArgumentException("There must be at least one option!");
		
		this.options = options;
	}
	
	@Override
	protected String parseUnfiltered(String str) throws CommandParseException {
		if (options.contains(str))
			return str;
		throw new CommandParseException("'" + str + "' is not one of: " + String.join(", ", options));
	}
	
}