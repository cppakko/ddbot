package akko.ddbot.command

import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.util.*
import java.util.regex.Pattern

class DiceMan : GroupCommand {
    override fun groupMessage(eventGroupMessage: EventGroupMessage, groupUser: GroupUser, group: Group, s: String, arrayList: ArrayList<String>): String {
        val input = eventGroupMessage.getMessage().trim { it <= ' ' }
        val inputSlice = input.split(" ".toRegex()).toTypedArray()
        return if (inputSlice.size == 1 || inputSlice.size == 2 && inputSlice[1] == "help") {
            helpInfo()
        } else if (inputSlice.size == 3 && inputSlice[1] == "roll") {
            rollResult(inputSlice[2])
        } else {
            "啊这，命令不对呢，输入!dice help获取帮助"
        }
    }

    private fun rollResult(input: String): String {
        val matcher = rollPattern.matcher(input)
        return if (matcher.matches()) {
            try {
                val diceList: MutableList<String> = ArrayList()
                val msgBuilder = MessageBuilder()
                val random = Random()
                var `val` = 0
                var fixSum = 0
                val inputSegments = input.split("[+]".toRegex()).toTypedArray()
                for (seg in inputSegments) {
                    val segsep = seg.split("[d]".toRegex()).toTypedArray()
                    if (segsep.size == 1) {
                        val fix = seg.toInt()
                        `val` += fix
                        fixSum += fix
                    } else {
                        val diceCount = segsep[0].toInt()
                        val diceType = segsep[1].toInt()
                        var diceVal = 0
                        for (i in 0 until diceCount) {
                            diceVal += random.nextInt(diceType) + 1
                        }
                        `val` += diceVal
                        diceList.add("投掷" + diceCount + "枚" + diceType + "面骰，结果为:" + diceVal)
                    }
                }
                if (`val` < 0) {
                    return "啊这，是不是数字太大了，骰子man溢出了"
                }
                msgBuilder.add("掷骰~").newLine()
                for (diceInfo in diceList) {
                    msgBuilder.add(diceInfo).newLine()
                }
                msgBuilder.add("+" + fixSum + "补正").newLine()
                msgBuilder.add("结果：$`val`")
                msgBuilder.toString()
            } catch (e: Exception) {
                "啊这，骰子man坏了：" + e.message
            }
        } else {
            "啊这，掷筛格式错误，输入!dice help获取帮助"
        }
    }

    private fun helpInfo(): String {
        return MessageBuilder()
                .add("------DiceMan------").newLine()
                .add("dice roll {param}").newLine()
                .add("(暂时只有一个这一个roll子命令）").newLine()
                .add("param格式大致像1d6+2d20+5这样").newLine()
                .add("<a>d<b>表示掷a个b面骰，+[数字]表示补正").newLine()
                .add("具体细节建议直接问群友").newLine()
                .add("-------------------").toString()
    }

    override fun properties(): CommandProperties {
        return CommandProperties("dice", "d")
    }

    companion object {
        private const val patternStr = "^([0-9]+)d([0-9]+)(?:[+](?:(?:([0-9]+)d([0-9]+))|([0-9]+)))*$"
        private val rollPattern = Pattern.compile(patternStr)
    }
}