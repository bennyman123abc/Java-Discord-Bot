package com.bendevnull.DiscordBot.Config;

import org.json.simple.JSONObject;

public class Config extends Object {
	
	public String token;
	public String prefix;

	private JSONObject json;
	
	public Config(JSONObject json) {
		this.token = (String) json.get("token");
		this.prefix = (String) json.get("prefix");
		// TODO finish filling in config fields as development continues

		this.json = json;
	}

	public Object get(Object key) {
		return this.json.get(key);
	}

}
