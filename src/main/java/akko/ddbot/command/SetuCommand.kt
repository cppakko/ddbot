package akko.ddbot.command

import akko.ddbot.InitCheck
import akko.ddbot.data.LoliconApi.LoliconApiDataClass
import akko.ddbot.network.LoliconApiNetwork
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException
import java.util.*

class SetuCommand : GroupCommand {
    override fun groupMessage(eventGroupMessage: EventGroupMessage, groupUser: GroupUser, group: Group, s: String, arrayList: ArrayList<String>): String {
        val retrofit = Retrofit.Builder().baseUrl("https://api.lolicon.app/").build()
        val call: Call<ResponseBody?>? = retrofit.create(LoliconApiNetwork::class.java)[InitCheck.LOLICON_APIKEY, "true"]
        val om = ObjectMapper()
        return try {
            val body: String
            val responsebody = call?.execute()?.body()
            body = if (responsebody != null) {
                responsebody.string()
            } else {
                return "Lolicon网络有点问题(确信"
            }
            println(body)
            val url = om.readValue(body, LoliconApiDataClass::class.java).data[0].url
            MessageBuilder().add(ComponentImage(url)).toString()
        } catch (e: IOException) {
            e.printStackTrace()
            e.message!!
        }
    }

    override fun properties(): CommandProperties {
        return CommandProperties("setu")
    }
}
