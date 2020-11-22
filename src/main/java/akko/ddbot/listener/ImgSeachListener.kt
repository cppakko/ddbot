package akko.ddbot.listener

import akko.ddbot.InitCheck
import akko.ddbot.data.CQGetImgData.CQGetImgData
import akko.ddbot.data.MessageGetData.MesGetData
import akko.ddbot.network.SaucenaoApiService
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import java.io.IOException
import java.util.regex.Pattern

class ImgSeachListener : IcqListener() {
    @EventHandler
    fun groupTest(event: EventGroupMessage) {
        var pattern = Pattern.compile("(CQ:reply)(.+)(给我翻译翻译)")
        val firstMatcher = pattern.matcher(event.message)
        if (firstMatcher.find() && "CQ:reply" == firstMatcher.group(1) && "给我翻译翻译" == firstMatcher.group(3)) {
            val rawMessage = event.rawMessage
            val regex = "(id=)(-*)(\\d*)"
            pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(rawMessage)
            if (matcher.find()) {
                if ("id=" == matcher.group(1)) {
                    val isMinus = "-" == matcher.group(2)
                    val messageId = if (isMinus) "-" + matcher.group(3) else matcher.group(3)
                    val url = "http://0.0.0.0:5700/get_msg?message_id=" + messageId + "&access_token=" + InitCheck.ACCESS_TOKEN
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val call = client.newCall(request)
                    val om = ObjectMapper()
                    try {
                        val body = call.execute().body()!!.string()
                        val rawImginfo = om.readValue(body, MesGetData::class.java).data.message
                        pattern = Pattern.compile("(file=)([0-9a-z.]*)")
                        val imgM = pattern.matcher(rawImginfo)
                        if (imgM.find()) {
                            val getImgRequest = Request.Builder().url("http://0.0.0.0:5700/get_image?file=" + imgM.group(2) + "&access_token=" + InitCheck.ACCESS_TOKEN).build()
                            val imgQqUrl = om.readValue(client.newCall(getImgRequest).execute().body()!!.string(), CQGetImgData::class.java).data.url
                            val retrofit = Retrofit.Builder().baseUrl("https://saucenao.com/").build()
                            val search_body: String = retrofit.create(SaucenaoApiService::class.java).get(999, 2, 1, 1, imgQqUrl)!!.execute().body()!!.string()
                        }
                    } catch (e: IOException) {
                        event.respond(e.message)
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
