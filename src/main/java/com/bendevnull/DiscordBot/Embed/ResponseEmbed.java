package com.bendevnull.DiscordBot.Embed;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class ResponseEmbed implements EmbedTemplate {

    private String title;
    private String body;
    private String footer;
    private EmbedField[] fields;

    public ResponseEmbed(String title, String body, EmbedField[] fields) {
        if (title != null) {
            this.title = title;
        } else {
            this.title = "Bot Response";
        }
        this.body = body;
        this.fields = fields;
    }

    public ResponseEmbed(String title, String body, String footer, EmbedField[] fields) {
        if (title != null) {
            this.title = title;
        } else {
            this.title = "Bot Response";
        }
        this.body = body;
        this.fields = fields;
        this.footer = footer;
    }

    public MessageEmbed build() {
        Random r = new Random();
        EmbedBuilder e = new EmbedBuilder()
                            .setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)))
                            .setTitle(this.title);

        if (this.body != null) {
            e.setDescription(this.body);
        }

        if (this.fields != null) {
            for (EmbedField f : this.fields) {
                e.addField(f.title, f.body, f.inline);
            }
        }

        if (this.footer != null) {
            e.setFooter(this.footer, null);
        }

        return e.build();
    }
}