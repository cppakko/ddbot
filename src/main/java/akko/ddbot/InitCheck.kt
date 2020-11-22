package akko.ddbot

import akko.ddbot.data.BotConfigData
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

object InitCheck {
    var GROUP_ID = 0
    var ACCESS_TOKEN: String? = null
    var SECRET: String? = null
    var SOCKET_PORT = 0
    var POST_PORT = 0
    var BAIDU_APP_ID: String? = null
    var BAIDU_SECURITY_KEY: String? = null
    var LOLICON_APIKEY: String? = null
    var SAUCENAO_API_KEY: String? = null

    fun installCheck() {
        val isInstalled = File("isInstalled")
        val botXconfig = File("botXconfig.json")
        val om = ObjectMapper()
        if (!isInstalled.exists()) {
            try {
                isInstalled.createNewFile()
                botXconfig.createNewFile()
                val botConfigData = BotConfigData()
                botConfigData.accessToken = ""
                botConfigData.secret = ""
                botConfigData.baiduAppId = ""
                botConfigData.baiduSecurityKey = ""
                botConfigData.loliconApikey = ""
                om.writeValue(botXconfig, botConfigData)
                println("请对botXconfig.json完成修改后重启 别填错了别填错了别填错了")
                exitProcess(0)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            try {
                val botConfigData = om.readValue(botXconfig, BotConfigData::class.java)
                GROUP_ID = botConfigData.groupId
                ACCESS_TOKEN = botConfigData.accessToken
                SECRET = botConfigData.secret
                SOCKET_PORT = botConfigData.socketPort
                POST_PORT = botConfigData.postPort
                BAIDU_APP_ID = botConfigData.baiduAppId
                BAIDU_SECURITY_KEY = botConfigData.baiduSecurityKey
                LOLICON_APIKEY = botConfigData.loliconApikey
                SAUCENAO_API_KEY = botConfigData.saucenaoApiKey
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}