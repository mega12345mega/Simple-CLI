package com.luneruniverse.simplecli.inputs;

import java.util.Map;

public class ParsedInputs {
	
	private final Map<String, Object> args;
	private final Map<String, Object> flags;
	
	public ParsedInputs(Map<String, Object> args, Map<String, Object> flags) {
		this.args = args;
		this.flags = flags;
	}
	
	public <T> T getArgument(String name, Class<T> clazz) {
		return clazz.cast(args.get(name));
	}
	
	public <T> T getFlag(String name, Class<T> clazz) {
		return clazz.cast(flags.get(name));
	}
	
	public boolean hasFlag(String name) {
		return flags.containsKey(name);
	}
	
}