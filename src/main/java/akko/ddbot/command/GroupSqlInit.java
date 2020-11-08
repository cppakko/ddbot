package akko.ddbot.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

public class GroupSqlInit implements GroupCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("init");
    }

    @Override
    public String groupMessage(EventGroupMessage arg0, GroupUser arg1, Group arg2, String arg3,
            ArrayList<String> arg4) {
        try 
        {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/LinsterInfo.db");

            Statement sql = sqliteC.createStatement();
            sql.execute("drop table if exists G" + arg2.getId() + ";");
            StringBuilder order = new StringBuilder();
            order.append("CREATE TABLE " + "G" + arg2.getId() + "(\n");
            order.append("ID TEXT PRIMARY KEY     NOT NULL,\n");
            order.append("NAME           TEXT    NOT NULL\n");
            order.append(");");
            System.out.println(order.toString());
            sql.execute(order.toString());
            
            ReturnListData<RGroupMemberInfo> rd = arg0.getHttpApi().getGroupMemberList(arg2.getId());
            List<RGroupMemberInfo> list = rd.getData();
            for (RGroupMemberInfo rgmi : list)
            {
                order.delete(0, order.capacity() - 1);
                order.append("insert into G" + arg2.getId() + " (ID,NAME)\n");
                order.append("values (\'" + rgmi.getUserId() + "\',\'" + rgmi.getNickname() + "\');");
                System.out.println(order.toString());
                sql.execute(order.toString());
            }
            return ("yattaze");
        } 
        catch (SQLException | ClassNotFoundException e) 
        {
            e.printStackTrace();
            return e.toString();
        }
    }
    
}
