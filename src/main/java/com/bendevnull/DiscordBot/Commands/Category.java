package com.bendevnull.DiscordBot.Commands;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private List<Command> commands;
    private String name;
    private String description;

    public Category(String name, String description) {
        this.commands = new ArrayList<>();
        this.description = description;
        this.name = name;
    }

    public void addCommand(Command cmd) {
        this.commands.add(cmd);
    }

    public void removeCommand(Command cmd) {
        this.commands.remove(cmd);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}