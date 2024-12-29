package com.luneruniverse.simplecli.inputs;

import java.util.function.Function;

public interface Input<T> {
	public Input<T> addFilter(Function<T, String> filter);
}
