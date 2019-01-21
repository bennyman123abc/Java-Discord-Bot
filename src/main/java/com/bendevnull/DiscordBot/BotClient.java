package com.bendevnull.DiscordBot;

import javax.security.auth.login.LoginException;

import com.bendevnull.DiscordBot.Listeners.MessageListener;
import com.bendevnull.DiscordBot.Listeners.ReadyListener;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class BotClient {
	
	public BotClient client;
	private JDA jda;
	
	public BotClient(String token) throws LoginException, InterruptedException {
		this.jda = new JDABuilder("bot")
				.setToken(token)
				.addEventListener(new ReadyListener())
				.addEventListener(new MessageListener())
				.build();
		
		this.jda.awaitReady();
	}
	
	public JDA getJDA() {
		return this.jda;
	}

}
