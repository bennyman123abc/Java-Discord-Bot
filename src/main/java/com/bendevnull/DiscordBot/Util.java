package com.bendevnull.DiscordBot;

import com.bendevnull.DiscordBot.Commands.Command;

import net.dv8tion.jda.core.entities.Guild;

public class Util {

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