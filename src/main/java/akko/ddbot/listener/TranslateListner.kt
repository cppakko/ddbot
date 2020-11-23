package akko.ddbot.listener

import akko.ddbot.InitCheck
import akko.ddbot.data.MessageGetData.MesGetData
import akko.ddbot.data.OCRdata.OCRdata
import akko.ddbot.data.TranslateData.TranslateData
import akko.ddbot.task.translate.TransApi
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.regex.Pattern

class TranslateListener : IcqListener() {
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
                    val message_id = if (isMinus) "-" + matcher.group(3) else matcher.group(3)
                    val url = "http://0.0.0.0:5700/get_msg?message_id=" + message_id + "&access_token=" + InitCheck.ACCESS_TOKEN
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val call = client.newCall(request)
                    val om = ObjectMapper()
                    try {
                        val body = call.execute().body()!!.string()
                        val raw_imgInfo = om.readValue(body, MesGetData::class.java).data!!.message
                        pattern = Pattern.compile("(file=)([0-9a-z.]*)")
                        val imgM = pattern.matcher(raw_imgInfo)
                        if (imgM.find()) {
                            val ocr_url = "http://0.0.0.0:" + InitCheck.POST_PORT.toString() + "/.ocr_image?image=" + imgM.group(2).toString() + "&access_token=" + InitCheck.ACCESS_TOKEN
                            val request_ocr = Request.Builder().url(ocr_url).build()
                            val responseBodyCall = client.newCall(request_ocr)
                            val jsBody = responseBodyCall.execute().body()!!.string()
                            val data = om.readValue(jsBody, OCRdata::class.java).data!!
                            val language = data.language
                            val textsList = data.texts!!
                            val mb = MessageBuilder()
                            if ("zh" == language) {
                                for (t in textsList) {
                                    mb.add(t.text)
                                    mb.newLine()
                                }
                            } else {
                                val transApi = TransApi(InitCheck.BAIDU_APP_ID, InitCheck.BAIDU_SECURITY_KEY)
                                for (t in textsList) {
                                    val transJS = transApi.getTransResult(t.text, "auto", "zh")
                                    val unicode = om.readValue(transJS, TranslateData::class.java).transResult!![0].dst
                                    mb.add(unicode)
                                    mb.newLine()
                                }
                            }
                            event.respond(mb.toString())
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