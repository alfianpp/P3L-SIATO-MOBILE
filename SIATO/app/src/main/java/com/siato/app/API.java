package com.siato.app;

import com.siato.app.POJO.DetailPengadaanBarang;
import com.siato.app.POJO.DetailPenjualan;
import com.siato.app.POJO.DetailPenjualanJasaService;
import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.POJO.Kendaraan;
import com.siato.app.POJO.Konsumen;
import com.siato.app.POJO.Partially.Cabang;
import com.siato.app.POJO.Partially.JasaService;
import com.siato.app.POJO.Pegawai;
import com.siato.app.POJO.Penjualan;
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

    // --- CABANG

    @FormUrlEncoded
    @POST("data/cabang/index")
    Call<APIResponse<List<Cabang>>> getAllCabang(@Field("api_key") String api_key);

    // --- MONTIR

    @FormUrlEncoded
    @POST("data/pegawai/index/role/3")
    Call<APIResponse<List<Pegawai>>> getAllMontir(@Field("api_key") String api_key);

    // --- JASA SERVICE

    @FormUrlEncoded
    @POST("data/jasaservice/index")
    Call<APIResponse<List<JasaService>>> getAllJasaService(@Field("api_key") String api_key);

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
    @POST("data/spareparts/index/stokminimal")
    Call<APIResponse<List<Spareparts>>> getStokSpareparts(@Field("api_key") String api_key);


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
    @POST("transaksi/pengadaan/data/{id}")
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


    // --- CRUD DETAIL PENGADAAN BARANG

    @FormUrlEncoded
    @POST("transaksi/pengadaan/detail/{id_pengadaan_barang}")
    Call<APIResponse<List<DetailPengadaanBarang>>> getAllDetailPengadaanBarang(@Path("id_pengadaan_barang") Integer id_pengadaan_barang, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("transaksi/pengadaan/detail")
    Call<APIResponse> createDetailPengadaanBarang(
            @Field("id_pengadaan_barang") Integer id_pengadaan_barang,
            @Field("kode_spareparts") String kode_spareparts,
            @Field("jumlah_pesan") String jumlah_pesan,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("transaksi/pengadaan/data/{id}")
    Call<APIResponse<DetailPengadaanBarang>> getDetailPengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/pengadaan/detail/{id}", hasBody = true)
    Call<APIResponse> updateDetailPengadaanBarang(
            @Path("id") Integer id,
            @Field("jumlah_pesan") String jumlah_pesan,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "transaksi/pengadaan/detail/{id}", hasBody = true)
    Call<APIResponse> deleteDetailPengadaanBarang(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/pengadaan/detail/{id}", hasBody = true)
    Call<APIResponse> verifikasiDetailPengadaanBarang(
            @Path("id") Integer id,
            @Field("jumlah_datang") String jumlah_datang,
            @Field("harga") String harga,
            @Field("api_key") String api_key
    );


    // --- CRUD PENJUALAN

    @FormUrlEncoded
    @POST("transaksi/penjualan/index")
    Call<APIResponse<List<Penjualan>>> getAllPenjualan(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("transaksi/penjualan/data")
    Call<APIResponse> createPenjualan(
            @Field("id_cabang") String id_cabang,
            @Field("jenis") String jenis,
            @Field("id_konsumen") String id_konsumen,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("transaksi/penjualan/data/{id}")
    Call<APIResponse<Penjualan>> getPenjualan(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/penjualan/data/{id}", hasBody = true)
    Call<APIResponse> updatePenjualan(
            @Path("id") Integer id,
            @Field("id_cabang") String id_cabang,
            @Field("jenis") String jenis,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/penjualan/data/{id}", hasBody = true)
    Call<APIResponse> verifikasiPenjualan(
            @Path("id") Integer id,
            @Field("status") String status,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "transaksi/penjualan/data/{id}", hasBody = true)
    Call<APIResponse> deletePenjualan(@Path("id") Integer id, @Field("api_key") String api_key);


    // --- CRUD DETAIL PENJUALAN

    @FormUrlEncoded
    @POST("transaksi/penjualan/detail/{id_penjualan}")
    Call<APIResponse<List<DetailPenjualan>>> getAllDetailPenjualan(@Path("id_penjualan") Integer id_penjualan, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("transaksi/penjualan/detail")
    Call<APIResponse> createDetailPenjualan(
            @Field("id_penjualan") Integer id_penjualan,
            @Field("nomor_polisi") String nomor_polisi,
            @Field("id_montir") String id_montir,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("transaksi/penjualan/data/{id}")
    Call<APIResponse<DetailPenjualan>> getDetailPenjualan(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/penjualan/data/{id}", hasBody = true)
    Call<APIResponse> updateDetailPenjualan(
            @Path("id") Integer id,
            @Field("id_cabang") String id_cabang,
            @Field("jenis") String jenis,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "transaksi/penjualan/data/{id}", hasBody = true)
    Call<APIResponse> deleteDetailPenjualan(@Path("id") Integer id, @Field("api_key") String api_key);


    // --- CRUD DETAIL PENJUALAN SPAREPARTS

    @FormUrlEncoded
    @POST("transaksi/penjualan/detail/spareparts/{id_detail_penjualan}")
    Call<APIResponse<List<DetailPenjualanSpareparts>>> getAllDetailPenjualanSpareparts(@Path("id_detail_penjualan") Integer id_detail_penjualan, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("transaksi/penjualan/detail/spareparts/data")
    Call<APIResponse> createDetailPenjualanSpareparts(
            @Field("id_detail_penjualan") Integer id_detail_penjualan,
            @Field("kode_spareparts") String kode_spareparts,
            @Field("jumlah") String jumlah,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("transaksi/penjualan/data/{id}")
    Call<APIResponse<DetailPenjualanSpareparts>> getDetailPenjualanSpareparts(@Path("id") Integer id, @Field("api_key") String api_key);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/penjualan/detail/spareparts/data/{id}", hasBody = true)
    Call<APIResponse> updateDetailPenjualanSpareparts(
            @Path("id") Integer id,
            @Field("jumlah") String jumlah,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "transaksi/penjualan/detail/spareparts/data/{id}", hasBody = true)
    Call<APIResponse> deleteDetailPenjualanSpareparts(@Path("id") Integer id, @Field("api_key") String api_key);


    // --- CRUD DETAIL PENJUALAN JASA SERVICE

    @FormUrlEncoded
    @POST("transaksi/penjualan/detail/jasaservice/{id_detail_penjualan}")
    Call<APIResponse<List<DetailPenjualanJasaService>>> getAllDetailPenjualanJasaService(@Path("id_detail_penjualan") Integer id_detail_penjualan, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("transaksi/penjualan/detail/jasaservice/data")
    Call<APIResponse> createDetailPenjualanJasaService(
            @Field("id_detail_penjualan") Integer id_detail_penjualan,
            @Field("id_jasaservice") String kode_spareparts,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "transaksi/penjualan/detail/jasaservice/data/{id}", hasBody = true)
    Call<APIResponse> updateDetailPenjualanJasaService(
            @Path("id") Integer id,
            @Field("jumlah") String jumlah,
            @Field("api_key") String api_key
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "transaksi/penjualan/detail/jasaservice/data/{id}", hasBody = true)
    Call<APIResponse> deleteDetailPenjualanJasaService(@Path("id") Integer id, @Field("api_key") String api_key);
}
