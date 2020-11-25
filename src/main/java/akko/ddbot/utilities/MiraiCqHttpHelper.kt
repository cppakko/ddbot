package akko.ddbot.utilities

import akko.ddbot.BotMainActivity
import akko.ddbot.InitCheck
import akko.ddbot.data.CQImageData
import akko.ddbot.data.MessageGetData.Data
import akko.ddbot.data.MessageGetData.MesGetData
import akko.ddbot.data.OCRdata.OCRdata
import cc.moecraft.icq.sender.returndata.ReturnData
import cc.moecraft.icq.sender.returndata.ReturnStatus
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request

//发送群组消息
fun GroupMsg(group_id: Long,msg: String): ReturnStatus? { return BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(group_id,msg).status }
fun RawGroupMsg(group_id: Long,msg: String): ReturnData<RMessageReturnData>? { return BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(group_id,msg) }
/**
 * @param msg_id CQHTTP消息ID
 */
fun getMsg(msg_id:String): Data
{
    val url = "http://0.0.0.0:5700/get_msg?message_id=" + msg_id + "&access_token=" + InitCheck.ACCESS_TOKEN
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val call = client.newCall(request)
    val body = call.execute().body()!!.string()
    return GlobalObject.objectMapper.readValue(body, MesGetData::class.java).data!!
}
/**
 * @param imgFile 文件相对 {go-cqhttp安装目录}/data/images/下的文件名
 */
fun ocrFun(imgFile: String): OCRdata {
    val client = OkHttpClient()
    val ocrUrl = "http://0.0.0.0:" + InitCheck.POST_PORT.toString() + "/.ocr_image?image=" + imgFile + "&access_token=" + InitCheck.ACCESS_TOKEN
    val requestOcr = Request.Builder().url(ocrUrl).build()
    val responseBodyCall = client.newCall(requestOcr)
    val jsBody = responseBodyCall.execute().body()!!.string()
    return GlobalObject.objectMapper.readValue(jsBody, OCRdata::class.java)!!
}

fun getImg(image_id: String): CQImageData
{
    val url = "http://0.0.0.0:" + InitCheck.POST_PORT.toString() + "/get_image?file=" + image_id + "&access_token=" + InitCheck.ACCESS_TOKEN
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val call = client.newCall(request)
    val body = call.execute().body()!!.string()
    return jacksonObjectMapper().readValue<CQImageData>(body)
}