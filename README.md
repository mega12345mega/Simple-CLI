# Simple CLI

[![Release](https://jitpack.io/v/mega12345mega/Simple-CLI.svg)](https://jitpack.io/#mega12345mega/Simple-CLI)

This is a very simple Java library for parsing commands.

Commands are split into tokens, where each token is separated by whitespace (or equals (=) for flags). Use double quotes (") around an entire token to include whitespace in that token.

Commands are divided into three sections:

<pre>
file move from.txt to.txt --force
^^^^^^^^^ ^^^^^^^^^^^^^^^ ^^^^^^^
Command   Arguments       Flags
</pre>

## Section 1: Command

Build command tree structures with `GroupCommand`s, where the final command name (leaf) is a `SingleCommand`. Add arguments and flags to the `SingleCommand`.

## Section 2: Arguments

Arguments aren't optional (use flags to make something optional). Arguments can take multiple tokens. Arguments implement `Argument`, but most arguments should extend `ArgumentOrFlagImpl` (see below).

## Section 3: Flags

All flags have a long name (like \-\-force) and can optionally have a short name (like \-f). Flags may require a value, where the flag name and value is separated by an equals (like \-\-maxdepth=10). Note that the flag name and value are both individually one token. There can be whitespace before and after the equals (=).

## Inputs (Arguments & Flags)

Most types of inputs only need one token, so they can work as either an argument or flag. Use `ArgumentOrFlagImpl` to implement both `Argument` and `Flag` simultaneously. There are several built in inputs:

* StringInput
* StringKeyInput
* BooleanInput
* IntegerInput
* LongInput
* DoubleInput

Arguments and flags both support filtering: `new IntegerInput().addFilter(num -> num % 2 == 0 ? null : "The number must be even!")`, and most have some built in filters: `new IntegerInput().min(0)`