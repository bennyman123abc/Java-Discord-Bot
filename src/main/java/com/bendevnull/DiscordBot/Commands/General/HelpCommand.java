package com.bendevnull.DiscordBot.Commands.General;

import java.util.List;

import com.bendevnull.DiscordBot.Commands.Command;

import net.dv8tion.jda.core.entities.Message;

public class HelpCommand extends Command {

    public static final int itemsPerPage = 4;

    private List<List<Command>> pages;

    public HelpCommand() {
        super("help",
            "Responds with a page of commands and their usage",
            "$cmd page <PageNumber> | $cmd <PageNumber> | $cmd <CommandName> | $cmd",
            null);
    }

    public void run(Message message, String[] args) {
        
    }

} 