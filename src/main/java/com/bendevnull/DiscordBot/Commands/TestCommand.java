package com.bendevnull.DiscordBot.Commands;

import net.dv8tion.jda.core.entities.Message;

public class TestCommand extends Command {

	public TestCommand() {
		super("test", "A test command", null);
	}

	@Override
	public void run(Message message, String[] args) {
		message.getChannel().sendMessage("Hello, world!").queue();
	}
}
