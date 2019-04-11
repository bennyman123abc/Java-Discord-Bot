package com.bendevnull.DiscordBot.Listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Commands.CommandHandler;
import com.bendevnull.DiscordBot.Database.GuildDAO;
import com.bendevnull.DiscordBot.Database.LoggerDAO;
import com.bendevnull.DiscordBot.Response.NoPermissionResponse;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	
	// private Logger logger;

	private LoggerDAO l = Program.getLoggerDAO();
	private GuildDAO g = Program.getGuildDAO();
	
	public MessageListener() {
		// this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		g.create(event.getGuild());
		l.logMessage(event.getMessage());
		this.handleMessage(event.getMessage());
	}

	@Override
	public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
		l.editMessage(event.getMessage());
	}

	@Override
	public void onGuildMessageDelete(GuildMessageDeleteEvent event) {
		l.deleteMessage(event.getMessageId());
	}
	
	public void handleMessage(Message message) {
		Guild guild = message.getGuild();
		
		String prefix = g.getPrefix(guild);
		String mention = "<@" + Program.getBotClient().getJDA().getSelfUser().getId() + "> ";

		if (message.getContentRaw().startsWith(prefix)) {
			if (message.getAuthor().isBot()) {
				return;
			}
			CommandHandler handler = Program.getCommandHandler();
			String[] split = message.getContentRaw().split(" ");
			String cmd = split[0].replace(prefix, "");
			String[] args = stripCommand(split);

			handleCommand(cmd, args, message, handler);
		} else if (message.getContentRaw().startsWith(mention)) {
			if (message.getAuthor().isBot()) {
				return;
			}
			CommandHandler handler = Program.getCommandHandler();
			String mentionless = message.getContentRaw().replace(mention, "");
			String[] split = mentionless.split(" ");
			String cmd = split[0].replace(prefix, "").replace(" ", "");
			String[] args = stripCommand(split);

			handleCommand(cmd, args, message, handler);
		}
	}

	private String[] stripCommand(String[] arr) {
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		list.remove(arr[0]);
		return list.toArray(new String[0]);
	}

	private void handleCommand(String cmd, String[] args, Message message, CommandHandler handler) {
		if (cmd.startsWith("resp-")) {
			return;
		}

		if (handler.hasCommand(cmd)) {
			Command c = handler.getCommand(cmd);
			if (message.getMember().hasPermission(c.getPermissions()) || message.getAuthor().getId().equalsIgnoreCase(Program.getConfig().owner)) {
				handler.runCommand(c, message, args);
			} else {
				NoPermissionResponse.fire(message);
			}
		}
	}
}
