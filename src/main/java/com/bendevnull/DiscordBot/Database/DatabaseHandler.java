package com.bendevnull.DiscordBot.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

public class DatabaseHandler {
	
	private String databasePath;
	private Logger logger;
	private Connection conn;
	
	public DatabaseHandler(String path) {
		this.logger = BotLoggerFactory.createLogger(this);
		this.databasePath = path;
	}
	
	public void connect() {
		try {
			this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", this.databasePath));
			this.logger.info(String.format("Opened database at %s.", this.databasePath));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}

}
