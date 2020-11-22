package akko.ddbot.task

import akko.ddbot.sql.SQLFun
import akko.ddbot.sql.TwoTuple
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class LiverInit {
    companion object {
        var liverList: MutableList<String>? = null
        fun init() {
            liverList = LinkedList()
            try {
                val tuple: TwoTuple<ResultSet?, Connection?>? = SQLFun().executeQuery("GroupInfo", "SELECT * FROM vLiver;")
                val set: ResultSet = tuple!!.resultSet
                while (set.next()) {
                    (liverList as LinkedList<String>).add(set.getString("vID"))
                }
                tuple.connection.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}