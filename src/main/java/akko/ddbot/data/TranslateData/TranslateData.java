package akko.ddbot.data.TranslateData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2020-11-21 0:18:50
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class TranslateData {

    private String from;
    private String to;
    @JsonProperty("trans_result")
    private List<TransResult> transResult;
    public void setFrom(String from) {
        this.from = from;
    }
    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getTo() {
        return to;
    }

    public void setTransResult(List<TransResult> transResult) {
        this.transResult = transResult;
    }
    public List<TransResult> getTransResult() {
        return transResult;
    }

}