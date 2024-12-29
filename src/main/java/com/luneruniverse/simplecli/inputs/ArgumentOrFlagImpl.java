package com.luneruniverse.simplecli.inputs;

import java.util.function.Function;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.CommandStream;
import com.luneruniverse.simplecli.inputs.arguments.Argument;
import com.luneruniverse.simplecli.inputs.flags.FlagImpl;

public abstract class ArgumentOrFlagImpl<T> extends FlagImpl<T> implements Argument<T> {
	
	@Override
	public ArgumentOrFlagImpl<T> addFilter(Function<T, String> filter) {
		super.addFilter(filter);
		return this;
	}
	
	@Override
	public T parse(CommandStream stream) throws CommandParseException {
		return parse(stream.read().orElseThrow(() -> new CommandParseException("Missing input")));
	}
	
}
