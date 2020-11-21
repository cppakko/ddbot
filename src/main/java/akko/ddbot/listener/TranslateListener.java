package akko.ddbot.listener;

import akko.ddbot.data.MessageGetData.MesGetData;
import akko.ddbot.data.OCRdata.Data;
import akko.ddbot.data.OCRdata.OCRdata;
import akko.ddbot.data.OCRdata.Texts;
import akko.ddbot.data.TranslateData.TranslateData;
import akko.ddbot.task.translate.TransApi;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateListener extends IcqListener {

    @EventHandler
    public void groupTest(EventGroupMessage event)
    {
        Pattern pattern = Pattern.compile("(CQ:reply)(.+)(给我翻译翻译)");
        Matcher firstMatcher = pattern.matcher(event.message);
        if (firstMatcher.find() && "CQ:reply".equals(firstMatcher.group(1)) && "给我翻译翻译".equals(firstMatcher.group(3)))
        {
            String rawMessage = event.getRawMessage();
            String regex = "(id=)(-*)(\\d*)";
            pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(rawMessage);
            if (matcher.find())
            {
                if ("id=".equals(matcher.group(1)))
                {
                    final boolean isMinus = "-".equals(matcher.group(2));
                    final String message_id = (isMinus) ? "-" + matcher.group(3) : matcher.group(3);
                    final String access_token = "";
                    final String url = "http://0.0.0.0:5700/get_msg?message_id=" + message_id + "&access_token=" + access_token;
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    final Call call = client.newCall(request);
                    ObjectMapper om = new ObjectMapper();
                    try {
                        String body = call.execute().body().string();
                        String raw_imgInfo = om.readValue(body, MesGetData.class).getData().getMessage();
                        pattern = Pattern.compile("(file=)([0-9a-z.]*)");
                        Matcher imgM = pattern.matcher(raw_imgInfo);
                        if (imgM.find())
                        {
                            String ocr_url = "http://0.0.0.0:5700/.ocr_image?image=" + imgM.group(2) + "&access_token=" + access_token;
                            Request request_ocr = new Request.Builder().url(ocr_url).build();
                            Call responseBodyCall = client.newCall(request_ocr);
                            String jsBody = responseBodyCall.execute().body().string();
                            Data data = om.readValue(jsBody, OCRdata.class).getData();
                            String language = data.getLanguage();
                            List<Texts> textsList = data.getTexts();
                            MessageBuilder mb = new MessageBuilder();
                            if ("zh".equals(language))
                            {
                                for (Texts t : textsList)
                                {
                                    mb.add(t.getText());
                                    mb.newLine();
                                }
                            }
                            else
                            {
                                final String APP_ID = "";
                                final String SECURITY_KEY = "";
                                TransApi transApi = new TransApi(APP_ID,SECURITY_KEY);
                                for (Texts t : textsList)
                                {
                                    String transJS = transApi.getTransResult(t.getText(),"auto","zh");
                                    String unicode = om.readValue(transJS, TranslateData.class).getTransResult().get(0).getDst();
                                    mb.add(unicode);
                                    mb.newLine();
                                }
                            }
                            event.respond(mb.toString());
                        }
                    } catch (IOException e) {
                        event.respond(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}