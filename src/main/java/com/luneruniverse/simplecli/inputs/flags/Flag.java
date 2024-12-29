package com.luneruniverse.simplecli.inputs.flags;

import java.util.function.Function;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.inputs.Input;

public interface Flag<T> extends Input<T> {
	@Override
	public Flag<T> addFilter(Function<T, String> filter);
	public T parse(String str) throws CommandParseException;
}
