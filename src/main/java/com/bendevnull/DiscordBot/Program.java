package com.bendevnull.DiscordBot;

import javax.security.auth.login.LoginException;

import com.bendevnull.DiscordBot.Config.Config;
import com.bendevnull.DiscordBot.Config.ConfigHandler;
import com.bendevnull.DiscordBot.Database.DatabaseHandler;

public class Program {
	
	private static BotClient client;
	private static Config config;
	private static ConfigHandler configHandler;
	
	private static String configFilename = "config.json";
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		configHandler = new ConfigHandler(client, configFilename);
		config = configHandler.getConfig();
		DatabaseHandler db = new DatabaseHandler("test.db");
		db.connect();
		client = new BotClient(config.token);
	}

}
