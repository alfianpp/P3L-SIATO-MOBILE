package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Spareparts implements Parcelable {
    private String kode;
    private String nama;

    public Spareparts(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;

    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kode);
        dest.writeString(this.nama);
    }

    protected Spareparts(Parcel in) {
        this.kode = in.readString();
        this.nama = in.readString();
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