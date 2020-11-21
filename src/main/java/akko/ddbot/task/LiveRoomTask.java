package akko.ddbot.task;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import akko.ddbot.BotMainActivity;
import akko.ddbot.InitCheck;
import akko.ddbot.data.BilibiliApi.BilibiliDataClass;
import akko.ddbot.data.BilibiliApi.Data;
import akko.ddbot.data.BilibiliApi.Live_room;
import akko.ddbot.sql.SQLFun;
import com.fasterxml.jackson.databind.ObjectMapper;

import akko.ddbot.network.BilibiliApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class LiveRoomTask implements Runnable {

    @Override
    public void run() {
        ObjectMapper oMapper = new ObjectMapper();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.bilibili.com/x/space/acc/").build();
        try {
            while (true)
            {
                new LiverInit();
                List<String> liverList = LiverInit.liverList;
                Connection sqliteC = new SQLFun().connection("GroupInfo");
                for (String vID : liverList) {
                    Call<ResponseBody> call = retrofit.create(BilibiliApiService.class).getDatCall(vID);
                    String body = null;
                    ResponseBody rawBody = call.execute().body();
                    if (rawBody != null) { body = rawBody.string(); }
                    else {
                        BotMainActivity.bot.getAccountManager().getNonAccountSpecifiedApi().sendGroupMsg(InitCheck.GROUP_ID,"b站网络出问题了(确信");
                        continue;
                    }
                    Data data = oMapper.readValue(body, BilibiliDataClass.class).getData();
                    Live_room liveRoomData = data.getLive_room();
                    int statusRightNow = liveRoomData.getLiveStatus();
                    System.out.println(vID);
                    int statusindb = sqliteC.prepareStatement("select * from  vLiver WHERE vID = '" + vID + "';").executeQuery().getInt("vSTATE");

                    if (statusRightNow == 1 && statusindb == 0)
                    {
                        sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 1 WHERE vID = " + vID + ";").execute();
                        new RemindListener().RemindListenerFun(liveRoomData.getCover(),vID,data.getName(),liveRoomData.getTitle(),liveRoomData.getUrl());;
                    }
                    else if (statusRightNow == 0 && statusindb == 1)
                    {
                        sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 0 WHERE vID = " + vID + ";").execute();
                    }
                }
                sqliteC.close();
                Thread.sleep(60000);
            }
        } 
        catch (SQLException | InterruptedException | IOException e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
}
