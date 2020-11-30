package akko.ddbot.utilities

import akko.ddbot.BotMainActivity
import akko.ddbot.Init
import akko.ddbot.data.CQImageData
import akko.ddbot.data.GetMsgData
import akko.ddbot.data.MsgData
import akko.ddbot.data.OCRdata.OCRdata
import cc.moecraft.icq.sender.returndata.ReturnData
import cc.moecraft.icq.sender.returndata.ReturnStatus
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request

//发送群组消息
fun groupMsg(group_id: Long, msg: String): ReturnStatus? { return BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(group_id,msg).status }
fun rawGroupMsg(group_id: Long, msg: String): ReturnData<RMessageReturnData>? { return BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(group_id,msg) }
/**
 * @param msg_id CQHTTP消息ID
 */
fun getMsg(msg_id:String): MsgData
{
    val url = "http://0.0.0.0:5700/get_msg?message_id=" + msg_id + "&access_token=" + Init.ACCESS_TOKEN
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val call = client.newCall(request)
    val body = call.execute().body()!!.string()
    return GlobalObject.jacksonObjectMapper.readValue<GetMsgData>(body).data
}
/**
 * @param imgFile 文件相对 {go-cqhttp安装目录}/data/images/下的文件名
 */
fun ocrFun(imgFile: String): OCRdata {
    val client = OkHttpClient()
    val ocrUrl = "http://0.0.0.0:" + Init.POST_PORT.toString() + "/.ocr_image?image=" + imgFile + "&access_token=" + Init.ACCESS_TOKEN
    val requestOcr = Request.Builder().url(ocrUrl).build()
    val responseBodyCall = client.newCall(requestOcr)
    val jsBody = responseBodyCall.execute().body()!!.string()
    //TODO F D
    return GlobalObject.objectMapper.readValue(jsBody, OCRdata::class.java)!!
}


/*{
    "data": {
    "file": "data/cache/6c5b4573d8928ed872fbff5d76b304ab.image..jpg",
    "filename": "{6C5B4573-D892-8ED8-72FB-FF5D76B304AB}.jpg",
    "size": 67405,
    "url": "http://qq.com"
},
    "retcode": 0,
    "status": "ok"
}*/

fun getImg(image_id: String): CQImageData
{
    val url = "http://0.0.0.0:" + Init.POST_PORT.toString() + "/get_image?file=" + image_id + "&access_token=" + Init.ACCESS_TOKEN
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val call = client.newCall(request)
    val body = call.execute().body()!!.string()
    return jacksonObjectMapper().readValue<CQImageData>(body)
}