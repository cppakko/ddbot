package akko.ddbot.task;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;

import akko.ddbot.network.BilibiliNetworkService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class LiveRoomTask implements Runnable {

    @Override
    public void run() {
        ObjectMapper oMapper = new ObjectMapper();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.bilibili.com/x/space/acc/").build();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection sqliteC = DriverManager.getConnection("jdbc:sqlite:db/GroupInfo.db");
            while (true) {
                PreparedStatement sql = sqliteC.prepareStatement("select * from  vLiver;");
                ResultSet res = sql.executeQuery();
                while (res.next()) {
                    String vID = res.getString("vID");
                    Call<ResponseBody> call = retrofit.create(BilibiliNetworkService.class).getDatCall(vID);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Reader reader;
                            try {
                                reader = new StringReader(response.body().string());
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
                            catch (IOException | SQLException e) 
                            {
                                System.out.println(e.toString());
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) 
                        {
                            System.out.println(t.getMessage());
                        }
                        
                    });
                    Thread.sleep(60000);
                }
            }
        } 
        catch (ClassNotFoundException | SQLException | InterruptedException e) 
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
}
