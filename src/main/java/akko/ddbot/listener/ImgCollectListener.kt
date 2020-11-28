package akko.ddbot.listener

import akko.ddbot.InitCheck
import akko.ddbot.sql.SQLFun
import akko.ddbot.utilities.PatternHelper
import akko.ddbot.utilities.getImg
import akko.ddbot.utilities.getMsg
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.utils.FileUtils
import net.coobird.thumbnailator.Thumbnails
import org.sqlite.SQLiteException
import java.io.File
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.regex.Pattern

class ImgCollectListener : IcqListener() {
    @EventHandler
    fun listener(event: EventGroupMessage) {
        val pattern = Pattern.compile("(\\[CQ:reply,id=([-0-9]*))*([ a-zA-Z0-9,=:\\[\\]])*(这个好)")
        val matcher = pattern.matcher(event.message)
        if (matcher.find() && matcher.group(4) == "这个好") {
            val data = getMsg(matcher.group(2))
            val bot_id: Long = 395837251
            //判断是否为机器人发送的图片
            if (data.sender.user_id != bot_id) {
                // [CQ:image,file=6c5b4573d8928ed872fbff5d76b304ab.image] <- 正则例子
                var path = getImg(PatternHelper().regexHelper("(\\[CQ:image,file=)([a-z0-9.]*)",data.message).group(2)).data.file
                //6c5b4573d8928ed872fbff5d76b304ab.image
                val fileName = PatternHelper().regexHelper("([0-9a-z.]*)\$",path).group(1)
                val newFile = File("data/images/img/$fileName")
                if (newFile.exists()) {
                    val rawFile = "data/images/img/$fileName"
                    try {
                        val tuple = SQLFun().executeQuery("bot", "SELECT picture_id from imgcollect.imginfo where raw_file='$rawFile';")
                        val pictureId = tuple!!.resultSet.getInt(1)
                        tuple.connection.close()
                        val time = Date().time / 1000L
                        SQLFun().execute("bot", "INSERT INTO imgcollect.collectinfo VALUES (${event.senderId},'$pictureId','$time');")
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                    event.respond("添加成功 yattaze")
                } else {
                    path = "data/cache/$fileName"
                    //复制图片 持久保存
                    FileUtils.copy(File(path), newFile)
                    path = "data/images/img/$fileName"
                    val thumbnailsPath = "data/images/img_thumbnails/" + fileName + "_thumbnail" + ".jpg"
                    Thumbnails.of(path).size(800, 800).toFile(thumbnailsPath)
                    try {
                        SQLFun().execute("bot", "INSERT INTO imgcollect.imginfo (raw_file,thumbnail) VALUES ('$path','$thumbnailsPath');")
                        InitCheck.MAX_PICTURE_ID++
                        val time = Date().time / 1000L
                        SQLFun().execute("bot", "INSERT INTO imgcollect.collectinfo VALUES (${event.senderId},'${InitCheck.MAX_PICTURE_ID}','$time');")
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                    event.respond("添加成功 yattaze")
                }
            } else {
                val t = SQLFun().executeQuery("bot","SELECT filepath from setulist.list where message_id = '${data.message_id}';")
                val path = t!!.resultSet.getString(1)
                //jn123jn4_p0.jpg
                val thumbnailsPath = "data/images/img_thumbnails/" + PatternHelper().regexHelper("(/setu_img/)([0-9_a-z]*)",path).group(2) + "_thumbnail" + ".jpg"
                println(thumbnailsPath)
                Class.forName("org.postgresql.Driver")
                val sqliteC = DriverManager.getConnection("jdbc:postgresql://${InitCheck.POSTGRE_URL}/bot",InitCheck.POSTGRE_USER,InitCheck.POSTGRE_PASSWD)
                val res = sqliteC.prepareStatement("select picture_id from imgcollect.imginfo where thumbnail = '$thumbnailsPath';").executeQuery()
                if (res.next()) {
                    sqliteC.close()
                    try {
                        val tuple = SQLFun().executeQuery("bot", "SELECT imgcollect.picture_id from imginfo where thumbnail='$thumbnailsPath';")
                        val pictureId = tuple!!.resultSet.getInt(1)
                        tuple.connection.close()
                        val time = Date().time / 1000L
                        SQLFun().execute("bot", "INSERT INTO imgcollect.collectinfo VALUES (${event.senderId},'$pictureId','$time');")
                    }
                    catch (e: SQLiteException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                } else {
                    try {
                        sqliteC.close()
                        println(6)
                        SQLFun().execute("bot", "INSERT INTO imgcollect.imginfo (raw_file,thumbnail) VALUES ('$path','$thumbnailsPath');")
                        InitCheck.MAX_PICTURE_ID++
                        val time = Date().time / 1000L
                        println(7)
                        SQLFun().execute("bot", "INSERT INTO imgcollect.collectinfo VALUES (${event.senderId},'${InitCheck.MAX_PICTURE_ID}','$time');")
                    } catch (e: SQLiteException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                    event.respond("添加成功 yattaze")
                }
            }
        }
    }
}