package akko.ddbot.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import akko.ddbot.BotMainActivity;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;

public class RemindListener {
    void RemindListenerFun(String vID,String vNAME,String title,String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/LinsterInfo.db");
            PreparedStatement sql = sqliteC.prepareStatement("select * from  V" + vID + ";");
            ResultSet res = sql.executeQuery();
            MessageBuilder mb = new MessageBuilder();
            int count = 0;
            mb.add(vNAME + "开播力~").newLine()
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
            } //955263823
            BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(955263823, mb.toString());
        } 
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
