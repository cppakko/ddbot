package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.data.BilibiliApi.BilibiliDataClass
import akko.ddbot.network.BilibiliApiService
import akko.ddbot.sql.KtormObject
import akko.ddbot.sql.connectionPool
import akko.ddbot.utilities.groupMsg
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.ResponseBody
import org.ktorm.database.Database
import org.ktorm.dsl.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.sql.SQLException

val LiveRoomTask = Thread {
    val oMapper = jacksonObjectMapper()
    val retrofit = Retrofit.Builder().baseUrl("https://api.bilibili.com/x/space/acc/").build()
    try {
        while (true) {
            LoadLiver.reLoad()
            val liverList: List<String> = LoadLiver.liverList
            for (vID in liverList) {
                val call = retrofit.create(BilibiliApiService::class.java).getDatCall(vID)
                call!!.enqueue(object: Callback<ResponseBody?>{
                    override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                        if (response.body() != null) {
                            val database = Database.connect(connectionPool.connectionPool)
                            val body = response.body()!!.string()
                            val data = oMapper.readValue<BilibiliDataClass>(body).data
                            val liveRoomData = data.live_room
                            val statusRightNow = liveRoomData.liveStatus
                            BotMainActivity.NLogger!!.log("$vID 检查完成")
                            var statusindb = 0
                            for (row in database.from(KtormObject.VliverInfo).select(KtormObject.VliverInfo.liveState).where {KtormObject.VliverInfo.pid like vID})
                            {
                                statusindb = row[KtormObject.VliverInfo.liveState]!!
                            }
                            if (statusRightNow == 1 && statusindb == 0) {
                                database.update(KtormObject.VliverInfo)
                                {
                                    set(it.liveState,1)
                                    where { it.pid like vID }
                                }
                                remindListenerFun(liveRoomData.cover, vID, data.name, liveRoomData.title, liveRoomData.url)
                            } else if (statusRightNow == 0 && statusindb == 1) {
                                database.update(KtormObject.VliverInfo)
                                {
                                    set(it.liveState,0)
                                    where { it.pid like vID }
                                }
                            }
                        } else {
                            groupMsg(Init.GROUP_ID.toLong(), "b站网络出问题了(确信")
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
        BotMainActivity.ExceptionLogger!!.debug(e.message)
        e.printStackTrace()
    } catch (e: InterruptedException) {
        BotMainActivity.ExceptionLogger!!.debug(e.message)
        e.printStackTrace()
    } catch (e: IOException) {
        BotMainActivity.ExceptionLogger!!.debug(e.message)
        e.printStackTrace()
    }
}