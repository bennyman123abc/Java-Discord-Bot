package com.bendevnull.DiscordBot.Response;

import java.util.Random;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Commands.Command;
import com.bendevnull.DiscordBot.Embed.ErrorEmbed;

import net.dv8tion.jda.core.entities.Message;

public class NoPermissionResponse extends Command {

    private static String cmdName;

    public NoPermissionResponse() {
        super("resp-npr", "", "", null);
        cmdName = this.getName();
    }

    public void run(Message m, String[] args) {
        String[] names = new String[]{
            "Bob",
            "Bill",
            "Agatha",
            "Joe",
            "Susan",
            "Calium",
            "Elizabeth",
            "Frank",
            "Dave",
            "Jerry",
            "Princess Consuella Banana Hammock",
            "Crapbag"
        };
        String name = names[new Random().nextInt(names.length)];
        m.getChannel().sendMessage(new ErrorEmbed(String.format("Dammit %s! You don't have permission to use that command!", name)).build()).queue();
    }

    public static void fire(Message m) {
        Program.getCommandHandler().runCommand(cmdName, m, null);
    }
}