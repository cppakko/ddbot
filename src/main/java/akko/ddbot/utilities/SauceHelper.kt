package akko.ddbot.utilities

import akko.ddbot.data.SauceResult
import kotlin.math.min

object SauceHelper {
    fun extract(input:String,limit:Int = 5) :List<SauceResult>{
        val json = GlobalObject.objectMapper.readTree(input)
        val rsltNode = json["results"]
        val len = rsltNode.size()
        val finallimit = min(len, limit)
        val arr = ArrayList<SauceResult>()
        for (idx in 0 until finallimit){
            val title =  rsltNode[idx]["data"]["title"]?.asText() ?: ""
            val thumbnail = rsltNode[idx]["header"]["thumbnail"].asText()
            val indexName = rsltNode[idx]["header"]["index_name"].asText()
            val extUrl = rsltNode[idx]["data"]["ext_urls"]?.map{it.asText()}?.toList() ?: ArrayList()
            val authorName = rsltNode[idx]["data"]["author_name"]?.asText() ?: ""
            val authorUrl = rsltNode[idx]["data"]["author_url"]?.asText() ?: ""
            val similarityText = rsltNode[idx]["header"]["similarity"]?.asText() ?: "0"
            val similarity = similarityText.toDouble()

            val engName = rsltNode[idx]["data"]["eng_name"]?.asText() ?: ""
            val jpName = rsltNode[idx]["data"]["jp_name"]?.asText() ?: ""

            arr.add(SauceResult(
                    title,
                    thumbnail,
                    indexName,
                    extUrl,
                    authorName,
                    authorUrl,
                    similarity,
                    engName,
                    jpName
            ))
        }
        return arr
    }
}