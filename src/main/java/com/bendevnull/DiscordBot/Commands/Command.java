package com.bendevnull.DiscordBot.Commands;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public abstract class Command {

	private Logger logger;
	
	private String name;
	private String description;
	private String usage;
	private Category category;
	private Permission perms;
	private boolean enabled;

	private String categoryString;
	
	public Command(String name, String description, String usage, Permission perms) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.perms = perms;
	}

	public Command(String name, String description, String usage, Permission perms, String cat) {
		this(name, description, usage, perms);
		this.categoryString = cat;
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

	public Permission getPermissions() {
		return this.perms;
	}
	
	public void setCategory(Category c) {
		// cleanup old category
		this.category.removeCommand(this);
		// set new category
		this.category = c;
		this.category.addCommand(this);
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

	public void init() {
		// init default logger for errors in this function
		this.logger = BotLoggerFactory.createLogger(this);
		// init category
		if (this.categoryString != null) {
			this.category = Program.getCommandHandler().getCategory(this.categoryString);
			if (this.category == null) {
				this.logger.warn(String.format("%s: The provided category does not exist! Using default \"General\" instead.", this.name));
				this.categoryString = "General";
				this.init(); // rerun the init
			} else {
				this.category.addCommand(this);
			}
		} else {
			this.categoryString = "General";
			this.init();
		}
	}

	public void postInit() {} // available to the command if needed
	
	
}
