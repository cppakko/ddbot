package akko.ddbot.data.OCRdata;
import java.util.List;

/**
 * Auto-generated: 2020-11-20 23:38:23
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Texts {

    private String text;
    private int confidence;
    private List<Coordinates> coordinates;
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
    public int getConfidence() {
        return confidence;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }
    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

}