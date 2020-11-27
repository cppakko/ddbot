package akko.ddbot.task

import akko.ddbot.InitCheck
import akko.ddbot.sql.SQLFun
import akko.ddbot.sql.TwoTuple
import akko.ddbot.utilities.groupMsg
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentAt
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.sender.returndata.ReturnStatus
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException

class RemindListener {
    fun remindListenerFun(cover: String?, vID: String, vNAME: String, title: String?, url: String?) {
        try {
            val tuple: TwoTuple<ResultSet?, Connection?>? = SQLFun().executeQuery("ListenerInfo", "select * from  V$vID;")
            val res: ResultSet = tuple!!.resultSet
            val mb = MessageBuilder()
            var count = 0
            mb.run {
                add("$vNAME 开播力~").newLine()
                add("---------------").newLine()
                add(ComponentImage(cover))
                add(title).newLine()
                add(url).newLine()
                add("---------------").newLine()
            }
            while (res.next()) {
                mb.run {
                    add(ComponentAt(res.getString("ID").toLong()))
                    add(" ")
                }
                count++
                if (count == 3) {
                    mb.newLine()
                    count = 0
                }
            }
            tuple.connection.close()
            val groupId = InitCheck.GROUP_ID.toLong()
            var rStatus = groupMsg(groupId, mb.toString())
            var retryCount = 0
            while (rStatus != ReturnStatus.ok && retryCount <= 5) {
                if (retryCount == 5) {
                    groupMsg(groupId, "重试了五次也没发出来  饶了我吧(哭")
                    groupMsg(InitCheck.GROUP_ID.toLong(),MessageBuilder().add(ComponentImage("amamiya_err.jpg")).toString())
                    break
                }
                rStatus = groupMsg(groupId, mb.toString())
                retryCount++
            }
        } catch (e: SQLException) {
            println(e.message)
            e.printStackTrace()
        }
    }
}