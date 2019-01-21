package com.bendevnull.DiscordBot.Config;

import org.json.simple.JSONObject;

public class Config extends Object {
	
	public String token;
	
	public Config(JSONObject json) {
		this.token = (String) json.get("token");
		// TODO finish filling in config fields as development continues
	}

}
