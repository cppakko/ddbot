/**
  * Copyright 2020 bejson.com 
  */
package akko.ddbot.task;

/**
 * Auto-generated: 2020-11-08 12:2:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
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

class Data {

    private long mid;
    private String name;
    private String sex;
    private String face;
    private String sign;
    private int rank;
    private int level;
    private int jointime;
    private int moral;
    private int silence;
    private String birthday;
    private int coins;
    private boolean fans_badge;
    private Official official;
    private Vip vip;
    private Pendant pendant;
    private Nameplate nameplate;
    private boolean is_followed;
    private String top_photo;
    private Theme theme;
    private Sys_notice sys_notice;
    private Live_room live_room;

    public long getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getFace() {
        return face;
    }

    public String getSign() {
        return sign;
    }

    public int getRank() {
        return rank;
    }

    public int getLevel() {
        return level;
    }

    public int getJointime() {
        return jointime;
    }

    public int getMoral() {
        return moral;
    }

    public int getSilence() {
        return silence;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getCoins() {
        return coins;
    }

    public boolean getFans_badge() {
        return fans_badge;
    }

    public Official getOfficial() {
        return official;
    }

    public Vip getVip() {
        return vip;
    }

    public Pendant getPendant() {
        return pendant;
    }

    public Nameplate getNameplate() {
        return nameplate;
    }

    public boolean getIs_followed() {
        return is_followed;
    }

    public String getTop_photo() {
        return top_photo;
    }

    public Theme getTheme() {
        return theme;
    }

    public Sys_notice getSys_notice() {
        return sys_notice;
    }

    public Live_room getLive_room() {
        return live_room;
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

class Live_room {

    private int roomStatus;
    private int liveStatus;
    private String url;
    private String title;
    private String cover;
    private long online;
    private long roomid;
    private int roundStatus;
    private int broadcast_type;

    public int getRoomStatus() {
        return roomStatus;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public long getOnline() {
        return online;
    }

    public long getRoomid() {
        return roomid;
    }

    public int getRoundStatus() {
        return roundStatus;
    }

    public int getBroadcast_type() {
        return broadcast_type;
    }

}