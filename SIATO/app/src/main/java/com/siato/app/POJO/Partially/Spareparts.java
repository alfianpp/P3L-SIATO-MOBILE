package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Spareparts implements Parcelable {
    private String kode;
    private String nama;
    private String merk;
    private String tipe;
    private String kode_peletakan;

    public Spareparts(String kode, String nama, String merk, String tipe, String kode_peletakan) {
        this.kode = kode;
        this.nama = nama;
        this.merk = merk;
        this.tipe = tipe;
        this.kode_peletakan = kode_peletakan;
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
    }

    protected Spareparts(Parcel in) {
        this.kode = in.readString();
        this.nama = in.readString();
        this.merk = in.readString();
        this.tipe = in.readString();
        this.kode_peletakan = in.readString();
    }

    public static final Creator<Spareparts> CREATOR = new Creator<Spareparts>() {
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