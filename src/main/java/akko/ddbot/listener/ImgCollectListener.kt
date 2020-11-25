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
import net.coobird.thumbnailator.Thumbnails
import java.io.File
import java.util.*
import java.util.regex.Pattern

class ImgCollectListener : IcqListener() {
    @EventHandler
    fun listener(event: EventGroupMessage)
    {
        val pattern = Pattern.compile("(\\[CQ:reply,id=([-0-9]*))*([ a-zA-Z0-9,=:\\[\\]])*(这个好)")
        val matcher =  pattern.matcher(event.message)
        if (matcher.find() && matcher.group(4) == "这个好")
        {
            val data = getMsg(matcher.group(2))
            if (data.sender!!.userId != 395837251)
            {
                val pattern = Pattern.compile("(\\[CQ:image,file=)([a-z0-9.]*)")
                val matcher = pattern.matcher(data.message)
                matcher.find()
                var path = getImg(matcher.group(2)).data.file
                val pattern1 = Pattern.compile("([0-9a-z.]*)\$")
                val matcher1 = pattern1.matcher(path)
                matcher1.find()
                val fileName = matcher1.group(1)
                val newFile = File("/data/img/$fileName")
                if (newFile.exists())
                {
                    val rawFile = "/data/img/$fileName"
                    val pictureId = SQLFun().executeQuery("ImgCollect","SELECT picture_id from ImgInfo where raw_file='$rawFile';")!!.resultSet.getInt(1)
                    val time = Date().time / 1000L
                    SQLFun().execute("ImgCollect","INSERT INTO CollectInfo VALUES (${event.senderId},'$pictureId','$time');")
                }
                else
                {
                    newFile.createNewFile()
                    path = "/data/img/$fileName"
                    copyFile(File(path),newFile)
                    val thumbnailsPath = "data/images/img_thumbnails/" + fileName + "_thumbnail" + ".jpg"
                    Thumbnails.of(path).size(800,800).toFile(thumbnailsPath)
                    SQLFun().execute("ImgCollect", "INSERT INTO ImgInfo (raw_file,thumbnail) VALUES ('$path','$thumbnailsPath');")
                    InitCheck.MAX_PICTURE_ID++
                    val time = Date().time / 1000L
                    SQLFun().execute("ImgCollect","INSERT INTO CollectInfo VALUES (${event.senderId},'${InitCheck.MAX_PICTURE_ID}','$time');")
                }
            }
            else
            {
                val path = SetuList().find(data.messageId)
                val pattern = Pattern.compile("(/setu_img/)([0-9_a-z]*)")
                val matcher = pattern.matcher(path)
                matcher.find()
                val thumbnailsPath = "data/images/img_thumbnails/" + matcher.group(2) + "_thumbnail" + ".jpg"
                if (File(thumbnailsPath).exists())
                {
                    val pictureId = SQLFun().executeQuery("ImgCollect","SELECT picture_id from ImgInfo where thumbnail='$thumbnailsPath';")!!.resultSet.getInt(1)
                    val time = Date().time / 1000L
                    SQLFun().execute("ImgCollect","INSERT INTO CollectInfo VALUES (${event.senderId},'$pictureId','$time');")
                }
                else
                {
                    Thumbnails.of(path).size(800,800).toFile(thumbnailsPath)
                    SQLFun().execute("ImgCollect", "INSERT INTO ImgInfo (raw_file,thumbnail) VALUES ('$path','$thumbnailsPath');")
                    InitCheck.MAX_PICTURE_ID++
                    val time = Date().time / 1000L
                    SQLFun().execute("ImgCollect","INSERT INTO CollectInfo VALUES (${event.senderId},'${InitCheck.MAX_PICTURE_ID}','$time');")
                }
            }
        }
    }
}