package akko.ddbot.data

data class SauceResult (
        val title:String,
        val thumbnail:String,
        val indexName:String,
        val extUrls:List<String>,
        val authorName:String,
        val authorUrl:String,
        val similarity:Double,
)