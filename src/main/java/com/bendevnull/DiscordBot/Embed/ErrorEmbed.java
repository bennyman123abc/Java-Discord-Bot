package com.bendevnull.DiscordBot.Embed;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class ErrorEmbed implements EmbedTemplate{

    private String message;
    
    public ErrorEmbed(String message) {
        this.message = message;
    }

    public MessageEmbed build() {
        return new EmbedBuilder()
        .setColor(Color.RED)
        .setTitle("Bot Runtime Error")
        .setDescription(message)
        .build();
    }
    
}