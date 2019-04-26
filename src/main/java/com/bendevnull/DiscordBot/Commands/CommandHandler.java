package com.bendevnull.DiscordBot.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

public class CommandHandler {

    private Map<String, Command> commands;
    private Map<String, Category> categories;
    private Logger logger;
    
    public CommandHandler() {
        this.commands = new HashMap<>();
        this.categories = new HashMap<>();
        this.logger = BotLoggerFactory.createLogger(this);
    }

    public CommandHandler addCommand(Command c) {
        if (!this.commands.containsValue(c) || !this.commands.containsKey(c.getName())) {
            this.commands.put(c.getName(), c);
        }
        return this;
    }

    public CommandHandler addCategory(String c, String d) {
        if (!this.categories.containsKey(c)) {
            this.categories.put(c, new Category(c, d));
        }
        return this;
    }

    public CommandHandler runInit() {
        for (Map.Entry<String, Command> entry : this.commands.entrySet()) {
            entry.getValue().init();
        }
        return this;
    }

    public CommandHandler runPostInit() {
        for (Map.Entry<String, Command> entry : this.commands.entrySet()) {
            entry.getValue().postInit();
        }
        return this;
    }

    public Command getCommand(String s) {
        return this.commands.get(s);
    }

    public Category getCategory(String s) {
        return this.categories.get(s);
    }

    public boolean hasCommand(String s) {
        return this.commands.containsKey(s);
    }

    public boolean hasCommand(Command c) {
        return this.commands.containsValue(c);
    }

    public List<Command> getCommandList() {
        List<Command> c = new ArrayList<>();
        for (Map.Entry<String, Command> entry : this.commands.entrySet()) {
            c.add(entry.getValue());
        }
        return c;
    }

    public List<Category> getCategoryList() {
        List<Category> c = new ArrayList<>();
        for (Map.Entry<String, Category> entry : this.categories.entrySet()) {
            c.add(entry.getValue());
        }
        return c;
    }

    public Map<String, Command> getCommandMap() {
        return this.commands;
    }

    public Map<String, Category> getCategoryMap() {
        return this.categories;
    }

    public void runCommand(final Command c, final Message m, final String[] args) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				c.run(m, args);
			}
		});
		t.start();
    }
    
    // public void runCommand(final String s, final Message m, final String[] args) {
    //     final Command c = this.getCommand(s);
    //     if (c == null) {
    //         this.logger.error(String.format("Command %s not found.", s));
    //         return;
    //     } else {
    //         Thread t = new Thread(new Runnable() {
    //             public void run() {
    //                 c.run(m, args);
    //             }
    //         });
    //         t.start();
    //     }
    // }

    public static boolean hasPermissions(Member member, Permission[] permissions) {
        for (Permission p : permissions) {
            if (!member.hasPermission(p)) {
                return false;
            }
        }
        return true;
    }

}