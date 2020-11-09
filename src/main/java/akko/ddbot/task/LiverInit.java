package akko.ddbot.task;

import akko.ddbot.sql.SQLFun;
import akko.ddbot.sql.TwoTuple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LiverInit {
    public static List<String> liverList;

    public void init() {
        liverList = new LinkedList<>();
        
        try 
        {
            TwoTuple<ResultSet,Connection> tuple = new SQLFun().executeQuery("GroupInfo","SELECT * FROM vLiver;");
            ResultSet set = tuple.resultSet;
            while (set.next()) {
                liverList.add(set.getString("vID"));
            }
            tuple.connection.close();
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
