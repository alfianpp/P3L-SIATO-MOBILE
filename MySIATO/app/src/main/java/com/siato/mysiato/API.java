package com.siato.mysiato;

import com.siato.mysiato.POJO.Penjualan;
import com.siato.mysiato.POJO.Spareparts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @FormUrlEncoded
    @POST("riwayat/login")
    Call<APIResponse> login(
            @Field("nomor_polisi") String nomor_polisi,
            @Field("nomor_telepon") String nomor_telepon
    );

    @GET("data/spareparts/index/search")
    Call<APIResponse<List<Spareparts>>> listRepos(
        @Query("name") String name,
        @Query("order_by") String order_by
    );

    @FormUrlEncoded
    @POST("riwayat/index")
    Call<APIResponse<List<Penjualan>>> index(
            @Field("nomor_polisi") String nomor_polisi
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
