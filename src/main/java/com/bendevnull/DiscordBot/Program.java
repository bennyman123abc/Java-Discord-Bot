package com.bendevnull.DiscordBot;

import javax.security.auth.login.LoginException;

import com.bendevnull.DiscordBot.Commands.CommandHandler;
import com.bendevnull.DiscordBot.Commands.TestCommand;
import com.bendevnull.DiscordBot.Config.Config;
import com.bendevnull.DiscordBot.Config.ConfigHandler;
import com.bendevnull.DiscordBot.Database.DatabaseHandler;

public class Program {
	
	private static BotClient client;
	private static Config config;
	private static ConfigHandler configHandler;
	private static CommandHandler commandHandler;
	
	private static String configFilename = "config.json";
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		configHandler = new ConfigHandler(client, configFilename);
		config = configHandler.getConfig();
		DatabaseHandler db = new DatabaseHandler("test.db");
		db.connect();
		commandHandler = new CommandHandler()
				.addCommand(new TestCommand());
		client = new BotClient(config.token);
	}

	public static Config getConfig() {
		return config;
	}

	public static CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
