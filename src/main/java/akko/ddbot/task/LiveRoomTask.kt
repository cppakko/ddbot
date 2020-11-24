package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.InitCheck
import akko.ddbot.data.BilibiliApi.BilibiliDataClass
import akko.ddbot.network.BilibiliApiService
import akko.ddbot.sql.SQLFun
import com.fasterxml.jackson.module.kotlin.*
import retrofit2.Retrofit
import java.io.IOException
import java.sql.Connection
import java.sql.SQLException

val LiveRoomTask = Thread() {
    val oMapper = jacksonObjectMapper()
    val retrofit = Retrofit.Builder().baseUrl("https://api.bilibili.com/x/space/acc/").build()
    try {
        while (true) {
            LiverInit()
            val liverList: List<String>? = LiverInit.liverList
            val sqliteC: Connection = SQLFun().connection("GroupInfo")!!
            for (vID in liverList!!) {
                val call = retrofit.create(BilibiliApiService::class.java).getDatCall(vID)
                val rawBody = call!!.execute().body()
                val body = if (rawBody != null) {
                    rawBody.string()
                } else {
                    BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(InitCheck.GROUP_ID.toLong(), "b站网络出问题了(确信")
                    continue
                }
                val data = oMapper.readValue<BilibiliDataClass>(body).data
                val liveRoomData = data.live_room
                val statusRightNow = liveRoomData.liveStatus
                println(vID)
                val statusindb = sqliteC.prepareStatement("select * from  vLiver WHERE vID = '$vID';").executeQuery().getInt("vSTATE")
                if (statusRightNow == 1 && statusindb == 0) {
                    sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 1 WHERE vID = $vID;").execute()
                    RemindListener().RemindListenerFun(liveRoomData.cover, vID, data.name, liveRoomData.title, liveRoomData.url)
                } else if (statusRightNow == 0 && statusindb == 1) {
                    sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 0 WHERE vID = $vID;").execute()
                }
            }
            sqliteC.close()
            Thread.sleep(60000)
        }
    } catch (e: SQLException) {
        println(e.toString())
        e.printStackTrace()
    } catch (e: InterruptedException) {
        println(e.toString())
        e.printStackTrace()
    } catch (e: IOException) {
        println(e.toString())
        e.printStackTrace()
    }
}