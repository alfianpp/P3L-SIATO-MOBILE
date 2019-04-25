package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Konsumen implements Parcelable {
    private Integer id;
    private String nama;
    private String nomor_telepon;
    private String alamat;

    public Konsumen(Integer id, String nama, String nomor_telepon, String alamat) {
        this.id = id;
        this.nama = nama;
        this.nomor_telepon = nomor_telepon;
        this.alamat = alamat;
    }

    public Integer getID() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorTelepon() {
        return nomor_telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.nomor_telepon);
        dest.writeString(this.alamat);
    }

    protected Konsumen(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
        this.nomor_telepon = in.readString();
        this.alamat = in.readString();
    }

    public static final Parcelable.Creator<Konsumen> CREATOR = new Parcelable.Creator<Konsumen>() {
        @Override
        public Konsumen createFromParcel(Parcel source) {
            return new Konsumen(source);
        }

        @Override
        public Konsumen[] newArray(int size) {
            return new Konsumen[size];
        }
    };
}
