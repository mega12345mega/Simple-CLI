package com.luneruniverse.simplecli.inputs.flags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.luneruniverse.simplecli.CommandParseException;

public abstract class FlagImpl<T> implements Flag<T> {
	
	private final List<Function<T, String>> filters;
	
	public FlagImpl() {
		filters = new ArrayList<>();
	}
	
	@Override
	public FlagImpl<T> addFilter(Function<T, String> filter) {
		filters.add(filter);
		return this;
	}
	
	@Override
	public T parse(String str) throws CommandParseException {
		T value = parseUnfiltered(str);
		for (Function<T, String> filter : filters) {
			String error = filter.apply(value);
			if (error != null)
				throw new CommandParseException(error);
		}
		return value;
	}
	
	protected abstract T parseUnfiltered(String str) throws CommandParseException;
	
}
