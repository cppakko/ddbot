package akko.ddbot.command

import akko.ddbot.sql.SQLFun
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.util.*

class RemoveListener : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("remove")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        val arr = arg0.getMessage().split(" ".toRegex()).toTypedArray()
        return if (arr.size == 2) {
            //TODO SQL操作检测
            //arr[1] 主播pid            arg1.id 发送者QQ号
            SQLFun().execute("DELETE FROM groupinfo.follow_info WHERE user_id = ${arg1.id} AND vid = (SELECT id FROM groupinfo.vliver WHERE \"vID\" = '${arr[1]}');")
            "操作成功 yattaze"
        } else {
            "ERR 输入有误"
        }
    }
}