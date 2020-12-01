package akko.ddbot.command

import akko.ddbot.BotMainActivity
import akko.ddbot.sql.SQLFun
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class vLiverFinder : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("vLiver")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        return try {
            val pair = SQLFun().executeQuery("SELECT * FROM groupinfo.vliver;")
            val set: ResultSet = pair!!.first
            val mb = MessageBuilder()
            mb.add("----------").newLine()
            mb.add("uid | Name").newLine()
            while (set.next()) {
                mb.add(set.getString("vID") + "   " + set.getString("vNAME")).newLine()
            }
            pair.second.close()
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