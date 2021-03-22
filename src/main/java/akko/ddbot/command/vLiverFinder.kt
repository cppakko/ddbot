package akko.ddbot.command

import akko.ddbot.BotMainActivity
import akko.ddbot.sql.KtormObject
import akko.ddbot.sql.connectionPool
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import java.sql.SQLException
import java.util.*

class vLiverFinder : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("vLiver")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        return try {
            val connection = Database.connect(connectionPool.connectionPool)
            val mb = MessageBuilder()
            mb.add("----------").newLine()
            mb.add("uid | Name").newLine()
            for (row in connection.from(KtormObject.VliverInfo).select())
            {
                mb.add(row[KtormObject.VliverInfo.pid] + "   " + row[KtormObject.VliverInfo.name]).newLine()
            }
            mb.add("----------").toString()
        } catch (e: ClassNotFoundException) {
            BotMainActivity.ExceptionLogger!!.debug(e.message)
            e.printStackTrace()
            e.message!!
        } catch (e: SQLException) {
            BotMainActivity.ExceptionLogger!!.debug(e.message)
            e.printStackTrace()
            e.message!!
        }
    }
}