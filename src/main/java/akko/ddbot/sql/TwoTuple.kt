package akko.ddbot.sql

import java.sql.Connection
import java.sql.ResultSet

class TwoTuple<A, B>(val resultSet: ResultSet, val connection: Connection)