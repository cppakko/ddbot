package akko.ddbot.task;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import akko.ddbot.sql.SQLFun;
import com.fasterxml.jackson.databind.ObjectMapper;

import akko.ddbot.network.BilibiliNetworkService;
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
                    Call<ResponseBody> call = retrofit.create(BilibiliNetworkService.class).getDatCall(vID);
                    StringReader reader = new StringReader(call.execute().body().string());
                    Data data = oMapper.readValue(reader, BilibiliDataClass.class).getData();
                    Live_room liveRoomData = data.getLive_room();
                    int statusRightNow = liveRoomData.getLiveStatus();
                    System.out.println(vID);
                    int statusindb = sqliteC.prepareStatement("select * from  vLiver WHERE vID = '" + vID + "';").executeQuery().getInt("vSTATE");

                    if (statusRightNow == 1 && statusindb == 0)
                    {
                        sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 1 WHERE vID = " + vID + ";").execute();
                        new RemindListener().RemindListenerFun(vID,data.getName(),liveRoomData.getTitle(),liveRoomData.getUrl());;
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
