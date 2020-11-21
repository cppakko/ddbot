package akko.ddbot.command;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceMan implements GroupCommand {
    private static final String patternStr = "^([0-9]+)d([0-9]+)(?:[+](?:(?:([0-9]+)d([0-9]+))|([0-9]+)))*$";
    private static final Pattern rollPattern = Pattern.compile(patternStr);

    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {

        String input = eventGroupMessage.getMessage().trim();
        String[] inputSlice = input.split(" ");
        if (inputSlice.length == 1 || (inputSlice.length == 2 && inputSlice[1].equals("help"))) {
            return helpInfo();
        } else if (inputSlice.length == 3 && inputSlice[1].equals("roll")) {
            return rollResult(inputSlice[2]);
        } else {
            return "啊这，命令不对呢，输入!dice help获取帮助";
        }
    }


    private String rollResult(String input) {
        Matcher matcher = rollPattern.matcher(input);
        if (matcher.matches()) {
            List<String> diceList = new ArrayList<>();
            MessageBuilder msgBuilder = new MessageBuilder();
            Random random = new Random();
            int val = 0;
            int fixSum = 0;
            String[] inputSegments = input.split("[+]");
            for (String seg : inputSegments) {
                String[] segsep = seg.split("[d]");
                if (segsep.length == 1) {
                    int fix = Integer.parseInt(seg);
                    val += fix;
                    fixSum += fix;
                } else {
                    int diceCount = Integer.parseInt(segsep[0]);
                    int diceType = Integer.parseInt(segsep[1]);
                    int diceVal = 0;
                    for (int i = 0; i < diceCount; i++) {
                        diceVal += random.nextInt(diceType) + 1;
                    }
                    val += diceVal;
                    diceList.add("投掷" + diceCount + "枚" + diceType + "面骰，结果为:" + diceVal);
                }
            }
            msgBuilder.add("掷骰~").newLine();
            for (String diceInfo : diceList) {
                msgBuilder.add(diceInfo).newLine();
            }
            msgBuilder.add("+" + fixSum + "补正").newLine();
            msgBuilder.add("结果：" + val);
            return msgBuilder.toString();
        } else {
            return "啊这，掷筛格式错误，输入!dice help获取帮助";
        }
    }

    private String helpInfo() {
        return new MessageBuilder()
            .add("------DiceMan------").newLine()
            .add("dice roll {param}").newLine()
            .add("(暂时只有一个这一个roll子命令）").newLine()
            .add("param格式大致像1d6+2d20+5这样").newLine()
            .add("<a>d<b>表示掷a个b面骰，+[数字]表示补正").newLine()
            .add("具体细节建议直接问群友").newLine()
            .add("-------------------").toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("dice", "d");
    }
}
