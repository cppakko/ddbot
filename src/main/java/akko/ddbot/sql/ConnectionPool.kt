package akko.ddbot.sql

import akko.ddbot.Init
import com.mchange.v2.c3p0.ComboPooledDataSource

object connectionPool {
    val connectionPool = ComboPooledDataSource()
    fun poolInit()
    {
        connectionPool.driverClass = "org.postgresql.Driver"
        connectionPool.jdbcUrl = "jdbc:postgresql://${Init.POSTGRE_URL}/${Init.POSTGRE_DATABASE}"
        connectionPool.user = Init.POSTGRE_USER
        connectionPool.password = Init.POSTGRE_PASSWD
        connectionPool.maxStatements = 20
    }
}