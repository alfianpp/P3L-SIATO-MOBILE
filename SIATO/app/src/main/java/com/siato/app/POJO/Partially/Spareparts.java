package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Spareparts implements Parcelable {
    private String kode;
    private String nama;
    private String merk;

    public Spareparts(String kode, String nama, String merk) {
        this.kode = kode;
        this.nama = nama;
        this.merk = merk;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kode);
        dest.writeString(this.nama);
        dest.writeString(this.merk);
    }

    protected Spareparts(Parcel in) {
        this.kode = in.readString();
        this.nama = in.readString();
        this.merk = in.readString();
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