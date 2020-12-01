package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.sql.SQLFun
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
                val pair = SQLFun().executeQuery("SELECT * FROM groupinfo.vliver;")
                val set: ResultSet = pair!!.first
                do {
                    (liverList as LinkedList<String>).add(set.getString("vID"))
                } while (set.next())
                pair.second.close()
            } catch (e: SQLException) {
                BotMainActivity.ExceptionLogger!!.debug(e.message)
                e.printStackTrace()
            }
        }
    }
}