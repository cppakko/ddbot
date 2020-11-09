package akko.ddbot.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
            try {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteListner = DriverManager.getConnection("jdbc:sqlite:db/ListenerInfo.db");
            sqliteListner.createStatement().execute("create table V" + arr[1] + "(ID TEXT PRIMARY KEY NOT NULL) ;");
            sqliteListner.close();
            Class.forName("org.sqlite.JDBC");
            Connection sqliteGroup = DriverManager.getConnection("jdbc:sqlite:db/GroupInfo.db");
            sqliteGroup.createStatement().execute("insert into vLiver values (\'" + arr[1]+ "\',\'" + arr[2] + "\',0);");
            sqliteGroup.close();
            
            new LiverInit().init();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        else return "输入有误";
        return "✔";
    }
    
}
