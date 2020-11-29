package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.InitCheck
import akko.ddbot.data.BilibiliApi.BilibiliDataClass
import akko.ddbot.network.BilibiliApiService
import akko.ddbot.sql.SQLFun
import akko.ddbot.utilities.GlobalObject
import com.fasterxml.jackson.module.kotlin.*
import okhttp3.ResponseBody
import org.hydev.logger.foreground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.awt.Color
import java.io.IOException
import java.sql.Connection
import java.sql.SQLException

val LiveRoomTask = Thread {
    val oMapper = jacksonObjectMapper()
    val retrofit = Retrofit.Builder().baseUrl("https://api.bilibili.com/x/space/acc/").build()
    try {
        while (true) {
            LiverInit()
            val liverList: List<String>? = LiverInit.liverList
            for (vID in liverList!!) {
                val call = retrofit.create(BilibiliApiService::class.java).getDatCall(vID)
                call!!.enqueue(object: Callback<ResponseBody?>{
                    override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                        if (response.body() != null) {
                            val sqliteC: Connection = SQLFun().connection("bot")!!
                            val body = response.body()!!.string()
                            val data = oMapper.readValue<BilibiliDataClass>(body).data
                            val liveRoomData = data.live_room
                            val statusRightNow = liveRoomData.liveStatus
                            GlobalObject.LiveRoomlog.debug("${Color(252, 168, 187).foreground()}$vID 检查完成")
                            val resultSet= sqliteC.prepareStatement("select * from  groupinfo.vliver WHERE \"vID\" = '$vID';").executeQuery()
                            resultSet.next()
                            val statusindb = resultSet.getInt("vSTATE")
                            if (statusRightNow == 1 && statusindb == 0) {
                                sqliteC.prepareStatement("UPDATE groupinfo.vliver SET \"vSTATE\" = 1 WHERE \"vID\" = '$vID';").execute()
                                RemindListener().remindListenerFun(liveRoomData.cover, vID, data.name, liveRoomData.title, liveRoomData.url)
                            } else if (statusRightNow == 0 && statusindb == 1) {
                                sqliteC.prepareStatement("UPDATE groupinfo.vliver SET \"vSTATE\" = 0 WHERE \"vID\" = '$vID';").execute()
                            }
                            sqliteC.close()
                        } else {
                            BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(InitCheck.GROUP_ID.toLong(), "b站网络出问题了(确信")
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
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