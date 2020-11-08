package akko.ddbot.command;

import java.util.ArrayList;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

public class HelpCommand implements GroupCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("help");
    }

    @Override
    public String groupMessage(EventGroupMessage arg0, GroupUser arg1, Group arg2, String arg3,
            ArrayList<String> arg4) {
        return new MessageBuilder().add("-----------").newLine()
            .add("输入 !vLiver 查看所有vtuber的uid").newLine()
            .add("输入 !add {vtuber的uid} 将自己添加到该vliver的提醒列表中").newLine()
            .add("输入 !remove {vtuber的uid} 将自己从该vliver的提醒列表中移除").newLine()
            .add("输入 !help 来查看你现在正在看的帮助").newLine()
            .add("----------").toString();
    }
    
}
