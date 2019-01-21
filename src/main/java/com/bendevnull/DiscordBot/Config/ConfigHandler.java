package com.bendevnull.DiscordBot.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bendevnull.DiscordBot.BotClient;

public class ConfigHandler {
	
	private BotClient client;
	private Config config;
	private String filename;
	
	public ConfigHandler(BotClient client, String filename) {
		this.client = client;
		this.filename = filename;
		this.config = this.parseConfig();
	}
	
	@SuppressWarnings("resource")
	private Config parseConfig() {
		JSONParser parser = new JSONParser();
		Scanner scanner;
		File file;
		String str;
		JSONObject obj;
		try {
			file = new File(this.filename);
			scanner = new Scanner(file).useDelimiter("\\Z");
			str = scanner.next();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try {
			obj = (JSONObject) parser.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return new Config(obj);
		
	}
	
	public Config getConfig() {
		return this.config;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public BotClient getClient() {
		return this.client;
	}

}
