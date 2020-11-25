package akko.ddbot.data.MessageGetData

import com.fasterxml.jackson.annotation.JsonProperty
import akko.ddbot.data.MessageGetData.Sender

/**
 * Auto-generated: 2020-11-20 23:35:25
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
class Data {
    var group = false
    var message: String? = null

    @JsonProperty("message_id")
    var messageId: Long = 0

    @JsonProperty("real_id")
    var realId = 0
    var sender: Sender? = null
    var time = 0
}