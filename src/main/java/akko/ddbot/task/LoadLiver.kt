package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.sql.KtormObject
import akko.ddbot.sql.connectionPool
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import java.sql.SQLException
import java.util.*

class LoadLiver {
    companion object {
        var liverList: MutableList<String> = LinkedList()
        fun reLoad() {
            liverList = LinkedList()
            try {
                for (row in Database.connect(connectionPool.connectionPool).from(KtormObject.VliverInfo).select())
                {
                    liverList.add(row[KtormObject.VliverInfo.pid]!!)
                }
            } catch (e: SQLException) {
                BotMainActivity.ExceptionLogger!!.debug(e.message)
                e.printStackTrace()
            }
        }
    }
}