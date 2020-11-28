package akko.ddbot.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Auto-generated: 2020-11-21 21:4:3
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
class BotConfigData {
    @JsonProperty("group_id")
    var groupId = 0
    var secret: String? = null

    @JsonProperty("access_token")
    var accessToken: String? = null

    @JsonProperty("socket_port")
    var socketPort = 0

    @JsonProperty("post_port")
    var postPort = 0

    @JsonProperty("BAIDU_APP_ID")
    var baiduAppId: String? = null

    @JsonProperty("BAIDU_SECURITY_KEY")
    var baiduSecurityKey: String? = null

    @JsonProperty("lolicon_apikey")
    var loliconApikey: String? = null

    @JsonProperty("SauceNAO_API_KEY")
    var saucenaoApiKey: String? = null

    @JsonProperty("postgre_user")
    var postgreUser:String? = null

    @JsonProperty("postgre_passwd")
    var postgrePasswd:String? = null

    @JsonProperty("postgre_url")
    var postgreUrl:String? = null
}