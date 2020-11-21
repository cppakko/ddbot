package akko.ddbot.data.LoliconApi;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
/**
 * Auto-generated: 2020-11-20 0:23:12
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class LoliconApiDataClass {

    private int code;
    private String msg;
    private int quota;
    @JsonProperty("quota_min_ttl")
    private int quotaMinTtl;
    private int count;
    private List<Data> data;
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public int getQuota() {
        return quota;
    }

    public int getQuotaMinTtl() {
        return quotaMinTtl;
    }

    public int getCount() {
        return count;
    }

    public List<Data> getData() {
        return data;
    }

}

