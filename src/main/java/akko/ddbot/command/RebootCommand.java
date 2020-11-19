package akko.ddbot.command;

import akko.ddbot.BotMainActivity;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.io.IOException;
import java.util.ArrayList;

public class RebootCommand implements EverywhereCommand {
    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        try {
            Runtime.getRuntime().exec("sh reboot.sh");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.exit(0);
        return "正在重启";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("reboot");
    }
}
