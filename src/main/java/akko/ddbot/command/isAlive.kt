package akko.ddbot.command

import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.EverywhereCommand
import cc.moecraft.icq.event.events.message.EventMessage
import cc.moecraft.icq.user.User
import java.util.*

class isAlive : EverywhereCommand {
    override fun run(eventMessage: EventMessage, user: User, s: String, arrayList: ArrayList<String>): String {
        return "感觉良好 由PicqBotX和Kotlin强力驱动"
    }

    override fun properties(): CommandProperties {
        return CommandProperties("还活着吗")
    }
}
