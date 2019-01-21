package com.bendevnull.DiscordBot.Commands;

import com.bendevnull.DiscordBot.Commands.Categories.Category;

import net.dv8tion.jda.core.entities.Message;

public abstract class Command {
	
	private String name;
	private String description;
	private String usage;
	private Category category;
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

	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category c) {
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
