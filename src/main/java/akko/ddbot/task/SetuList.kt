package akko.ddbot.task

import java.util.*

class SetuList {
    companion object{
        private val setuList = LinkedList<Setu_msg>()
    }
    fun put(filePath: String,message_id: Long)
    {
        setuList.add(Setu_msg(filePath,message_id))
        if (setuList.size >= 10)
        {
            setuList.removeAt(0)
        }
    }
    fun find(message_id: Long): String {
        var j = setuList.size - 1
        while(j >= 0)
        {
            if (setuList[j].msgId == message_id)
            {
                return setuList[j].filePath
            }
            else j--
        }
        return "null"
    }
}

private data class Setu_msg(
        val filePath: String,
        val msgId: Long
)