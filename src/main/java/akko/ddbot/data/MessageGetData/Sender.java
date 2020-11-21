package akko.ddbot.data.MessageGetData;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-11-20 23:35:25
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Sender {

    private String nickname;
    @JsonProperty("user_id")
    private int userId;
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

}