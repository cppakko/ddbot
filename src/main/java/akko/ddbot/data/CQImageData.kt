package akko.ddbot.data
class CQImageData(
        val `data`: Data,
        val retcode: Int,
        val status: String
)

data class Data(
        val `file`: String,
        val filename: String,
        val size: Int,
        val url: String
)