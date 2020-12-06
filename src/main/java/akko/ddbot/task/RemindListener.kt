package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.sql.SQLFun
import akko.ddbot.utilities.groupMsg
import akko.ddbot.utilities.rawGroupMsg
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentAt
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.sender.returndata.ReturnData
import cc.moecraft.icq.sender.returndata.ReturnStatus
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData
import cn.hutool.http.HttpException
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

fun remindListenerFun(cover: String?, vID: String, vNAME: String, title: String?, url: String?) {
    try {
        val tuple = SQLFun().executeQuery("SELECT user_id FROM groupinfo.follow_info WHERE vid = (SELECT id FROM groupinfo.vliver WHERE \"vID\" = '${vID}')")
        val res: ResultSet = tuple!!.first
        val mb = MessageBuilder()
        val mbWithoutImg = MessageBuilder()
        var count = 0
        mbWithoutImg.run {
            add("$vNAME 开播力~").newLine()
            add(UUID.randomUUID()).newLine()
            add("---------------").newLine()
            add(title).newLine()
            add(url).newLine()
            add("---------------").newLine()
        }
        mb.run {
            add("$vNAME 开播力~").newLine()
            add(UUID.randomUUID()).newLine()
            add("---------------").newLine()
            add(ComponentImage(cover))
            add(title).newLine()
            add(url).newLine()
            add("---------------").newLine()
        }
        if (res.row > 0) {
            do {
                mb.run {
                    add(ComponentAt(res.getString("user_id").toLong()))
                    add(" ")
                }
                mbWithoutImg.run {
                    add(ComponentAt(res.getString("user_id").toLong()))
                    add(" ")
                }
                count++
                if (count == 3) {
                    mb.newLine()
                    count = 0
                }
            } while (res.next())
        }
        tuple.second.close()
        val groupId = Init.GROUP_ID.toLong()
        var data: ReturnData<RMessageReturnData>? = null
        try {
            data = rawGroupMsg(groupId, mb.toString())
        }catch (e: HttpException)
        {
            groupMsg(Init.GROUP_ID.toLong(), MessageBuilder().add("被风控力").newLine().add(ComponentImage("amamiya_err.jpg")).toString())
            groupMsg(Init.GROUP_ID.toLong(),mbWithoutImg.toString())
            return
        }
        var rStatus = data!!.status
        println(rStatus)
        println(data.returnCode)
        var retryCount = 0
        while (rStatus != ReturnStatus.ok && retryCount <= 5) {
            if (retryCount == 5) {
                groupMsg(groupId, "重试了五次也没发出来  饶了我吧(哭")
                groupMsg(Init.GROUP_ID.toLong(), MessageBuilder().add(ComponentImage("amamiya_err.jpg")).toString())
                break
            }
            rStatus = groupMsg(groupId, mb.toString())
            retryCount++
        }
    } catch (e: SQLException) {
        BotMainActivity.ExceptionLogger!!.debug(e.message)
        e.printStackTrace()
    }
}