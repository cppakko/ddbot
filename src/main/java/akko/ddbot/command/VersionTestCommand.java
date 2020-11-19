package akko.ddbot.command;

import java.util.ArrayList;

import akko.ddbot.BotMainActivity;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.returndata.ReturnStatus;
import cc.moecraft.icq.user.User;

public class VersionTestCommand implements EverywhereCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("version","v");
    }

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        ReturnStatus data = BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(1041084231, "test").getStatus();
        return data.toString();
    }
    
}
