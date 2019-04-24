package com.siato.app;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<APIResponse<Spareparts>> showSpareparts(@Path("kode") String kode, @Field("api_key") String api_key);

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
    Call<APIResponse<Supplier>> showSupplier(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @PUT("data/supplier/{id}")
    Call<APIResponse> updateSupplier(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("nama_sales") String nama_sales,
            @Field("nomor_telepon_sales") String nomor_telepon_sales,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @DELETE("data/spareparts/{id}")
    Call<APIResponse> deleteSupplier(@Path("id") Integer id, @Field("api_key") String api_key);

    // --- CRUD KENDARAAN

    @FormUrlEncoded
    @POST("data/kendaraan/index")
    Call<APIResponse<List<Kendaraan>>> getAllKendaraan(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/kendaraan")
    Call<APIResponse> createKendaraan(
            @Path("nomor_polisi") String nomor_polisi,
            @Field("merk") String merk,
            @Field("tipe") String tipe,
            @Field("pemilik") String pemilik,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("data/kendaraan/{id}")
    Call<APIResponse<Kendaraan>> showKendaraan(@Path("nomor_polisi") String nomor_polisi, @Field("api_key") String api_key);

    @FormUrlEncoded
    @PUT("data/kendaraan/{id}")
    Call<APIResponse> updateKendaraan(
            @Path("nomor_polisi") String nomor_polisi,
            @Field("merk") String merk,
            @Field("tipe") String tipe,
            @Field("pemilik") String pemilik,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @DELETE("data/kendaraan/{id}")
    Call<APIResponse> deleteKendaraan(@Path("nomor_polisi") String nomor_polisi, @Field("api_key") String api_key);

    // --- CRUD PENGADAAN BARANG

    @FormUrlEncoded
    @POST("data/pengadaanBarang/index")
    Call<APIResponse<List<PengadaanBarang>>> getAllPengadaanBarang(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("data/pengadaanBarang")
    Call<APIResponse> createPengadaanBarang(
            @Field("id_supplier") Integer id_supplier,
            @Field("total") Double total,
            @Field("status") Integer status,
            @Field("tgl_transaksi") String tgl_transaksi,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("data/pengadaanBarang/{id}")
    Call<APIResponse<PengadaanBarang>> showPengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @PUT("data/pengadaanBarang/{id}")
    Call<APIResponse> updatePengadaanBarang(
            @Field("id_supplier") Integer id_supplier,
            @Field("total") Double total,
            @Field("status") Integer status,
            @Field("tgl_transaksi") String tgl_transaksi,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @DELETE("data/pengadaanBarang/{id}")
    Call<APIResponse> deletePengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);

    // --- CRUD DETIL PENGADAAN BARANG

//    @FormUrlEncoded
//    @POST("data/detailPengadaanBarang/index")
//    Call<APIResponse<List<DetailPengadaanBarang>>> getAllDetailPengadaanBarang(@Field("api_key") String api_key);
//
//    @FormUrlEncoded
//    @POST("data/detailPengadaanBarang")
//    Call<APIResponse> createPengadaanBarang(
//            @Field("id_supplier") Integer id_supplier,
//            @Field("total") Double total,
//            @Field("status") Integer status,
//            @Field("tgl_transaksi") String tgl_transaksi,
//            @Field("api_key") String api_key
//    );
//
//    @FormUrlEncoded
//    @POST("data/pengadaanBarang/{id}")
//    Call<APIResponse<PengadaanBarang>> showPengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);
//
//    @FormUrlEncoded
//    @PUT("data/pengadaanBarang/{id}")
//    Call<APIResponse> updatePengadaanBarang(
//            @Field("id_supplier") Integer id_supplier,
//            @Field("total") Double total,
//            @Field("status") Integer status,
//            @Field("tgl_transaksi") String tgl_transaksi,
//            @Field("api_key") String api_key
//    );
//
//    @FormUrlEncoded
//    @DELETE("data/pengadaanBarang/{id}")
//    Call<APIResponse> deletePengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);
}
