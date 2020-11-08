package akko.ddbot.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

public class AddListener implements GroupCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("add");
    }

    @Override
    public String groupMessage(EventGroupMessage arg0, GroupUser arg1, Group arg2, String arg3,
            ArrayList<String> arg4) {

        String[] arr = arg0.getMessage().split(" ");
        if (arr.length == 2) {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/LinsterInfo.db");
                sqliteC.prepareStatement("INSERT INTO V" + arr[1] + " VALUES(\'" + arg1.getId() + "\');").execute();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return e.getMessage();
            }
            
            return "操作成功 yattaze";
        }
        else return "ERR 输入有误";
    }
    
}
