package com.bendevnull.DiscordBot.Commands.General;

import java.util.List;
import java.util.Map;

import com.bendevnull.DiscordBot.Commands.Category;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Commands.CommandHandler;

import net.dv8tion.jda.core.entities.Message;

public class HelpCommand extends Command {

    public static final int itemsPerPage = 4;

    private CommandHandler handler;

    private List<List<Command>> pages;
    private List<Category> categories;
    private Map<String, List<List<Command>>> categoryCommandPages;

    public HelpCommand() {

        super("help",
            "Responds with a page of commands and their usage",
            "$cmd page <PageNumber> | $cmd category <CategoryName> | $cmd <CommandName> | $cmd",
            null);

        this.disable(); // TODO remove this when command is in a usable state

        this.handler.addCommand(new Command("page", null, null, null) {
        
            @Override
            public void run(Message message, String[] args) {
                
            }
        });
    }

    @Override
    public void postInit() {
        // TODO populate pages and cached results here
    }

    public void run(Message message, String[] args) {
        
    }

    private void sendPage() {

    }

} 