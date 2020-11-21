package akko.ddbot.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import akko.ddbot.BotMainActivity;
import akko.ddbot.InitCheck;
import akko.ddbot.sql.SQLFun;
import akko.ddbot.sql.TwoTuple;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.sender.returndata.ReturnStatus;

public class RemindListener {
    void RemindListenerFun(String cover ,String vID, String vNAME, String title, String url) {
        try {
            TwoTuple<ResultSet,Connection> tuple = new SQLFun().executeQuery("ListenerInfo","select * from  V" + vID + ";");
            ResultSet res = tuple.resultSet;
            MessageBuilder mb = new MessageBuilder();
            int count = 0;
            mb.add(vNAME + " 开播力~").newLine()
                    .add("---------------").newLine()
                    .add(new ComponentImage(cover))
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
            ReturnStatus rStatus = BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(InitCheck.GROUP_ID, mb.toString()).getStatus();
            int retryCount = 0;
            while (rStatus != ReturnStatus.ok && retryCount <= 5)
            {
                if (retryCount == 5)
                {
                    BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(InitCheck.GROUP_ID, "重试了五次也没发出来  饶了我吧(哭");
                    break;
                }
                rStatus = BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(InitCheck.GROUP_ID, mb.toString()).getStatus();
                retryCount++;
            }
        } 
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
