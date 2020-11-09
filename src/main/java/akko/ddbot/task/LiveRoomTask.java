package akko.ddbot.task;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import akko.ddbot.network.BilibiliNetworkService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class LiveRoomTask implements Runnable {

    @Override
    public void run() {
        ObjectMapper oMapper = new ObjectMapper();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.bilibili.com/x/space/acc/").build();
        try {
            while (true) {
                new LiverInit();
                List<String> LiverList = LiverInit.liverList;
                Class.forName("org.sqlite.JDBC");
                Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/GroupInfo.db");
                for (String vID : LiverList) {
                    Call<ResponseBody> call = retrofit.create(BilibiliNetworkService.class).getDatCall(vID);
                    
                    StringReader reader = new StringReader(call.execute().body().string());
                    Data data = oMapper.readValue(reader, BilibiliDataClass.class).getData();
                    Live_room LiveRoomData = data.getLive_room();
                    int StatusRightNow = LiveRoomData.getLiveStatus();
                    System.out.println(vID);
                    int StatusInDB = sqliteC.prepareStatement("select * from  vLiver WHERE vID = \'" + vID + "\';").executeQuery().getInt("vSTATE");
                    if (StatusRightNow == 1 && StatusInDB == 0)
                    {
                        sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 1 WHERE vID = " + vID + ";").execute();
                        new RemindListener().RemindListenerFun(vID,data.getName(),LiveRoomData.getTitle(),LiveRoomData.getUrl());;
                    }
                    else if (StatusRightNow == 0 && StatusInDB == 1)
                    {
                        sqliteC.prepareStatement("UPDATE vLiver SET vSTATE = 0 WHERE vID = " + vID + ";").execute();
                    }
                }
                sqliteC.close();
                Thread.sleep(60000);
            }
        } 
        catch (ClassNotFoundException | SQLException | InterruptedException | IOException e) 
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
}
