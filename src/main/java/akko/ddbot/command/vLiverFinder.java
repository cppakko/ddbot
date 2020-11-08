package akko.ddbot.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

public class vLiverFinder implements GroupCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("vLiver");
    }

    @Override
    public String groupMessage(EventGroupMessage arg0, GroupUser arg1, Group arg2, String arg3,
            ArrayList<String> arg4) {
        try 
        {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/GroupInfo.db");
            ResultSet set = sqliteC.prepareStatement("select * from vLiver;").executeQuery();
            MessageBuilder mb = new MessageBuilder();
            mb.add("----------").newLine();
            mb.add("uid | Name").newLine();
            while (set.next()) {
                mb.add(set.getString("vID") + "   " + set.getString("vNAME")).newLine();
            }
            return mb.add("----------").toString();
        } 
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
}
