package com.bendevnull.DiscordBot.Commands.Fun;

import com.bendevnull.DiscordBot.Commands.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class WordRaceCommand extends Command {

    public WordRaceCommand() {
        super("wordrace",
            "Manages the word race game",
            "$cmd",
            Permission.MANAGE_SERVER);
    }

    public void run(Message message, String[] args) {
        
    }
}