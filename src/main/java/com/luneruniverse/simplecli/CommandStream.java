package com.luneruniverse.simplecli;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class CommandStream {
	
	private final char[] cmd;
	private int cmdIndex;
	
	public CommandStream(String cmd) {
		// cmd.trim() with Character.isWhitespace
		int start = 0;
		int end = cmd.length();
		while (start < end && Character.isWhitespace(cmd.charAt(start)))
			start++;
		while (start < end && Character.isWhitespace(cmd.charAt(end - 1)))
			end--;
		this.cmd = new char[end - start];
		cmd.getChars(start, end, this.cmd, 0);
	}
	
	private char peekChar() {
		return cmd[cmdIndex];
	}
	
	private char readChar() {
		return cmd[cmdIndex++];
	}
	
	private boolean hasNextChar() {
		return cmdIndex < cmd.length;
	}
	
	private String readString(Character stopChar) throws CommandSyntaxException {
		StringBuilder output = new StringBuilder();
		boolean quoted = false;
		boolean escaped = false;
		
		while (hasNextChar()) {
			char c = readChar();
			if (escaped) {
				output.append(c);
				escaped = false;
			} else if (c == '\\') {
				escaped = true;
			} else if (c == '"') {
				if (quoted) {
					if (hasNextChar()) {
						c = peekChar();
						if (stopChar != null && c == stopChar || Character.isWhitespace(c))
							return output.toString();
						throw new CommandSyntaxException("Unexpected '" + c + "'");
					} else {
						return output.toString();
					}
				} else if (output.length() == 0) {
					quoted = true;
				} else {
					throw new CommandSyntaxException("Unexpected unescaped '\"'");
				}
			} else if (!quoted && Character.isWhitespace(c)) {
				if (output.length() != 0)
					return output.toString();
			} else if (stopChar != null && c == stopChar) {
				cmdIndex--;
				break;
			} else {
				output.append(c);
			}
		}
		
		if (quoted)
			throw new CommandSyntaxException("Unclosed '\"'");
		if (escaped)
			throw new CommandSyntaxException("Incomplete '\\'");
		
		return output.toString();
	}
	
	public Optional<String> readArg() throws CommandSyntaxException {
		if (!hasNext())
			return Optional.empty();
		return Optional.of(readString(null));
	}
	
	public Optional<Map.Entry<String, Optional<String>>> readFlag() throws CommandSyntaxException {
		if (!hasNext())
			return Optional.empty();
		
		String name = readString('=');
		
		while (hasNextChar() && Character.isWhitespace(peekChar()))
			readChar();
		if (!hasNextChar() || peekChar() != '=')
			return Optional.of(new AbstractMap.SimpleImmutableEntry<>(name, Optional.empty()));
		readChar();
		
		return Optional.of(new AbstractMap.SimpleImmutableEntry<>(name, Optional.of(readString(null))));
	}
	
	public boolean hasNext() {
		// Works as whitespace characters are already removed from the end of cmd
		return hasNextChar();
	}
	
}
