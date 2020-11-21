package akko.ddbot.data.BilibiliApi;

public class Live_room {

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
