package com.bendevnull.DiscordBot.Listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {
	
	private Logger logger;
	
	public ReadyListener() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public void onReady(ReadyEvent event) {
		logger.info(event.getJDA().getSelfUser().getId());
	}

}
