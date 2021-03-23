package akko.ddbot.task

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.sql.KtormObject
import akko.ddbot.sql.connectionPool
import akko.ddbot.utilities.groupMsg
import akko.ddbot.utilities.rawGroupMsg
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentAt
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.sender.returndata.ReturnData
import cc.moecraft.icq.sender.returndata.ReturnStatus
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import java.sql.SQLException
import java.util.*

fun remindListenerFun(cover: String?, vID: String, vNAME: String, title: String?, url: String?) {
    try {
        val connection = Database.connect(connectionPool.connectionPool)
        var vid = -1;
        for (row in connection.from(KtormObject.VliverInfo).select(KtormObject.VliverInfo.id).where {
            KtormObject.VliverInfo.pid eq vID
        }) {
            vid = row[KtormObject.VliverInfo.id]!!
        }
        val res = connection.from(KtormObject.FollowTable).select(KtormObject.FollowTable.userId).where {
            KtormObject.FollowTable.id eq vid
        }
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
        for (row in res) {
            mb.run {
                add(ComponentAt(row[KtormObject.FollowTable.userId]!!.toLong()))
                add(" ")
            }
            //TODO plan to del
            mbWithoutImg.run {
                add(ComponentAt(row[KtormObject.FollowTable.userId]!!.toLong()))
                add(" ")
            }
            count++
            if (count == 3) {
                mb.newLine()
                count = 0
            }
        }
        val groupId = Init.GROUP_ID.toLong()
        val data: ReturnData<RMessageReturnData>? = rawGroupMsg(groupId, mb.toString())
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