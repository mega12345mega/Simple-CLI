package com.luneruniverse.simplecli.commands;

import java.util.HashMap;
import java.util.Map;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.CommandStream;
import com.luneruniverse.simplecli.CommandSyntaxException;

public class GroupCommand implements Command {
	
	private final String name;
	private final Map<String, Command> children;
	
	public GroupCommand(String name) {
		this.name = name;
		this.children = new HashMap<>();
	}
	
	public GroupCommand addCommand(Command child) {
		children.put(child.getName(), child);
		return this;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void parse(CommandStream stream) throws CommandSyntaxException, CommandParseException {
		String name = stream.readArg().orElseThrow(
				() -> new CommandParseException("Incomplete command - expected: " + String.join(", ", children.keySet())));
		Command command = children.get(name);
		if (command == null)
			throw new CommandParseException("Unknown command '" + name + "' - expected: " + String.join(", ", children.keySet()));
		command.parse(stream);
	}
	
}
