package com.bendevnull.DiscordBot.Embed;

public class CommandResponseEmbed extends ResponseEmbed {

    public CommandResponseEmbed(String body, EmbedField[] fields) {
        super("Command Response", body, fields);
    }
    
}