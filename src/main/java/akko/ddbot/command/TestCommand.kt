package akko.ddbot.command

import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.io.File
import java.util.ArrayList

class TestCommand : GroupCommand{
    override fun properties(): CommandProperties {
        return CommandProperties("test")
    }

    override fun groupMessage(event: EventGroupMessage?, sender: GroupUser?, group: Group?, command: String?, args: ArrayList<String>?): String {
        return MessageBuilder().add(ComponentImage("amamiya_err.jpg")).toString()
    }

}