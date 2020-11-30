package akko.ddbot.command

import akko.ddbot.sql.SQLFun
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.util.*

class AddListener : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("add")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        val arr = arg0.getMessage().split(" ".toRegex()).toTypedArray()
        return if (arr.size == 2) {
            // arg1.id 发送者QQ号         arr[1]主播pid
            SQLFun().execute("INSERT INTO groupinfo.follow_info VALUES(${arg1.id},(SELECT \"id\" FROM groupinfo.vliver WHERE vliver.\"vID\" = '${arr[1]}'));")
            "操作成功 yattaze"
        } else {
            "ERR 输入有误"
        }
    }
}