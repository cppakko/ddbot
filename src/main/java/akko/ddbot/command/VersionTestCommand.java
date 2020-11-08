package akko.ddbot.command;

import java.util.ArrayList;

import akko.ddbot.BotMainActivity;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.user.User;

public class VersionTestCommand implements EverywhereCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("version","v");
    }

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(1041084231, "test");
        return new MessageBuilder()
        .add("vid.name 开播").newLine()
        .add("title").newLine()
        .add("url").newLine()
        .add("--------------").newLine()
        .add(new ComponentAt(1378774701))
        .add(new ComponentAt(1378774701))
        .add(new ComponentAt(1378774701))
        .add(new ComponentAt(1378774701))
        .add(new ComponentAt(1378774701))
        .add(new ComponentAt(1378774701)).newLine()
        .add("--------------").toString();

    }
    
}
