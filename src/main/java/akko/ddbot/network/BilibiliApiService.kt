package akko.ddbot.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BilibiliApiService {
    @GET("info")
    fun getDatCall(@Query("mid") uid: String?): Call<ResponseBody?>?
}