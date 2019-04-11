package com.bendevnull.DiscordBot.Commands.General;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Commands.Category;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Embed.EmbedField;
import com.bendevnull.DiscordBot.Embed.ErrorEmbed;
import com.bendevnull.DiscordBot.Embed.ResponseEmbed;
import com.bendevnull.DiscordBot.Response.IncorrectUsageResponse;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class HelpCommand extends Command {

    public static final int itemsPerPage = 4;
    // private static final String incorrectUsageResponse = "resp-iur"; 

    private List<List<Command>> pages;
    private List<List<Category>> categoryListPages;
    private Map<String, List<Command>> categoryPages;

    public HelpCommand() {
        super("help",
            "Responds with a page of commands and their usage",
            "$cmd page <PageNumber> | $cmd <PageNumber> | $cmd <CommandName> | $cmd",
            null);
    }

    public void run(Message message, String[] args) {
        if (args.length == 0) {
            message.getChannel().sendMessage(this.renderPage(1, message.getGuild())).queue();
        } else if (args[0].equalsIgnoreCase("page")) {
            if (args.length < 2) {
                IncorrectUsageResponse.fire(message, this);
            } else {
                int n;
                try {
                    n = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    message.getChannel().sendMessage(
                        // String.format("%s is not a valid number.", args[1])
                        new ErrorEmbed(e.toString()).build()
                    ).queue();
                    return;
                }

                if (n > pages.size() || n < 1) {
                    message.getChannel().sendMessage(this.renderPage(1, message.getGuild())).queue();
                } else {
                    message.getChannel().sendMessage(this.renderPage(n, message.getGuild())).queue();
                }
            }
        } else if (args[0].equalsIgnoreCase("category")) {
            
        } else {
            if (Program.getCommandHandler().hasCommand(args[0])) {
                Command cmd = Program.getCommandHandler().getCommand(args[0]);
                message.getChannel().sendMessage(this.renderCommandPage(cmd, message.getGuild())).queue();
            } else {
                try {
                    int n = Integer.parseInt(args[0]);

                    if (n > pages.size() || n < 1) {
                        message.getChannel().sendMessage(this.renderPage(1, message.getGuild())).queue();
                    } else {
                        message.getChannel().sendMessage(this.renderPage(n, message.getGuild())).queue();
                    }

                } catch(NumberFormatException e) {
                    message.getChannel().sendMessage(this.renderPage(1, message.getGuild())).queue();
                }
            }
        }
    }

    @Override
    public void postInit() {
        populatePages();
        populateCategoryPages();
    }

    // private void populatePages() {
    //     this.pages = new ArrayList<>();
    //     List<Category> categoryList = Program.getCommandHandler().getCategoryList();
    //     for (Category category : categoryList) {
    //         int c = 0;
    //         List<Command> page = new ArrayList<>();
    //         List<Command> commandList = category.getCommands();
    //         for (Command cmd : commandList) {
    //             if (c == commandsPerPage) {
    //                 this.pages.add(page);
    //                 c = 0;
    //                 page = new ArrayList<>();
    //             }
    
    //             if(cmd.getName().startsWith("resp-")) {
    //                 continue;
    //             }
    
    //             page.add(cmd);
    //             c += 1;
    //         }
    //         this.pages.add(page);
    //     }
    // }

    private void populatePages() {
        this.pages = new ArrayList<>();
        List<Command> commandList = Program.getCommandHandler().getCommandList();
        int c = 0;
        List<Command> page = new ArrayList<>();
        for (Command cmd : commandList) {
            if (c == itemsPerPage) {
                this.pages.add(page);
                c = 0;
                page = new ArrayList<>();
            }

            if(cmd.getName().startsWith("resp-")) {
                continue;
            }

            page.add(cmd);
            c += 1;
        }
        this.pages.add(page);
    } 

    private void populateCategoryPages() {
        
    }

    private MessageEmbed renderPage(int number, Guild guild) {
        try {
            List<EmbedField> fields = new ArrayList<>();
            for (Command cmd : pages.get(number - 1)) {
                String title = cmd.getName();
                String body = cmd.getDescription();
                fields.add(new EmbedField(title, body));
            }
            return new ResponseEmbed("Command Help",
                null,
                String.format("Page %d/%d | Use %shelp <CommandName> to get a help page on that command.",
                                number,
                                pages.size(),
                                Program.getGuildDAO().getPrefix(guild)),
                fields.toArray(new EmbedField[0])).build();
        } catch(IndexOutOfBoundsException e) {
            return new ResponseEmbed("Command Help",
                "This page does not exist. Please try another page.",
                String.format("%d Page(s) | Use %shelp to get a list of commands.",
                    pages.size(),
                    Program.getGuildDAO().getPrefix(guild)),
                null).build();
        }
    }

    private MessageEmbed renderCommandPage(Command cmd, Guild guild) {
        return new ResponseEmbed(String.format("%s%s", Program.getGuildDAO().getPrefix(guild), cmd.getName()),
            null,
            new EmbedField[] {
                new EmbedField("Description", cmd.getDescription()),
                new EmbedField("Usage", renderUsage(cmd, guild))
            }).build();
    }

    public static String renderUsage(Command cmd, Guild guild) {
        String prefix = Program.getGuildDAO().getPrefix(guild);
        String name = cmd.getName();
        return cmd.getUsage()
            .replace("$cmd", String.format("%s%s",
                prefix,
                name))
            .replace(" | ", "\n");
    }
} 