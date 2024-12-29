package com.luneruniverse.simplecli.inputs;

import java.util.Arrays;

import com.luneruniverse.simplecli.CommandParseException;

public class EnumInput<T extends Enum<T>> extends ArgumentOrFlagImpl<T> {
	
	private final Class<T> clazz;
	
	public EnumInput(Class<T> clazz) {
		if (clazz.getEnumConstants().length == 0)
			throw new IllegalArgumentException("Enum must have at least one option!");
		
		this.clazz = clazz;
	}
	
	@Override
	protected T parseUnfiltered(String str) throws CommandParseException {
		try {
			return Enum.valueOf(clazz, str);
		} catch (IllegalArgumentException e) {
			throw new CommandParseException("'" + str + "' is not one of: " +
					Arrays.stream(clazz.getEnumConstants()).map(Object::toString).reduce((a, b) -> a + ", " + b).get());
		}
	}
	
}
