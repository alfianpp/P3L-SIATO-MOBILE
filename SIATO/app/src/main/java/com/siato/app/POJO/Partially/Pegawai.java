package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Pegawai implements Parcelable{
    private Integer id;
    private String nama;

    public Pegawai(Integer id, String nama) {
        this.id = id;
        this.nama = nama;

    }


    public Integer getId() {
        return id;
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
        dest.writeValue(this.id);
        dest.writeString(this.nama);
    }

    protected Pegawai(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
    }

    public static final Parcelable.Creator<Pegawai> CREATOR = new Parcelable.Creator<Pegawai>() {
        @Override
        public Pegawai createFromParcel(Parcel source) {
            return new Pegawai(source);
        }

        @Override
        public Pegawai[] newArray(int size) {
            return new Pegawai[size];
        }
    };
}
