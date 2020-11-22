package akko.ddbot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-11-21 21:4:3
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class BotConfigData {

    @JsonProperty("group_id")
    private int groupId;
    private String secret;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("socket_port")
    private int socketPort;
    @JsonProperty("post_port")
    private int postPort;
    @JsonProperty("BAIDU_APP_ID")
    private String baiduAppId;
    @JsonProperty("BAIDU_SECURITY_KEY")
    private String baiduSecurityKey;
    @JsonProperty("lolicon_apikey")
    private String loliconApikey;
    @JsonProperty("SauceNAO_API_KEY")
    private String saucenaoApiKey;
    public void setSaucenaoApiKey(String saucenaoApiKey) { this.saucenaoApiKey = saucenaoApiKey; }
    public String getSaucenaoApiKey() { return saucenaoApiKey; }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getGroupId() {
        return groupId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    public String getSecret() {
        return secret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }
    public int getSocketPort() {
        return socketPort;
    }

    public void setPostPort(int postPort) {
        this.postPort = postPort;
    }
    public int getPostPort() {
        return postPort;
    }

    public void setBaiduAppId(String baiduAppId) {
        this.baiduAppId = baiduAppId;
    }
    public String getBaiduAppId() {
        return baiduAppId;
    }

    public void setBaiduSecurityKey(String baiduSecurityKey) {
        this.baiduSecurityKey = baiduSecurityKey;
    }
    public String getBaiduSecurityKey() {
        return baiduSecurityKey;
    }

    public void setLoliconApikey(String loliconApikey) {
        this.loliconApikey = loliconApikey;
    }
    public String getLoliconApikey() {
        return loliconApikey;
    }

}