package akko.ddbot.command

import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.util.*

class HelpCommand : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("help")
    }

    override fun groupMessage(arg0: EventGroupMessage, arg1: GroupUser, arg2: Group, arg3: String,
                              arg4: ArrayList<String>): String {
        return MessageBuilder().run {
            add("-----------").newLine()
            add(" !vLiver 查看所有vtuber的uid").newLine()
            add(" !add {vtuber的uid} 将自己添加到该vliver的提醒列表中").newLine()
            add(" !remove {vtuber的uid} 将自己从该vliver的提醒列表中移除").newLine()
            add(" !addLiver {vtuber的uid} {vtuber的用户名} 来添加一个vLiver").newLine()
            add(" !help 来查看你现在正在看的帮助").newLine()
            add(" !setu 懂的都懂").newLine()
            add(" 对图片回复 给我翻译翻译 就会给你翻译啦(确信 ").newLine()
            add(" 对图片回复 NAONAO 就会给你NAO一下(大嘘 ").newLine()
            add(" 对图片回复 这个好 就...").newLine()
            add(" 输入!dice help获取dice帮助").newLine()
            add(" 发送#reboot来重启机器人")
            add("----------").toString()
        }
    }
}