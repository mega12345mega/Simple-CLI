package com.luneruniverse.simplecli.commands;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.luneruniverse.simplecli.CommandParseException;
import com.luneruniverse.simplecli.CommandStream;
import com.luneruniverse.simplecli.CommandSyntaxException;
import com.luneruniverse.simplecli.inputs.ParsedInputs;
import com.luneruniverse.simplecli.inputs.arguments.Argument;
import com.luneruniverse.simplecli.inputs.flags.Flag;

public class SingleCommand implements Command {
	
	private static class NamedFlag {
		private final String fullName;
		private final Flag<?> flag;
		public NamedFlag(String fullName, Flag<?> flag) {
			this.fullName = fullName;
			this.flag = flag;
		}
	}
	
	private final String name;
	private final Consumer<ParsedInputs> executor;
	private final Map<String, Argument<?>> arguments;
	private final Map<String, NamedFlag> flags;
	
	public SingleCommand(String name, Consumer<ParsedInputs> executor) {
		this.name = name;
		this.executor = executor;
		this.arguments = new LinkedHashMap<>();
		this.flags = new HashMap<>();
	}
	public SingleCommand(String name, Runnable executor) {
		this(name, inputs -> executor.run());
	}
	
	public SingleCommand addArgument(String name, Argument<?> argument) {
		arguments.put(name, argument);
		return this;
	}
	
	public SingleCommand addFlag(String fullName, String shortName, Flag<?> flag) {
		NamedFlag namedFlag = new NamedFlag(fullName, flag);
		flags.put("--" + fullName, namedFlag);
		if (shortName != null)
			flags.put("-" + shortName, namedFlag);
		return this;
	}
	public SingleCommand addFlag(String fullName, String shortName) {
		return addFlag(fullName, shortName, null);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void parse(CommandStream stream) throws CommandSyntaxException, CommandParseException {
		Map<String, Object> parsedArgs = new HashMap<>();
		for (Map.Entry<String, Argument<?>> arg : arguments.entrySet()) {
			try {
				parsedArgs.put(arg.getKey(), arg.getValue().parse(stream));
			} catch (CommandParseException e) {
				throw new CommandParseException("While parsing argument '" + arg.getKey() + "': " + e.getMessage());
			}
		}
		
		Map<String, Object> parsedFlags = new HashMap<>();
		while (stream.hasNext()) {
			Map.Entry<String, Optional<String>> flagParts = stream.readFlag().get();
			String flagName = flagParts.getKey();
			String flagValue = flagParts.getValue().orElse(null);
			
			NamedFlag flag = flags.get(flagName);
			if (flag == null)
				throw new CommandParseException("Unexpected '" + flagName + "'");
			
			if (flag.flag == null) {
				if (flagValue != null)
					throw new CommandParseException("Unexpected value '" + flagValue + "' for flag '" + flagName + "'");
			} else {
				if (flagValue == null)
					throw new CommandParseException("Expected value for flag '" + flagName + "'");
			}
			
			try {
				parsedFlags.put(flag.fullName, flagValue == null ? true : flag.flag.parse(flagValue));
			} catch (CommandParseException e) {
				throw new CommandParseException("While parsing flag '" + flagName + "': " + e.getMessage());
			}
		}
		
		executor.accept(new ParsedInputs(parsedArgs, parsedFlags));
	}
	
}
