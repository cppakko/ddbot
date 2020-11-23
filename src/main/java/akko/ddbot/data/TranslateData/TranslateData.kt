package akko.ddbot.data.TranslateData

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Auto-generated: 2020-11-21 0:18:50
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
class TranslateData {
    var from: String? = null
    var to: String? = null

    @JsonProperty("trans_result")
    var transResult: List<TransResult>? = null
}