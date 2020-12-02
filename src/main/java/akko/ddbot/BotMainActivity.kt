package akko.ddbot

import akko.ddbot.command.*
import akko.ddbot.listener.ImgCollectListener
import akko.ddbot.listener.ImgSeachListener
import akko.ddbot.listener.TranslateListener
import akko.ddbot.task.LiveRoomTask
import akko.ddbot.task.LiverInit
import cc.moecraft.icq.PicqBotX
import cc.moecraft.icq.PicqConfig
import cc.moecraft.logger.HyLogger
import cc.moecraft.logger.LoggerInstanceManager
import cc.moecraft.logger.environments.FileEnv
import org.hydev.logger.HyLoggerConfig

class BotMainActivity {
    companion object {
        var bot: PicqBotX? = null
        var NLogger:HyLogger? = null
        var SQLLogger:HyLogger? = null
        var ExceptionLogger:HyLogger? = null
        //MAIN
        @JvmStatic
        fun main(args: Array<String>) {
            //INIT
            Init.installCheck()
            HyLoggerConfig.debug = true

            // 日志管理器
            val loggerInstanceManager = LoggerInstanceManager()
            loggerInstanceManager.addEnvironment(FileEnv("logs", "ddbot"))
            NLogger = loggerInstanceManager.getLoggerInstance("LiveRoomTask",true)
            SQLLogger = loggerInstanceManager.getLoggerInstance("SQL",true)
            ExceptionLogger = loggerInstanceManager.getLoggerInstance("Exception",true)
            //INIT END
            val mainConfig = PicqConfig(Init.SOCKET_PORT).run {
                isDebug = true
                isUseAsyncCommands = true
                isMultiAccountOptimizations = false
                setApiAsync(true)
            }
            mainConfig.secret = Init.SECRET
            mainConfig.accessToken = Init.ACCESS_TOKEN
            val mainBot = PicqBotX(mainConfig)
            mainBot.addAccount("mainBot", "0.0.0.0", Init.POST_PORT)
            mainBot.enableCommandManager("!", "bot -")

            mainBot.eventManager.registerListeners(
                    TranslateListener(),
                    ImgSeachListener(),
                    ImgCollectListener()
            )
            mainBot.commandManager.registerCommands(
                    AddListener(),
                    AddLiver(),
                    DiceMan(),
                    HelpCommand(),
                    RemoveListener(),
                    SetuCommand(),
                    vLiverFinder(),
                    TestCommand(),
                    LogInCommand()
            )
            bot = mainBot
            LiverInit.init()
            mainBot.startBot()
            LiveRoomTask.run()
        }
    }
}

