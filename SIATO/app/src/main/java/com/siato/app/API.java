package com.siato.app;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {
    @FormUrlEncoded
    @POST("auth/pegawai")
    Call<APIResponse<Pegawai>> login(@Field("username") String username, @Field("password") String password);
}
