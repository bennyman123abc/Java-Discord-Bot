package com.bendevnull.DiscordBot.Listeners;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Database.GuildDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {
	
	private Logger logger;
	
	public ReadyListener() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public void onReady(ReadyEvent event) {
		// Guild database stuff
		GuildDAO g = Program.getGuildDAO();
		for (Guild guild : event.getJDA().getGuilds()) {
			g.create(guild);
		}
		// finish loading stuff
		logger.info("Logged in as ID " + event.getJDA().getSelfUser().getId());
	}
}
