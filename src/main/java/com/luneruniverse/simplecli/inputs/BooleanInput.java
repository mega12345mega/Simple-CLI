package com.luneruniverse.simplecli.inputs;

import com.luneruniverse.simplecli.CommandParseException;

public class BooleanInput extends ArgumentOrFlagImpl<Boolean> {
	
	@Override
	protected Boolean parseUnfiltered(String str) throws CommandParseException {
		switch (str.toLowerCase()) {
			case "false":
			case "f":
			case "no":
			case "n":
			case "off":
			case "0":
				return false;
			case "true":
			case "t":
			case "yes":
			case "y":
			case "on":
			case "1":
				return true;
			default:
				throw new CommandParseException("Invalid boolean '" + str + "'");
		}
	}
	
}
