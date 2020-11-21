package akko.ddbot.data.MessageGetData;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-11-20 23:35:25
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Data {

    private boolean group;
    private String message;
    @JsonProperty("message_id")
    private int messageId;
    @JsonProperty("real_id")
    private int realId;
    private Sender sender;
    private int time;
    public void setGroup(boolean group) {
        this.group = group;
    }
    public boolean getGroup() {
        return group;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public int getMessageId() {
        return messageId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }
    public int getRealId() {
        return realId;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
    public Sender getSender() {
        return sender;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public int getTime() {
        return time;
    }

}