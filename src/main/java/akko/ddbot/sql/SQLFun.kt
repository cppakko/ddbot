package akko.ddbot.sql

import akko.ddbot.InitCheck
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class SQLFun {
    fun execute(databaseName: String, sql: String): Boolean
    {
        try {
            Class.forName("org.postgresql.Driver")
            val sqliteC = DriverManager.getConnection("jdbc:postgresql://${InitCheck.POSTGRE_URL}/$databaseName",InitCheck.POSTGRE_USER,InitCheck.POSTGRE_PASSWD)
            val res = sqliteC.prepareStatement(sql).execute()
            sqliteC.close()
            return res
        } catch (e: ClassNotFoundException) {
            println(e.message)
            e.printStackTrace()
        } catch (e: SQLException) {
            println(e.message)
            e.printStackTrace()
        }
        return false
    }
    fun executeQuery(databaseName: String, sql: String?): TwoTuple<ResultSet?, Connection?>? {
        try {
            Class.forName("org.postgresql.Driver")
            val sqliteC = DriverManager.getConnection("jdbc:postgresql://${InitCheck.POSTGRE_URL}/$databaseName",InitCheck.POSTGRE_USER,InitCheck.POSTGRE_PASSWD)
            val res = sqliteC.prepareStatement(sql).executeQuery()
            res.next()
            return TwoTuple(res, sqliteC)
        } catch (e: ClassNotFoundException) {
            println(e.message)
            e.printStackTrace()
        } catch (e: SQLException) {
            println(e.message)
            e.printStackTrace()
        }
        return null
    }
    fun connection(databaseName: String): Connection? {
        try {
            Class.forName("org.postgresql.Driver")
            return DriverManager.getConnection("jdbc:postgresql://${InitCheck.POSTGRE_URL}/$databaseName",InitCheck.POSTGRE_USER,InitCheck.POSTGRE_PASSWD)
        } catch (e: ClassNotFoundException) {
            println(e.message)
            e.printStackTrace()
        } catch (e: SQLException) {
            println(e.message)
            e.printStackTrace()
        }
        return null
    }
}