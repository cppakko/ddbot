package akko.ddbot.command

import akko.ddbot.InitCheck
import akko.ddbot.data.LoliconApi.LoliconApiDataClass
import akko.ddbot.network.LoliconApiNetwork
import akko.ddbot.sql.SQLFun
import akko.ddbot.utilities.GlobalObject
import akko.ddbot.utilities.groupMsg
import akko.ddbot.utilities.rawGroupMsg
import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.sender.returndata.ReturnStatus
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import net.coobird.thumbnailator.Thumbnails
import okhttp3.*
import org.hydev.logger.HyLogger
import retrofit2.Call
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class SetuCommand : GroupCommand {
    override fun groupMessage(eventGroupMessage: EventGroupMessage, groupUser: GroupUser, group: Group, s: String, arrayList: ArrayList<String>): String {
        val retrofit = Retrofit.Builder().baseUrl("https://api.lolicon.app/").build()
        val call: Call<ResponseBody?>? = retrofit.create(LoliconApiNetwork::class.java)[InitCheck.LOLICON_APIKEY, "true"]
        try {
            val body: String
            val responsebody = call?.execute()?.body()
            body = if (responsebody != null) {
                responsebody.string()
            } else {
                return "Lolicon网络有点问题(确信"
            }
            HyLogger("SetuCommand").debug(body)
            val url = GlobalObject.objectMapper.readValue(body, LoliconApiDataClass::class.java).data!![0].url
            if (url != null) { getTask(url,group) } else { return "Lolicon网络有点问题(确信" }
        } catch (e: IOException) {
            e.printStackTrace()
            return e.message!!
        }
        return ""
    }

    override fun properties(): CommandProperties {
        return CommandProperties("setu")
    }
}

private fun getTask(url: String,group: Group)
{
    val filePathMatcher = Pattern.compile("[0-9a-z._]*\$").matcher(url)
    filePathMatcher.find()
    val fileName = filePathMatcher.group(0)
    val filePath = "data/images/setu_img/$fileName"
    val imgFile = File(filePath)
    if (imgFile.exists()) return onResponse("setu_img/$fileName","/img_thumbnails/" + fileName + "_thumbanil.jpg", group.id,fileName)
    else
    {
        val client = OkHttpClient.Builder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                onFailure(e,group.id)
            }
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val inputStream = response.body()!!.byteStream()
                val buffer = ByteArray(1024 * 4)
                val fos: FileOutputStream?
                var len: Int
                val off = 0
                try {
                    fos = FileOutputStream(imgFile)
                    while (inputStream.read(buffer).apply { len = this } > 0) {
                        fos.write(buffer, off, len)
                    }
                    fos.flush()
                    fos.close()
                }
                catch (e: IOException) { onFailure(e, group.id) }
                onResponse("data/images/setu_img/$fileName", "data/images/img_thumbnails/" + fileName + "_thumbanil.jpg",group.id,fileName)
            }
        })
    }
}

private fun onFailure(e: IOException,group_id: Long)
{
    groupMsg(group_id,e.message!!)
    groupMsg(group_id,MessageBuilder().add(ComponentImage("amamiya_err.jpg")).toString())
}

private fun onResponse(filePath: String,thumbnail_path: String,group_id: Long,fileName: String)
{
    if (!File(thumbnail_path).exists())
    {
        Thumbnails.of(filePath).size(800,800).toFile(thumbnail_path)
    }
    val retrunData = rawGroupMsg(group_id,MessageBuilder().add(ComponentImage("img_thumbnails/" + fileName + "_thumbanil.jpg")).toString())!!
    val messageId = retrunData.data.messageId
    val status = retrunData.status
    if (status != ReturnStatus.ok)
    {
        groupMsg(group_id,MessageBuilder().add(ComponentImage("amamiya_err.jpg")).toString())
    }
    else
    {
        SQLFun().execute("bot","INSERT INTO setulist.list VALUES('$messageId','$filePath','$thumbnail_path',${(Date().time / 1000L)});")
        val t = SQLFun().executeQuery("bot","SELECT count(*) from setulist.list;")
        val set = t!!.resultSet
        if (set.getInt(1) > 10)
        {
            SQLFun().execute("bot","DELETE FROM setulist.list WHERE message_id = (SELECT message_id from setulist.list WHERE sendtime = (SELECT MIN(sendtime) from setulist.list));")
        }
        t.connection.close()
    }
}