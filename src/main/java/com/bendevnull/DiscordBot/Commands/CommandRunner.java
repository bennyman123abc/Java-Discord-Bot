package com.bendevnull.DiscordBot.Commands;

import org.slf4j.Logger;

import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import net.dv8tion.jda.core.entities.Message;

public class CommandRunner {
	
	private Logger logger;
	
	public CommandRunner() {
		logger = BotLoggerFactory.createLogger(this);
	}
	
	public void runCommand(final Command c, final Message m, final String[] args) {
		Thread t = new Thread(new Runnable() {
			public void run() {
//				logger.info(String.format("Running %s", c.getName()));
				c.run(m, args);
			}
		});
		t.start();
		logger.info("First");
	}

}
