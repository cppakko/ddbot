package akko.ddbot.listener

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.utilities.getImg
import akko.ddbot.utilities.getMsg
import akko.ddbot.utilities.groupMsg
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.util.regex.Pattern

class Ascii2dSeachListener: IcqListener() {
    @EventHandler
    fun listner(event: EventGroupMessage)
    {
        GlobalScope.launch {
            var pattern = Pattern.compile("(CQ:reply)(.+)(ASCASC)")
            val firstMatcher = pattern.matcher(event.message)
            if (firstMatcher.find() && "CQ:reply" == firstMatcher.group(1) && "ASCASC" == firstMatcher.group(3)) {
                val rawMessage = event.rawMessage
                val regex = "(id=)(-*)(\\d*)"
                pattern = Pattern.compile(regex)
                val matcher = pattern.matcher(rawMessage)
                if (matcher.find()) {
                    if ("id=" == matcher.group(1)) {
                        val isMinus = "-" == matcher.group(2)
                        val messageId = if (isMinus) "-" + matcher.group(3) else matcher.group(3)
                        try {
                            val rawImginfo = getMsg(messageId).message
                            pattern = Pattern.compile("(file=)([0-9a-z.]*)")
                            val imgM = pattern.matcher(rawImginfo)
                            if (imgM.find()) {
                                val imgQqUrl = getImg(imgM.group(2)).data.url
                                val cilent = OkHttpClient()
                                val request = Request.Builder().url("https://ascii2d.net/search/url/$imgQqUrl").build()
                                val body = cilent.newCall(request).execute().body()!!.string()
                                val url = "https://ascii2d.net"
                                val doc = Jsoup.parse(body)
                                val array = doc.select(".detail-box")[1].text().split(" ")
                                val mb = MessageBuilder()
                                val imgE: Elements = doc.select(".item-box img")
                                var pattern = Pattern.compile("(src=\")([\\a-z0-9/.]*)(\" alt)")
                                var matcher = pattern.matcher(imgE[1].toString())
                                if(matcher.find())
                                {
                                    mb.add(ComponentImage(url + matcher.group(2))).newLine()
                                }
                                pattern = Pattern.compile("(href=\")([a-z0-9/:.?_=&^%]*)(\")")
                                for (i in 0 until (array.size - 1))
                                {
                                    matcher = pattern.matcher(doc.select(".detail-box a")[i].toString())
                                    matcher.find()
                                    mb.add(array[i] + " " + matcher.group(2)).newLine()
                                }
                                mb.add("为什么不试试神奇的NAONAO呢")
                                event.respond(mb.toString())
                            }
                        }catch (e: IOException){
                            BotMainActivity.ExceptionLogger!!.debug(e.message)
                            event.respond(e.message)
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}