package com.bendevnull.DiscordBot.Commands.Debug;

import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Embed.CommandResponseEmbed;

import net.dv8tion.jda.core.entities.Message;

public class TestCommand extends Command {

	public TestCommand() {
		super("test",
			  "A test command",
			  "$cmd",
			  null);
	}

	@Override
	public void run(Message message, String[] args) {
		message.getChannel().sendMessage(new CommandResponseEmbed("Hello, world!",
			null).build()).queue();
	}
}
