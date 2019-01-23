package com.bendevnull.DiscordBot.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.core.entities.Message;

public class CommandHandler {

    private Map<String, Command> commands;
    
    public CommandHandler() {
        this.commands = new HashMap<String, Command>();
    }

    public CommandHandler addCommand(Command c) {
        if (!this.commands.containsValue(c) || !this.commands.containsKey(c.getName())) {
            this.commands.put(c.getName(), c);
        }
        return this;
    }

    public Command getCommand(String s) {
        return this.commands.get(s);
    }

    public boolean hasCommand(String s) {
        return this.commands.containsKey(s);
    }

    public boolean hasCommand(Command c) {
        return this.commands.containsValue(c);
    }

    public List<Command> getCommandList() {
        List<Command> c = new ArrayList<Command>();
        for (Map.Entry<String, Command> entry : this.commands.entrySet()) {
            c.add(entry.getValue());
        }
        return c;
    }

    public Map<String, Command> getCommandMap() {
        return this.commands;
    }

    public void runCommand(final Command c, final Message m, final String[] args) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				c.run(m, args);
			}
		});
		t.start();
    }
    
    public void runCommand(String s, final Message m, final String[] args) {
        final Command c = this.getCommand(s);
        if (c == null) {
            return;
        } else {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    c.run(m, args);
                }
            });
            t.start();
        }
    }

}