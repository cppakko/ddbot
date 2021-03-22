package akko.ddbot.command

import akko.ddbot.sql.KtormObject
import akko.ddbot.sql.connectionPool
import akko.ddbot.task.LoadLiver
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import org.ktorm.database.Database
import org.ktorm.dsl.insert
import java.util.*

class AddLiver : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("addLiver")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        val arr = arg0.getMessage().split(" ".toRegex()).toTypedArray()
        if (arr.size == 3) {
            val connection = Database.connect(connectionPool.connectionPool)
            connection.insert(KtormObject.VliverInfo)
            {
                set(KtormObject.VliverInfo.pid,arr[1])
                set(KtormObject.VliverInfo.name,arr[2])
                set(KtormObject.VliverInfo.liveState,0)
            }
            LoadLiver.reLoad()
        } else {
            return "输入有误"
        }
        return "ok~"
    }
}