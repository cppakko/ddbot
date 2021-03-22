package akko.ddbot.command

import akko.ddbot.Init
import akko.ddbot.sql.KtormObject
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.support.postgresql.PostgreSqlDialect
import java.lang.StringBuilder
import java.util.*

class TestCommand : GroupCommand{
    override fun properties(): CommandProperties {
        return CommandProperties("test")
    }

    override fun groupMessage(event: EventGroupMessage?, sender: GroupUser?, group: Group?, command: String?, args: ArrayList<String>?): String {
        val database = Database.connect("jdbc:postgresql://${Init.POSTGRE_URL}/${Init.POSTGRE_DATABASE}","org.postgresql.Driver", Init.POSTGRE_USER, Init.POSTGRE_PASSWD,PostgreSqlDialect())
        val stringBuilder = StringBuilder()
        database.from(KtormObject.VliverInfo).select().sql
        for (row in database.from(KtormObject.VliverInfo).select())
        {
            stringBuilder.run {
                append(row[KtormObject.VliverInfo.pid])
                append(row[KtormObject.VliverInfo.name])
            }
        }
        return stringBuilder.toString()
    }

}