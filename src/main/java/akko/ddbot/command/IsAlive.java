package akko.ddbot.command;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class IsAlive implements EverywhereCommand {
    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        return "感觉良好";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("还活着吗");
    }
}
