package com.siato.mysiato;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @FormUrlEncoded
    @POST("riwayat/login")
    Call<APIResponse> login(
            @Field("nomor_polisi") String nomor_polisi,
            @Field("nomor_telepon") String nomor_telepon
    );

    // --- CRUD SPAREPARTS

    /*@FormUrlEncoded
    @POST("data/spareparts/index")
    Call<APIResponse<List<Spareparts>>> getAllSpareparts(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/spareparts")
    Call<APIResponse> createSpareparts(
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("merk") String merk,
            @Field("tipe") String tipe,
            @Field("kode_peletakan") String kode_peletakan,
            @Field("harga_jual") String harga_jual,
            @Field("harga_beli") String harga_beli,
            @Field("stok") String stok,
            @Field("stok_minimal") String stok_minimal,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("data/spareparts/{kode}")
    Call<APIResponse<Spareparts>> getSpareparts(@Path("kode") String kode, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "data/spareparts/{kode}", hasBody = true)
    Call<APIResponse> updateSpareparts(
            @Path("kode") String kode,
            @Field("nama") String nama,
            @Field("merk") String merk,
            @Field("tipe") String tipe,
            @Field("kode_peletakan") String kode_peletakan,
            @Field("harga_jual") String harga_jual,
            @Field("harga_beli") String harga_beli,
            @Field("stok_minimal") String stok_minimal,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "data/spareparts/{kode}", hasBody = true)
    Call<APIResponse> deleteSpareparts(@Path("kode") String kode, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/spareparts/index/stokminimal")
    Call<APIResponse<List<Spareparts>>> getStokSpareparts(@Field("api_key") String api_key);*/

}