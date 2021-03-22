package akko.ddbot.data

data class BilibiliLiveShare(
    val app: String,
    val config: Config,
    val desc: String,
    val extra: Extra,
    val meta: Meta,
    val prompt: String,
    val ver: String,
    val view: String
)

data class Config(
    val autosize: Boolean,
    val ctime: Int,
    val forward: Boolean,
    val token: String,
    val type: String
)

data class Extra(
    val app_type: Int,
    val appid: Int,
    val msg_seq: Long,
    val uin: Int
)

data class Meta(
    val news: News
)

data class News(
    val action: String,
    val android_pkg_name: String,
    val app_type: Int,
    val appid: Int,
    val desc: String,
    val jumpUrl: String,
    val preview: String,
    val source_icon: String,
    val source_url: String,
    val tag: String,
    val title: String
)