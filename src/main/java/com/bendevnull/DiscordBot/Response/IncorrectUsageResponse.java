package com.bendevnull.DiscordBot.Response;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Util;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Embed.ErrorEmbed;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

public class IncorrectUsageResponse extends Command {

    private static Command cmd;
    private static Guild guild;

    // private static String cmdName;
    private static Command iur;

    public IncorrectUsageResponse() {
        super("resp-iur", "", "", null);
        // cmdName = this.getName();
        iur = this;
    }

    public void run(Message m, String[] args) {
        m.getChannel().sendMessage(new ErrorEmbed(String.format("Incorrect command usage!\n\nUsage:\n%s",
            Util.renderUsage(cmd, guild))).build()).queue();
    }

    public static void fire(Message m, Command c) {
        cmd = c;
        guild = m.getGuild();
        Program.getCommandHandler().runCommand(iur, m, null);
    }
}