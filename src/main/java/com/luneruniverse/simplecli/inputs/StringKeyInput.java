package com.luneruniverse.simplecli.inputs;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.luneruniverse.simplecli.CommandParseException;

public class StringKeyInput<T> extends ArgumentOrFlagImpl<T> {
	
	public static <T extends Enum<T>> StringKeyInput<T> forEnum(Class<T> clazz, boolean ignoreCase) {
		return new StringKeyInput<>(Arrays.stream(clazz.getEnumConstants())
				.collect(Collectors.toMap(Enum::name, Function.identity(), (a, b) -> a, LinkedHashMap::new)), ignoreCase);
	}
	
	private final Map<String, T> map;
	private final boolean ignoreCase;
	private final String combinedKeys;
	
	public StringKeyInput(Map<String, T> map, boolean ignoreCase) {
		if (map.isEmpty())
			throw new IllegalArgumentException("There must be at least one key!");
		
		if (ignoreCase) {
			this.map = new LinkedHashMap<>();
			for (Map.Entry<String, T> entry : map.entrySet()) {
				String key = entry.getKey().toLowerCase();
				if (this.map.put(key, entry.getValue()) != null)
					throw new IllegalArgumentException("There are multiple keys that match '" + key + "'");
			}
		} else {
			this.map = new LinkedHashMap<>(map);
		}
		
		this.ignoreCase = ignoreCase;
		
		this.combinedKeys = String.join(", ", this.map.keySet());
	}
	
	@Override
	protected T parseUnfiltered(String str) throws CommandParseException {
		String key = ignoreCase ? str.toLowerCase() : str;
		if (map.containsKey(key))
			return map.get(key);
		throw new CommandParseException("'" + str + "' is not one of: " + combinedKeys);
	}
	
}
