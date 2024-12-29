package com.luneruniverse.simplecli.inputs.arguments;

import java.util.function.Function;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.CommandStream;
import com.luneruniverse.simplecli.inputs.Input;

public interface Argument<T> extends Input<T> {
	@Override
	public Argument<T> addFilter(Function<T, String> filter);
	public T parse(CommandStream stream) throws CommandParseException;
}
