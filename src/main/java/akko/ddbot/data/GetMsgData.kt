package akko.ddbot.data
class GetMsgData(
        val `data`: MsgData,
        val retcode: Int,
        val status: String
)

data class MsgData(
        val group: Boolean,
        val group_id: Int,
        val message: String,
        val message_id: Long,
        val real_id: Int,
        val sender: Sender,
        val time: Long
)

data class Sender(
        val nickname: String,
        val user_id: Long
)