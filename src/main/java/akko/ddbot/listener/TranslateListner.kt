package akko.ddbot.listener

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.data.TranslateData.TranslateData
import akko.ddbot.utilities.GlobalObject
import akko.ddbot.utilities.getMsg
import akko.ddbot.utilities.ocrFun
import akko.ddbot.utilities.translate.TransApi
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
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
            if (matcher.find())
            {
                if ("id=" == matcher.group(1)) {
                    val isMinus = "-" == matcher.group(2)
                    val messageId = if (isMinus) "-" + matcher.group(3) else matcher.group(3)
                    try {
                        pattern = Pattern.compile("(file=)([0-9a-z.]*)")
                        val rowMessage = getMsg(messageId).message
                        val imgM = pattern.matcher(rowMessage)
                        val transApi = TransApi(Init.BAIDU_APP_ID!!, Init.BAIDU_SECURITY_KEY!!)
                        val mb = MessageBuilder()
                        if (imgM.find())
                        {
                            val data = ocrFun(imgM.group(2).toString()).data!!
                            val language = data.language
                            val textsList = data.texts!!
                            for (t in textsList) {
                                val transJS = transApi.getTransResult(t.text!!, "auto", "zh")
                                val unicode = GlobalObject.objectMapper.readValue(transJS, TranslateData::class.java).transResult!![0].dst
                                mb.add(unicode)
                                mb.newLine()
                            }
                        }
                        else
                        {
                            val transJS = transApi.getTransResult(rowMessage, "auto", "zh")
                            val unicode = GlobalObject.objectMapper.readValue(transJS, TranslateData::class.java).transResult!![0].dst
                            mb.add(unicode)
                            mb.newLine()
                        }
                        event.respond(mb.toString())
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