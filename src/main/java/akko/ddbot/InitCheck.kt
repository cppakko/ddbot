package akko.ddbot

import akko.ddbot.data.BotConfigData
import akko.ddbot.sql.SQLFun
import akko.ddbot.utilities.GlobalObject
import org.hydev.logger.HyLogger
import java.io.File
import java.io.IOException
import java.sql.DriverManager
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
    var MAX_PICTURE_ID: Long = 0
    var POSTGRE_USER:String? = null
    var POSTGRE_PASSWD:String? = null
    var POSTGRE_URL:String? = null
    fun installCheck() {
        val isInstalled = File(".isInstalled")
        val botXconfig = File("botXconfig.json")
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
                botConfigData.saucenaoApiKey = ""
                GlobalObject.objectMapper.writeValue(botXconfig, botConfigData)

                HyLogger("InstallCheck").log("请对botXconfig.json完成修改后重启 别填错了别填错了别填错了")
                exitProcess(0)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            try {
                val botConfigData = GlobalObject.objectMapper.readValue(botXconfig, BotConfigData::class.java)
                GROUP_ID = botConfigData.groupId
                ACCESS_TOKEN = botConfigData.accessToken
                SECRET = botConfigData.secret
                SOCKET_PORT = botConfigData.socketPort
                POST_PORT = botConfigData.postPort
                BAIDU_APP_ID = botConfigData.baiduAppId
                BAIDU_SECURITY_KEY = botConfigData.baiduSecurityKey
                LOLICON_APIKEY = botConfigData.loliconApikey
                SAUCENAO_API_KEY = botConfigData.saucenaoApiKey
                POSTGRE_PASSWD = botConfigData.postgrePasswd
                POSTGRE_URL = botConfigData.postgreUrl
                POSTGRE_USER = botConfigData.postgreUser

                MAX_PICTURE_ID = SQLFun().executeQuery("bot","select max(picture_id) from imgcollect.imginfo;")!!.resultSet.getLong(1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}