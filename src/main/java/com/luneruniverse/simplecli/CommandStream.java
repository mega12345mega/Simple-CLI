package com.luneruniverse.simplecli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandStream {
	
	public static CommandStream parse(String cmd) throws CommandSyntaxException {
		List<String> args = new ArrayList<>();
		
		StringBuilder arg = new StringBuilder();
		boolean quoted = false;
		boolean escaped = false;
		
		for (char c : cmd.toCharArray()) {
			if (escaped) {
				arg.append(c);
				escaped = false;
			} else if (c == '\\') {
				escaped = true;
			} else if (c == '"') {
				if (quoted) {
					args.add(arg.toString());
					arg = new StringBuilder();
					quoted = false;
				} else if (arg.length() == 0) {
					quoted = true;
				} else {
					throw new CommandSyntaxException("Unexpected unescaped '\"'");
				}
			} else if (!quoted && Character.isWhitespace(c)) {
				if (arg.length() != 0) {
					args.add(arg.toString());
					arg = new StringBuilder();
				}
			} else {
				arg.append(c);
			}
		}
		
		if (quoted)
			throw new CommandSyntaxException("Unclosed '\"'");
		if (escaped)
			throw new CommandSyntaxException("Unexpected '\\'");
		
		if (arg.length() != 0)
			args.add(arg.toString());
		
		return new CommandStream(args.toArray(new String[args.size()]));
	}
	
	private final String[] args;
	private int index;
	
	public CommandStream(String[] args) {
		this.args = args;
	}
	
	public Optional<String> read() {
		if (index == args.length)
			return Optional.empty();
		return Optional.of(args[index++]);
	}
	
	public boolean hasNext() {
		return index != args.length;
	}
	
}
