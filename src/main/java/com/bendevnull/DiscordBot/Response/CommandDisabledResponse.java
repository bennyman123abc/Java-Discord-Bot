package com.bendevnull.DiscordBot.Response;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Embed.ErrorEmbed;

import net.dv8tion.jda.core.entities.Message;

public class CommandDisabledResponse extends Command {

    // private static String cmdName;
    private static Command cmd;
    
    public CommandDisabledResponse() {
        super("resp-cdr", null, null, null);
    }

    public void run(Message msg, String[] args) {
        msg.getChannel().sendMessage(new ErrorEmbed("The bot owner/host has disabled that command.").build());
    }

    public static void fire(Message m) {
        Program.getCommandHandler().runCommand(cmd, m, null);
    }
}