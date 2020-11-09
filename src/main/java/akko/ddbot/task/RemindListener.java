package akko.ddbot.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import akko.ddbot.BotMainActivity;
import akko.ddbot.sql.SQLFun;
import akko.ddbot.sql.TwoTuple;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;

public class RemindListener {
    void RemindListenerFun(String vID, String vNAME, String title, String url) {
        try {
            TwoTuple<ResultSet,Connection> tuple = new SQLFun().executeQuery("ListenerInfo","select * from  V" + vID + ";");
            ResultSet res = tuple.resultSet;
            MessageBuilder mb = new MessageBuilder();
            int count = 0;
            mb.add(vNAME + " 开播力~").newLine()
                    .add("---------------").newLine()
                    .add(title).newLine()
                    .add(url).newLine()
                    .add("---------------").newLine();
            while (res.next())
            {
                mb.add(new ComponentAt(Long.parseLong(res.getString("ID"))));
                mb.add(" ");
                count++;
                if (count == 3)
                {
                    mb.newLine();
                    count = 0;
                }
            }
            tuple.connection.close();
            BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(955263823, mb.toString());
        } 
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
