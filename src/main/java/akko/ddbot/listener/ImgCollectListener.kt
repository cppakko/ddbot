package akko.ddbot.listener

import akko.ddbot.InitCheck
import akko.ddbot.sql.SQLFun
import akko.ddbot.task.SetuList
import akko.ddbot.utilities.copyFile
import akko.ddbot.utilities.getImg
import akko.ddbot.utilities.getMsg
import cc.moecraft.icq.event.EventHandler
import cc.moecraft.icq.event.IcqListener
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.utils.FileUtils
import net.coobird.thumbnailator.Thumbnails
import org.sqlite.SQLiteException
import java.io.File
import java.io.IOException
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
            if (data.sender.user_id != 395837251) {
                val pattern = Pattern.compile("(\\[CQ:image,file=)([a-z0-9.]*)")
                val matcher = pattern.matcher(data.message)
                matcher.find()
                var path = getImg(matcher.group(2)).data.file
                val pattern1 = Pattern.compile("([0-9a-z.]*)\$")
                val matcher1 = pattern1.matcher(path)
                matcher1.find()
                val fileName = matcher1.group(1)
                val newFile = File("data/images/img/$fileName")
                if (newFile.exists()) {
                    val rawFile = "data/images/img/$fileName"
                    try {
                        val tuple = SQLFun().executeQuery("ImgCollect", "SELECT picture_id from ImgInfo where raw_file='$rawFile';")
                        val pictureId = tuple!!.resultSet.getInt(1)
                        tuple.connection.close()
                        val time = Date().time / 1000L
                        SQLFun().execute("ImgCollect", "INSERT INTO CollectInfo VALUES (${event.senderId},'$pictureId','$time');")
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                    event.respond("添加成功 yattaze")
                } else {
                    try {
                        path = "data/cache/$fileName"
                        FileUtils.copy(File(path), newFile)
                        path = "data/images/img/$fileName"
                    } catch (e: IOException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                    val thumbnailsPath = "data/images/img_thumbnails/" + fileName + "_thumbnail" + ".jpg"
                    Thumbnails.of(path).size(800, 800).toFile(thumbnailsPath)
                    try {
                        SQLFun().execute("ImgCollect", "INSERT INTO ImgInfo (raw_file,thumbnail) VALUES ('$path','$thumbnailsPath');")
                        InitCheck.MAX_PICTURE_ID++
                        val time = Date().time / 1000L
                        SQLFun().execute("ImgCollect", "INSERT INTO CollectInfo VALUES (${event.senderId},'${InitCheck.MAX_PICTURE_ID}','$time');")
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return
                    }
                    event.respond("添加成功 yattaze")
                }
            } else {
                val path = "data/images" + SetuList().find(data.message_id)
                val pattern = Pattern.compile("(/setu_img/)([0-9_a-z]*)")
                val matcher = pattern.matcher(path)
                matcher.find()
                val thumbnailsPath = "data/images/img_thumbnails/" + matcher.group(2) + "_thumbnail" + ".jpg"
                if (File(thumbnailsPath).exists()) {
                    try {
                        val tuple = SQLFun().executeQuery("ImgCollect", "SELECT picture_id from ImgInfo where thumbnail='$thumbnailsPath';")
                        val pictureId = tuple!!.resultSet.getInt(1)
                        tuple.connection.close()
                        val time = Date().time / 1000L
                        SQLFun().execute("ImgCollect", "INSERT INTO CollectInfo VALUES (${event.senderId},'$pictureId','$time');")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return;
                    } catch (e: SQLiteException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return;
                    }
                } else {
                    try {
                        Thumbnails.of(path).size(800, 800).toFile(thumbnailsPath)
                        SQLFun().execute("ImgCollect", "INSERT INTO ImgInfo (raw_file,thumbnail) VALUES ('$path','$thumbnailsPath');")
                        InitCheck.MAX_PICTURE_ID++
                        val time = Date().time / 1000L
                        SQLFun().execute("ImgCollect", "INSERT INTO CollectInfo VALUES (${event.senderId},'${InitCheck.MAX_PICTURE_ID}','$time');")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return;
                    } catch (e: SQLiteException) {
                        e.printStackTrace()
                        event.respond("失敗した失敗した失敗した失敗した失敗した失敗した")
                        return;
                    }
                    event.respond("添加成功 yattaze")
                }
            }
        }
    }
}