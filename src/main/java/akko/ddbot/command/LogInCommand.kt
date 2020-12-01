package akko.ddbot.command

import akko.ddbot.sql.SQLFun
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.EverywhereCommand
import cc.moecraft.icq.event.events.message.EventMessage
import cc.moecraft.icq.user.User
import java.util.*

class LogInCommand : EverywhereCommand{
    override fun properties(): CommandProperties {
        return CommandProperties("login","登录")
    }

    override fun run(event: EventMessage?, sender: User?, command: String?, args: ArrayList<String>?): String {
        val conn = SQLFun().connection()
        val set = conn!!.createStatement().executeQuery("SELECT token FROM imgcollect.user_token WHERE user_id = ${sender!!.id};")
        if (set.isBeforeFirst)
        {
            set.next()
            event!!.respond(set.getString(1))
            conn.close()
            return ""
        }
        else
        {
            conn.close()
            //TODO SQL UPDATE
            val uuid = UUID.randomUUID()
            SQLFun().execute("INSERT INTO imgcollect.user_token VALUES(${sender.id},'${uuid.toString()}');")
            event!!.respond(uuid.toString())
        }
        return ""
    }
}