package com.bendevnull.DiscordBot.Listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Commands.CommandHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	
	private Logger logger;
	
	public MessageListener() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//		logger.info(event.getMessage().getContentDisplay());
		this.handleMessage(event.getMessage());
	}
	
	public void handleMessage(Message message) {
		// TODO implement logging by guild
		// TODO implement command permissions
		if (message.getContentRaw().startsWith(Program.getConfig().prefix)) {
			if (message.getAuthor().isBot()) {
				return;
			}		
			CommandHandler handler = Program.getCommandHandler();
			String[] split = message.getContentRaw().split(" ");
			String cmd = split[0].replace(Program.getConfig().prefix, "");
			String[] args = stripCommand(split);

			if (handler.hasCommand(cmd)) {
				handler.runCommand(cmd, message, args);
			}
		}
	}

	private String[] stripCommand(String[] arr) {
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		list.remove(arr[0]);
		return list.toArray(new String[0]);
	}
}
