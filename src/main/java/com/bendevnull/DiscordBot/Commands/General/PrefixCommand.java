package com.bendevnull.DiscordBot.Commands.General;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Database.GuildDAO;
import com.bendevnull.DiscordBot.Embed.CommandResponseEmbed;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

public class PrefixCommand extends Command {

    public PrefixCommand() {
        super("prefix",
              "Changes or views the guild's command prefix",
              "$cmd | $cmd <New Prefix>",
              null,
              "Admin");
    }

    public void run(Message message, String[] args) {
        GuildDAO g = Program.getGuildDAO();
        Guild guild = message.getGuild();

        if (args.length == 0 || !message.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            message.getChannel().sendMessage(new CommandResponseEmbed("Guild prefix is currently " + g.getPrefix(guild),
                null).build()).queue();
        } else {
            g.updatePrefix(guild, args[0]);
            message.getChannel().sendMessage(new CommandResponseEmbed("Guild prefix set to " + g.getPrefix(guild),
                null).build()).queue();
        }
    }
}