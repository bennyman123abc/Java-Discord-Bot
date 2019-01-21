package com.bendevnull.DiscordBot.Listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bendevnull.DiscordBot.Commands.CommandRunner;
import com.bendevnull.DiscordBot.Commands.TestCommand;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	
	private Logger logger;
	private CommandRunner runner;
	
	public MessageListener() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.runner = new CommandRunner();
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//		logger.info(event.getMessage().getContentDisplay());
		this.handleMessage(event.getMessage());
	}
	
	public void handleMessage(Message message) {
		if (message.getContentRaw().equalsIgnoreCase("*test")) {
			logger.info("Got test command from server!");
			runner.runCommand(new TestCommand(), message, null);
		}
	}

}
