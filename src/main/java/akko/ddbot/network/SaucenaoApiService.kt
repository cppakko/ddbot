package akko.ddbot.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SaucenaoApiService {
    @GET("search.php")
    operator fun get(@Query("db") db: Int, @Query("output_type") output_type: Int, @Query("testmode") testmode: Int, @Query("numres") numres: Int, @Query("url") url: String?,@Query("api_key") api_key:String?): Call<ResponseBody?>?
}