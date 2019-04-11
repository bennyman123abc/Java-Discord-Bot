package com.bendevnull.DiscordBot.Listeners;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Database.GuildDAO;
import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildStatusListener extends ListenerAdapter {

    private Logger logger;

    public GuildStatusListener() {
        this.logger = BotLoggerFactory.createLogger(this);
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        GuildDAO g = Program.getGuildDAO();
        g.create(event.getGuild());
        logger.info("Created database entry for guild ID " + event.getGuild().getId());
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        GuildDAO g = Program.getGuildDAO();
        g.remove(event.getGuild());
        logger.info("Removed database entry for guild ID " + event.getGuild().getId());
    }
}