package com.bendevnull.DiscordBot.Commands;

import net.dv8tion.jda.core.entities.Message;

public abstract class Command {
	
	private String name;
	private String description;
	private String usage;
	private String category;
	private boolean enabled;
	
	public Command(String name, String description, String usage) {
		this.name = name;
		this.description = description;
		this.usage = usage;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public String getUsage() {
		return this.usage;
	}

	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String c) {
		this.category = c;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
	public void enable() {
		this.enabled = true;
	}

	public abstract void run(Message message, String[] args);
	
	
}
