package akko.ddbot.command;

import java.util.ArrayList;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;

public class VersionTestCommand implements EverywhereCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("version","v");
    }

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        //String regexPat = "(/new_room_cover/)(.*)";
        String line = "http://i0.hdslb.com/bfs/live/new_room_cover/7e56834fdb6a52958f7a383e8460e5581a06d94e.jpg";

        return new MessageBuilder().add(new ComponentImage(line)).toString();
    }
    
}
