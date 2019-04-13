package com.bendevnull.DiscordBot;

import javax.security.auth.login.LoginException;

import com.bendevnull.DiscordBot.Commands.CommandHandler;
import com.bendevnull.DiscordBot.Commands.Debug.TestCommand;
import com.bendevnull.DiscordBot.Commands.General.HelpCommand;
import com.bendevnull.DiscordBot.Commands.General.PrefixCommand;
import com.bendevnull.DiscordBot.Config.Config;
import com.bendevnull.DiscordBot.Config.ConfigHandler;
import com.bendevnull.DiscordBot.Database.DatabaseHandler;
import com.bendevnull.DiscordBot.Database.GuildDAO;
import com.bendevnull.DiscordBot.Database.LoggerDAO;
import com.bendevnull.DiscordBot.Response.IncorrectUsageResponse;
import com.bendevnull.DiscordBot.Response.NoPermissionResponse;

import net.dv8tion.jda.core.JDA;

public class Program {
	
	private static BotClient client;
	private static Config config;

	private static ConfigHandler configHandler;
	private static CommandHandler commandHandler;

	private static DatabaseHandler guildDatabase;
	private static DatabaseHandler messageDatabase;

	private static GuildDAO guildDAO;
	private static LoggerDAO loggerDAO;
	
	private static String configFilename = "config.json";
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		configHandler = new ConfigHandler(client, configFilename);
		config = configHandler.getConfig();

		guildDatabase = new DatabaseHandler("guilds.db");
		guildDatabase.connect();

		messageDatabase = new DatabaseHandler("messages.db");
		messageDatabase.connect();

		guildDAO = new GuildDAO(guildDatabase);
		loggerDAO = new LoggerDAO(messageDatabase);

		commandHandler = new CommandHandler()
			.addCommand(new TestCommand())
			.addCommand(new PrefixCommand())
			.addCommand(new HelpCommand())
			.addCommand(new NoPermissionResponse())
			.addCommand(new IncorrectUsageResponse())
			.addCategory("General", "The general purpose commands mostly available to everyone.")
			.addCategory("Admin", "Commands used to change bot settings for guilds or for other purposes.")
			.addCategory("Fun", "Commands available for entertainment purposes.")
			.addCategory("Debug", "Commands used for debugging purposes. There is no guarantee these will remain in the bot.")
			.addCategory("Music", "Commands that deal with music, or the manipulation of sound.");

		commandHandler.runInit()
			.runPostInit();

		client = new BotClient(config.token);
	}

	public static void close() {
		guildDatabase.finish();
	}

	public static Config getConfig() {
		return config;
	}

	public static DatabaseHandler getGuildDatabase() {
		return guildDatabase;
	}

	public static CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public static GuildDAO getGuildDAO() {
		return guildDAO;
	}

	public static LoggerDAO getLoggerDAO() {
		return loggerDAO;
	}

	public static BotClient getBotClient() {
		return client;
	}

	public static JDA getJDA() {
		return getBotClient().getJDA();
	}

}
