package com.bendevnull.DiscordBot.Commands.Categories;

import java.util.List;
import java.util.ArrayList;

import com.bendevnull.DiscordBot.Commands.Command;

public class Category {

	private String name = "";
	private List<Command> commands = new ArrayList<Command>();
	
	public Category(String name) {
		this.name = name;
	}
	
	public void addCommand(Command c) {
		if (!this.hasCommand(c)) {
			this.commands.add(c);
		}
	}
	
	public boolean hasCommand(Command c) {
		return this.commands.contains(c);
	}
	
	public boolean hasCommand(String s) {
		for (Command c : this.commands) {
			if (c.getName() == s) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Command> getCommands() {
		return this.commands;
	}
	
}
