package akko.ddbot.command;

import akko.ddbot.data.LoliconApi.LoliconApiDataClass;
import akko.ddbot.network.LoliconApiNetwork;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;

public class SetuCommand implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.lolicon.app/").build();
        Call<ResponseBody> call = retrofit.create(LoliconApiNetwork.class).get("677702765eb0ec8c4d4830","true");
        ObjectMapper om = new ObjectMapper();
        try {
            String body;
            ResponseBody responsebody = call.execute().body();
            if (responsebody != null) { body = responsebody.string(); }
            else { return "Lolicon网络有点问题(确信"; }
            System.out.println(body);
            String url = om.readValue(body, LoliconApiDataClass.class).getData().get(0).getUrl();
            return new MessageBuilder().add(new ComponentImage(url)).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("setu");
    }
}
