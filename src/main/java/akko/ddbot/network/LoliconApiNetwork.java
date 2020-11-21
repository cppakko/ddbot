package akko.ddbot.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoliconApiNetwork {

    @GET("setu")
    Call<ResponseBody> get(@Query("apikey") String apikey,@Query("size1200") String isSize1200);
}
