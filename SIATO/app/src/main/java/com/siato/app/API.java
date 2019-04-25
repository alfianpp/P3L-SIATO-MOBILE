package com.siato.app;

import com.siato.app.POJO.Kendaraan;
import com.siato.app.POJO.Konsumen;
import com.siato.app.POJO.Pegawai;
import com.siato.app.POJO.Spareparts;
import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.POJO.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    // --- CRUD SPAREPARTS

    @FormUrlEncoded
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
    @POST("auth/pegawai")
    Call<APIResponse<Pegawai>> login(@Field("username") String username, @Field("password") String password);


    // --- CRUD SUPPLIER

    @FormUrlEncoded
    @POST("data/supplier/index")
    Call<APIResponse<List<Supplier>>> getAllSupplier(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/supplier")
    Call<APIResponse> createSupplier(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("nama_sales") String nama_sales,
            @Field("nomor_telepon_sales") String nomor_telepon_sales,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("data/supplier/{id}")
    Call<APIResponse<Supplier>> getSupplier(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "data/supplier/{id}", hasBody = true)
    Call<APIResponse> updateSupplier(
            @Path("id") Integer id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("nama_sales") String nama_sales,
            @Field("nomor_telepon_sales") String nomor_telepon_sales,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "data/supplier/{id}", hasBody = true)
    Call<APIResponse> deleteSupplier(@Path("id") Integer id, @Field("api_key") String api_key);


    // --- CRUD KONSUMEN

    @FormUrlEncoded
    @POST("data/konsumen/index")
    Call<APIResponse<List<Konsumen>>> getAllKonsumen(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/konsumen")
    Call<APIResponse> createKonsumen(
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("alamat") String alamat,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("data/konsumen/{id}")
    Call<APIResponse<Konsumen>> getKonsumen(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "data/konsumen/{id}", hasBody = true)
    Call<APIResponse> updateKonsumen(
            @Path("id") Integer id,
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("alamat") String alamat,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "data/konsumen/{id}", hasBody = true)
    Call<APIResponse> deleteKonsumen(@Path("id") Integer id, @Field("api_key") String api_key);


    // --- CRUD KENDARAAN

    @FormUrlEncoded
    @POST("data/kendaraan/index")
    Call<APIResponse<List<Kendaraan>>> getAllKendaraan(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/kendaraan")
    Call<APIResponse> createKendaraan(
            @Field("nomor_polisi") String nomor_polisi,
            @Field("merk") String merk,
            @Field("tipe") String tipe,
            @Field("id_pemilik") String id_pemilik,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("data/kendaraan/{nomor_polisi}")
    Call<APIResponse<Kendaraan>> getKendaraan(@Path("nomor_polisi") String nomor_polisi, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "data/kendaraan/{nomor_polisi}", hasBody = true)
    Call<APIResponse> updateKendaraan(
            @Path("nomor_polisi") String nomor_polisi,
            @Field("merk") String merk,
            @Field("tipe") String tipe,
            @Field("id_pemilik") String id_pemilik,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "data/kendaraan/{nomor_polisi}", hasBody = true)
    Call<APIResponse> deleteKendaraan(@Path("nomor_polisi") String nomor_polisi, @Field("api_key") String api_key);


    // --- CRUD PENGADAAN BARANG

    @FormUrlEncoded
    @POST("transaksi/pengadaan/index")
    Call<APIResponse<List<PengadaanBarang>>> getAllPengadaanBarang(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("transaksi/pengadaan/data")
    Call<APIResponse> createPengadaanBarang(
            @Field("id_supplier") String id_supplier,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("transaksi/pengadaan/{id}")
    Call<APIResponse<PengadaanBarang>> getPengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/pengadaan/data/{id}", hasBody = true)
    Call<APIResponse> updatePengadaanBarang(
            @Path("id") Integer id,
            @Field("id_supplier") String id_supplier,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "transaksi/pengadaan/data/{id}", hasBody = true)
    Call<APIResponse> deletePengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);
}
