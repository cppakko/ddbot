package akko.ddbot.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

public class SqlRetrunMessageTest implements GroupCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("test");
    }

    @Override
    public String groupMessage(EventGroupMessage arg0, GroupUser arg1, Group arg2, String arg3,
            ArrayList<String> arg4) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/GroupInfo.db");
            PreparedStatement sql = sqliteC.prepareStatement("select * from liver;");
            ResultSet res = sql.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (res.next())
            {
                sb.append(res.getInt("vId") + " \n");
                sb.append(res.getString("vNAME") + " \n");
            }
            return sb.toString();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        String temp = System.getProperty("java.class.path") + " " + System.getProperty("usr.dir");
        return temp;
    }
    
}
