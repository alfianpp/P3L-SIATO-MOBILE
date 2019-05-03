package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Kendaraan implements Parcelable {
    private String nomor_polisi;
    private String merk;
    private String tipe;

    public Kendaraan(String nomor_polisi, String merk, String tipe) {
        this.nomor_polisi = nomor_polisi;
        this.merk = merk;
        this.tipe = tipe;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nomor_polisi);
        dest.writeString(this.merk);
        dest.writeString(this.tipe);
    }

    protected Kendaraan(Parcel in) {
        this.nomor_polisi = in.readString();
        this.merk = in.readString();
        this.tipe = in.readString();
    }

    public static final Creator<Kendaraan> CREATOR = new Creator<Kendaraan>() {
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
