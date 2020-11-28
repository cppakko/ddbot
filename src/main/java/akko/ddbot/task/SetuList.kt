package akko.ddbot.task

import akko.ddbot.data.SetuMsg
import java.util.*

class SetuList {
    companion object{
        private val setuList = LinkedList<SetuMsg>()
    }
    fun put(thumbnailPath:String,filePath: String,message_id: Long)
    {
        setuList.add(SetuMsg(thumbnailPath,filePath,message_id))
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