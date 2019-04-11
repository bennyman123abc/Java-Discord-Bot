package com.bendevnull.DiscordBot;

import javax.security.auth.login.LoginException;

import com.bendevnull.DiscordBot.Listeners.GuildStatusListener;
import com.bendevnull.DiscordBot.Listeners.MessageListener;
import com.bendevnull.DiscordBot.Listeners.ReadyListener;
import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class BotClient {
	
	public BotClient client;
	private JDA jda;
	private Logger logger;
	
	public BotClient(String token) throws LoginException, InterruptedException {

		logger = BotLoggerFactory.createLogger(this);

		this.jda = new JDABuilder("bot")
				.setToken(token)
				.addEventListener(new ReadyListener())
				.addEventListener(new MessageListener())
				.addEventListener(new GuildStatusListener())
				.build();
		
		this.jda.awaitReady();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.warn("Shutdown signal received. Safely shutting down.");
				Program.close();
			}
		});
	}
	
	public JDA getJDA() {
		return this.jda;
	}

}
