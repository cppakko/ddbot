package akko.ddbot.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFun {
    public Boolean execute(String databaseName, String sql)
    {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/" + databaseName +".db");
            Boolean res = sqliteC.prepareStatement(sql).execute();
            sqliteC.close();
            return res;
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public TwoTuple<ResultSet,Connection> executeQuery(String databaseName, String sql)
    {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/" + databaseName +".db");
            ResultSet res = sqliteC.prepareStatement(sql).executeQuery();
            return new TwoTuple<ResultSet, Connection>(res,sqliteC);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Connection connection(String databaseName)
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:db/" + databaseName + ".db");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
