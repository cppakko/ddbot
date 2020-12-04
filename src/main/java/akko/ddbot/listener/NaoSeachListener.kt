package akko.ddbot.listener

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.network.SaucenaoApiService
import akko.ddbot.utilities.*
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Retrofit
import java.io.IOException
import java.util.regex.Pattern

class NaoSeachListener : IcqListener() {
    @EventHandler
    fun listner(event: EventGroupMessage) {
        GlobalScope.launch {
            var pattern = Pattern.compile("(CQ:reply)(.+)(NAONAO)")
            val firstMatcher = pattern.matcher(event.message)
            if (firstMatcher.find() && "CQ:reply" == firstMatcher.group(1) && "NAONAO" == firstMatcher.group(3)) {
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
                                val retrofit = Retrofit.Builder().baseUrl("https://saucenao.com/").build()
                                retrofit.create(SaucenaoApiService::class.java)[999, 2, 1, 1, imgQqUrl, Init.SAUCENAO_API_KEY]?.enqueue(object: retrofit2.Callback<ResponseBody?> {
                                    override fun onResponse(call: retrofit2.Call<ResponseBody?>, response: retrofit2.Response<ResponseBody?>) {
                                        val searchBody = response.body()!!.string()
                                        val result = SauceHelper.extract(searchBody,1)[0]
                                        val builder = MessageBuilder()
                                        builder.run {
                                            add(ComponentImage(result.thumbnail)).newLine()
                                            for (str: String in result.extUrls) {
                                                add(str).newLine()
                                            }
                                            add("相似度 -> " + result.similarity)
                                        }
                                        if (result.similarity < 50) {
                                            builder.run {
                                                newLine()
                                                add("你这图怎么回事啊").newLine()
                                            }
                                        }
                                        builder.add("为什么不试试神奇的ASCASC呢")
                                        groupMsg(event.groupId,builder.toString())
                                    }
                                    override fun onFailure(call: retrofit2.Call<ResponseBody?>, t: Throwable) {
                                        groupMsg(Init.GROUP_ID.toLong(),MessageBuilder().add(ComponentImage("amamiya_err.jpg")).toString())
                                    }
                                })
                            }
                        } catch (e: IOException) {
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