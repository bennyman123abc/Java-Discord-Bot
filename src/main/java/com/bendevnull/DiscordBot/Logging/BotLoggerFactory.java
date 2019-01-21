package com.bendevnull.DiscordBot.Logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotLoggerFactory {
	
	public static Logger createLogger(Object obj) {
		return LoggerFactory.getLogger(obj.getClass());
	}

}
