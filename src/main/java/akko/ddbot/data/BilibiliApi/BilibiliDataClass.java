package akko.ddbot.data.BilibiliApi;

public class BilibiliDataClass {

    private int code;
    private String message;
    private int ttl;
    private Data data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getTtl() {
        return ttl;
    }

    public Data getData() {
        return data;
    }

}

class Official {

    private int role;
    private String title;
    private String desc;
    private int type;

    public int getRole() {
        return role;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getType() {
        return type;
    }

}

class Vip {

    private int type;
    private int status;
    private int theme_type;
    private Label label;
    private int avatar_subscript;
    private String nickname_color;

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getTheme_type() {
        return theme_type;
    }

    public Label getLabel() {
        return label;
    }

    public int getAvatar_subscript() {
        return avatar_subscript;
    }

    public String getNickname_color() {
        return nickname_color;
    }

}

class Label {

    private String path;
    private String text;
    private String label_theme;

    public String getPath() {
        return path;
    }

    public String getText() {
        return text;
    }

    public String getLabel_theme() {
        return label_theme;
    }

}

class Pendant {

    private int pid;
    private String name;
    private String image;
    private int expire;
    private String image_enhance;

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getExpire() {
        return expire;
    }

    public String getImage_enhance() {
        return image_enhance;
    }

}

class Nameplate {

    private int nid;
    private String name;
    private String image;
    private String image_small;
    private String level;
    private String condition;

    public int getNid() {
        return nid;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getImage_small() {
        return image_small;
    }

    public String getLevel() {
        return level;
    }

    public String getCondition() {
        return condition;
    }

}

class Theme {

}

class Sys_notice {

}

