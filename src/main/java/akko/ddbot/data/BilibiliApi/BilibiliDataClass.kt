package akko.ddbot.data.BilibiliApi

data class BilibiliDataClass(
        val code: Int,
        val `data`: Data,
        val message: String,
        val ttl: Int
)

data class Data(
        val birthday: String,
        val coins: Int,
        val face: String,
        val fans_badge: Boolean,
        val is_followed: Boolean,
        val jointime: Int,
        val level: Int,
        val live_room: LiveRoom,
        val mid: Int,
        val moral: Int,
        val name: String,
        val nameplate: Nameplate,
        val official: Official,
        val pendant: Pendant,
        val rank: Int,
        val sex: String,
        val sign: String,
        val silence: Int,
        val sys_notice: SysNotice,
        val theme: Theme,
        val top_photo: String,
        val vip: Vip
)

data class LiveRoom(
        val broadcast_type: Int,
        val cover: String,
        val liveStatus: Int,
        val online: Int,
        val roomStatus: Int,
        val roomid: Int,
        val roundStatus: Int,
        val title: String,
        val url: String
)

data class Nameplate(
        val condition: String,
        val image: String,
        val image_small: String,
        val level: String,
        val name: String,
        val nid: Int
)

data class Official(
        val desc: String,
        val role: Int,
        val title: String,
        val type: Int
)

data class Pendant(
        val expire: Int,
        val image: String,
        val image_enhance: String,
        val name: String,
        val pid: Int,
        val image_enhance_frame: String
)

class SysNotice

class Theme

data class Vip(
        val avatar_subscript: Int,
        val label: Label,
        val nickname_color: String,
        val status: Int,
        val theme_type: Int,
        val type: Int
)

data class Label(
        val label_theme: String,
        val path: String,
        val text: String
)