package akko.ddbot;

import akko.ddbot.command.AddListener;
import akko.ddbot.command.AddLiver;
import akko.ddbot.command.GroupSqlInit;
import akko.ddbot.command.HelpCommand;
import akko.ddbot.command.RemoveListener;
import akko.ddbot.command.SqlRetrunMessageTest;
import akko.ddbot.command.VersionTestCommand;
import akko.ddbot.command.vLiverFinder;
import akko.ddbot.task.LiveRoomTask;
import akko.ddbot.task.LiverInit;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;

public class BotMainActivity {
    public static PicqBotX bot;
    public static void main(String[] args) {
        PicqConfig mainConfig = new PicqConfig(8080).setDebug(true);
        mainConfig.setSecret("C{$Q-N'8R6gN.8zk");
        mainConfig.setAccessToken("b*}{UM22xk3~kUDB");
        PicqBotX mainBot = new PicqBotX(mainConfig);
        bot = mainBot;
        mainBot.addAccount("mainBot","0.0.0.0",5700);
        mainBot.enableCommandManager("!","bot -");

        mainBot.getEventManager().registerListeners(

        );
        mainBot.getCommandManager().registerCommands(
            new VersionTestCommand(),
            new GroupSqlInit(),
            new SqlRetrunMessageTest(),
            new HelpCommand(),
            new AddListener(),
            new RemoveListener(),
            new vLiverFinder(),
            new AddLiver()
        );

        mainBot.startBot();
        new LiverInit().init();
        new LiveRoomTask().run();
    }
}
