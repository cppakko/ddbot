package akko.ddbot.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoliconApiService {
    @GET("setu")
    operator fun get(@Query("apikey") apikey: String?, @Query("size1200") isSize1200: String?): Call<ResponseBody?>?
}