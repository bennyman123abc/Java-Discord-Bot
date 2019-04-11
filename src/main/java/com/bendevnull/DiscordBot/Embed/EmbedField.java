package com.bendevnull.DiscordBot.Embed;

public class EmbedField {
    public String title;
    public String body;
    public boolean inline;

    public EmbedField(String title, String body, boolean inline) {
        this.title = title;
        this.body = body;
        this.inline = inline;
    }

    public EmbedField(String title, String body) {
        this.title = title;
        this.body = body;
        this.inline = false;
    }
}