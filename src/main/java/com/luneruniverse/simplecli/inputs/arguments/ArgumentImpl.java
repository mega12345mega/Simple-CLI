package com.luneruniverse.simplecli.inputs.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.CommandStream;

public abstract class ArgumentImpl<T> implements Argument<T> {
	
	private final List<Function<T, String>> filters;
	
	public ArgumentImpl() {
		filters = new ArrayList<>();
	}
	
	@Override
	public ArgumentImpl<T> addFilter(Function<T, String> filter) {
		filters.add(filter);
		return this;
	}
	
	@Override
	public T parse(CommandStream stream) throws CommandParseException {
		T value = parseUnfiltered(stream);
		for (Function<T, String> filter : filters) {
			String error = filter.apply(value);
			if (error != null)
				throw new CommandParseException(error);
		}
		return value;
	}
	
	protected abstract T parseUnfiltered(CommandStream stream) throws CommandParseException;
	
}
