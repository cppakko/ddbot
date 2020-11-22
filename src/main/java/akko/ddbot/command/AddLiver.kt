package akko.ddbot.command

import akko.ddbot.sql.SQLFun
import akko.ddbot.task.LiverInit
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.util.*

class AddLiver : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("addLiver")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        val arr = arg0.getMessage().split(" ".toRegex()).toTypedArray()
        if (arr.size == 3) {
            SQLFun().run {
                execute("ListenerInfo", "create table V" + arr[1] + "(ID TEXT PRIMARY KEY NOT NULL) ;")
                execute("GroupInfo", "insert into vLiver values ('" + arr[1] + "','" + arr[2] + "',0);")
            }
            LiverInit.init()
        } else {
            return "输入有误"
        }
        return "✔"
    }
}