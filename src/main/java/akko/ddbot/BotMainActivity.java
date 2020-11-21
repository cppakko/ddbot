package akko.ddbot;

import akko.ddbot.command.*;
import akko.ddbot.listener.TranslateListener;
import akko.ddbot.task.LiveRoomTask;
import akko.ddbot.task.LiverInit;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;

import java.io.File;
import java.io.IOException;

public class BotMainActivity {
    public static PicqBotX bot;
    public static void main(String[] args) {
        // INIT
        InitCheck.installCheck();
        //INIT END
        PicqConfig mainConfig = new PicqConfig(InitCheck.SOCKET_PORT).setDebug(true);
        mainConfig.setSecret(InitCheck.SECRET);
        mainConfig.setAccessToken(InitCheck.ACCESS_TOKEN);
        PicqBotX mainBot = new PicqBotX(mainConfig);
        bot = mainBot;
        mainBot.addAccount("mainBot","0.0.0.0",InitCheck.POST_PORT);
        mainBot.enableCommandManager("!","bot -");

        mainBot.getEventManager().registerListeners(
                new TranslateListener()
        );
        mainBot.getCommandManager().registerCommands(
                new VersionTestCommand(),
                new GroupSqlInit(),
                new HelpCommand(),
                new AddListener(),
                new RemoveListener(),
                new vLiverFinder(),
                new AddLiver(),
                new IsAlive(),
                new RebootCommand(),
                new SetuCommand(),
                new DiceMan()
        );

        mainBot.startBot();
        new LiverInit().init();
        new LiveRoomTask().run();
    }
}
