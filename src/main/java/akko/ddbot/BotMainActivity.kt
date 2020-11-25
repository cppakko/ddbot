package akko.ddbot

import akko.ddbot.command.*
import akko.ddbot.listener.ImgCollectListener
import akko.ddbot.listener.ImgSeachListener
import akko.ddbot.listener.TranslateListener
import akko.ddbot.task.LiveRoomTask
import akko.ddbot.task.LiverInit
import cc.moecraft.icq.PicqBotX
import cc.moecraft.icq.PicqConfig

class BotMainActivity {
    companion object {
        var bot: PicqBotX? = null
        //MAIN
        @JvmStatic
        fun main(args: Array<String>) {
            //INIT
            InitCheck.installCheck()
            //INIT END
            val mainConfig = PicqConfig(InitCheck.SOCKET_PORT).setDebug(true)
            mainConfig.secret = InitCheck.SECRET
            mainConfig.accessToken = InitCheck.ACCESS_TOKEN
            val mainBot = PicqBotX(mainConfig)
            mainBot.addAccount("mainBot", "0.0.0.0", InitCheck.POST_PORT)
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
                    isAlive(),
                    RemoveListener(),
                    SetuCommand(),
                    vLiverFinder(),
                    TestCommand()
            )
            bot = mainBot
            LiverInit.init()
            mainBot.startBot()
            LiveRoomTask.run()
        }
    }
}

