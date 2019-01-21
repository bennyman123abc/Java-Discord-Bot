package com.bendevnull.DiscordBot.Database;

public class GuildDAO {
	
	private String tableName = "guilds";
	private DatabaseHandler handler;
	
	public GuildDAO(DatabaseHandler handler) {
		this.handler = handler;
	}
	
	public void create() {
		
	}
	
	public boolean exists() {
		
		return false;
	}

}
