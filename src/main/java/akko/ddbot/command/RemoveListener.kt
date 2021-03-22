package akko.ddbot.command

import akko.ddbot.sql.KtormObject
import akko.ddbot.sql.connectionPool
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import java.util.*

class RemoveListener : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("remove")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        val arr = arg0.getMessage().split(" ".toRegex()).toTypedArray()
        return if (arr.size == 2) {
            //arr[1] 主播pid            arg1.id 发送者QQ号
            val connection = Database.connect(connectionPool.connectionPool)
            connection.delete(KtormObject.FollowTable)
            {
                //TODO toLong
                it.userId eq arg1.id.toInt()
            }
            "操作成功 yattaze"
        } else {
            "ERR 输入有误"
        }
    }
}