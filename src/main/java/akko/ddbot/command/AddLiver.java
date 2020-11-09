package akko.ddbot.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import akko.ddbot.sql.SQLFun;
import akko.ddbot.task.LiverInit;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

public class AddLiver implements GroupCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("addLiver");
    }

    @Override
    public String groupMessage(EventGroupMessage arg0, GroupUser arg1, Group arg2, String arg3,
            ArrayList<String> arg4) {
        String[] arr = arg0.getMessage().split(" ");
        if (arr.length == 3)
        {
            new SQLFun().execute("ListenerInfo","create table V" + arr[1] + "(ID TEXT PRIMARY KEY NOT NULL) ;");
            new SQLFun().execute("GroupInfo","insert into vLiver values ('" + arr[1]+ "','" + arr[2] + "',0);");
            new LiverInit().init();
        }
        else {
            return "输入有误";
        }
        return "✔";
    }
    
}
