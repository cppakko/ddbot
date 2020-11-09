package akko.ddbot.task;

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
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/GroupInfo.db");
            ResultSet set = sqliteC.createStatement().executeQuery("select * from vLiver;");
            
            while (set.next()) {
                liverList.add(set.getString("vID"));
            }
            sqliteC.close();
        } 
        catch (SQLException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
}
