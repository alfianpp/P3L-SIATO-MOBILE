package com.siato.app;

public class Spareparts {
    private String kode;
    private String nama;
    private String merk;
    private String tipe;
    private String kode_peletakan;
    private Double harga_beli;
    private Double harga_jual;
    private Integer stok;
    private Integer stok_minimal;
    private String url_gambar;

    public Spareparts(String kode, String nama, String merk, String tipe, String kode_peletakan, Double harga_beli, Double harga_jual, Integer stok, Integer stok_minimal, String url_gambar) {
        this.kode = kode;
        this.nama = nama;
        this.merk = merk;
        this.tipe = tipe;
        this.kode_peletakan = kode_peletakan;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.stok = stok;
        this.stok_minimal = stok_minimal;
        this.url_gambar = url_gambar;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getMerk() {
        return merk;
    }

    public String getTipe() {
        return tipe;
    }

    public String getKodePeletakan() {
        return kode_peletakan;
    }

    public Double getHargaBeli() {
        return harga_beli;
    }

    public Double getHargaJual() {
        return harga_jual;
    }

    public Integer getStok() {
        return stok;
    }

    public Integer getStokMinimal() {
        return stok_minimal;
    }

    public String getURLGambar() {
        return url_gambar;
    }
}
