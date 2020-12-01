package akko.ddbot.sql

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import java.sql.*

class SQLFun {
    fun execute(sql: String): Boolean
    {
        try {
            Class.forName("org.postgresql.Driver")
            val sqliteC = DriverManager.getConnection("jdbc:postgresql://${Init.POSTGRE_URL}/${Init.POSTGRE_DATABASE}", Init.POSTGRE_USER, Init.POSTGRE_PASSWD)
            val res = sqliteC.prepareStatement(sql).execute()
            sqliteC.close()
            return res
        } catch (e: ClassNotFoundException) {
            BotMainActivity.SQLLogger!!.debug(e.message)
        } catch (e: SQLException) {
            BotMainActivity.SQLLogger!!.debug(e.message)
        }
        return false
    }
    fun executeQuery(sql: String?): Pair<ResultSet, Connection>? {
        try {
            Class.forName("org.postgresql.Driver")
            val sqliteC = DriverManager.getConnection("jdbc:postgresql://${Init.POSTGRE_URL}/${Init.POSTGRE_DATABASE}",Init.POSTGRE_USER,Init.POSTGRE_PASSWD)
            val res = sqliteC.prepareStatement(sql).executeQuery()
            res.next()
            return Pair<ResultSet, Connection>(res, sqliteC)
        } catch (e: ClassNotFoundException) {
            BotMainActivity.SQLLogger!!.debug(e.message)
        } catch (e: SQLException) {
            BotMainActivity.SQLLogger!!.debug(e.message)
        }
        return null
    }
    fun connection(): Connection? {
        try {
            Class.forName("org.postgresql.Driver")
            return DriverManager.getConnection("jdbc:postgresql://${Init.POSTGRE_URL}/${Init.POSTGRE_DATABASE}",Init.POSTGRE_USER,Init.POSTGRE_PASSWD)
        } catch (e: ClassNotFoundException) {
            BotMainActivity.SQLLogger!!.debug(e.message)
        } catch (e: SQLException) {
            BotMainActivity.SQLLogger!!.debug(e.message)
        }
        return null
    }
    fun PreparedStatement(sql: String): Pair<PreparedStatement,Connection>
    {
        Class.forName("org.postgresql.Driver")
        val conn = DriverManager.getConnection("jdbc:postgresql://${Init.POSTGRE_URL}/${Init.POSTGRE_DATABASE}",Init.POSTGRE_USER,Init.POSTGRE_PASSWD)
        return Pair<PreparedStatement,Connection>(conn.prepareStatement(sql),conn)
    }
}