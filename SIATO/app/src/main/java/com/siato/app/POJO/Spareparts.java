package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Spareparts implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kode);
        dest.writeString(this.nama);
        dest.writeString(this.merk);
        dest.writeString(this.tipe);
        dest.writeString(this.kode_peletakan);
        dest.writeValue(this.harga_beli);
        dest.writeValue(this.harga_jual);
        dest.writeValue(this.stok);
        dest.writeValue(this.stok_minimal);
        dest.writeString(this.url_gambar);
    }

    protected Spareparts(Parcel in) {
        this.kode = in.readString();
        this.nama = in.readString();
        this.merk = in.readString();
        this.tipe = in.readString();
        this.kode_peletakan = in.readString();
        this.harga_beli = (Double) in.readValue(Double.class.getClassLoader());
        this.harga_jual = (Double) in.readValue(Double.class.getClassLoader());
        this.stok = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stok_minimal = (Integer) in.readValue(Integer.class.getClassLoader());
        this.url_gambar = in.readString();
    }

    public static final Parcelable.Creator<Spareparts> CREATOR = new Parcelable.Creator<Spareparts>() {
        @Override
        public Spareparts createFromParcel(Parcel source) {
            return new Spareparts(source);
        }

        @Override
        public Spareparts[] newArray(int size) {
            return new Spareparts[size];
        }
    };
}