package id.or.qodr.jadwalkajianpekalongan.service;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by adul on 31/07/17.
 */

public interface UserClient {

    @FormUrlEncoded
    @POST("apiv2/jadwal/insert")
    Call<ResponseBody> submitNewKajian(
            @FieldMap Map<String, String> map
            );
}
