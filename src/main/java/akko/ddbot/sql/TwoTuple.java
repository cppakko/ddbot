package akko.ddbot.sql;

import java.sql.Connection;
import java.sql.ResultSet;

public class TwoTuple<A,B> {
    public final ResultSet resultSet;
    public final Connection connection;
    public TwoTuple(ResultSet a,Connection b)
    {
        this.resultSet = a;
        this.connection = b;
    }
}
