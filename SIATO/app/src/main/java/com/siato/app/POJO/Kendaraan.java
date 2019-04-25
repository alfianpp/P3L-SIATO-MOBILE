package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.Konsumen;

public class Kendaraan implements Parcelable {
    private String nomor_polisi;
    private String merk;
    private String tipe;
    private Konsumen pemilik;

    public Kendaraan(String nomor_polisi, String merk, String tipe, Konsumen pemilik) {
        this.nomor_polisi = nomor_polisi;
        this.merk = merk;
        this.tipe = tipe;
        this.pemilik = pemilik;
    }

    public String getNomorPolisi() {
        return nomor_polisi;
    }

    public String getMerk() {
        return merk;
    }

    public String getTipe() {
        return tipe;
    }

    public Konsumen getPemilik() {
        return pemilik;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nomor_polisi);
        dest.writeString(this.merk);
        dest.writeString(this.tipe);
        dest.writeParcelable(this.pemilik, flags);
    }

    protected Kendaraan(Parcel in) {
        this.nomor_polisi = in.readString();
        this.merk = in.readString();
        this.tipe = in.readString();
        this.pemilik = in.readParcelable(Konsumen.class.getClassLoader());
    }

    public static final Parcelable.Creator<Kendaraan> CREATOR = new Parcelable.Creator<Kendaraan>() {
        @Override
        public Kendaraan createFromParcel(Parcel source) {
            return new Kendaraan(source);
        }

        @Override
        public Kendaraan[] newArray(int size) {
            return new Kendaraan[size];
        }
    };
}
