package com.luneruniverse.simplecli.commands;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.CommandStream;
import com.luneruniverse.simplecli.CommandSyntaxException;

public interface Command {
	public String getName();
	public void parse(CommandStream stream) throws CommandSyntaxException, CommandParseException;
}
