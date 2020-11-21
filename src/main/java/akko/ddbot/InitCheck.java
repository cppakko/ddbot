package akko.ddbot;

import akko.ddbot.data.BotConfigData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class InitCheck {
    public static int GROUP_ID;
    public static String ACCESS_TOKEN;
    public static String SECRET;
    public static int SOCKET_PORT;
    public static int POST_PORT;
    public static String BAIDU_APP_ID;
    public static String BAIDU_SECURITY_KEY;
    public static String LOLICON_APIKEY;
    static void installCheck()
    {
        File isInstalled = new File("isInstalled");
        File botXconfig = new File("botXconfig.json");
        ObjectMapper om = new ObjectMapper();
        if (!isInstalled.exists()) {
            try {
                isInstalled.createNewFile();
                botXconfig.createNewFile();
                BotConfigData botConfigData = new BotConfigData();
                botConfigData.setAccessToken("");
                botConfigData.setSecret("");
                botConfigData.setBaiduAppId("");
                botConfigData.setBaiduSecurityKey("");
                botConfigData.setLoliconApikey("");

                om.writeValue(botXconfig,botConfigData);
                System.out.println("请对botXconfig.json完成修改后重启 别填错了别填错了别填错了");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                BotConfigData botConfigData = om.readValue(botXconfig,BotConfigData.class);
                GROUP_ID = botConfigData.getGroupId();
                ACCESS_TOKEN = botConfigData.getAccessToken();
                SECRET = botConfigData.getSecret();
                SOCKET_PORT = botConfigData.getSocketPort();
                POST_PORT = botConfigData.getPostPort();
                BAIDU_APP_ID = botConfigData.getBaiduAppId();
                BAIDU_SECURITY_KEY = botConfigData.getBaiduSecurityKey();
                LOLICON_APIKEY = botConfigData.getLoliconApikey();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
