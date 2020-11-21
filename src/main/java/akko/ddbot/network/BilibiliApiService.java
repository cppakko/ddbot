package akko.ddbot.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * bilibiliNetworkService
 */
public interface BilibiliApiService {
    @GET("info")
    Call<ResponseBody> getDatCall(@Query("mid") String uid);
}
