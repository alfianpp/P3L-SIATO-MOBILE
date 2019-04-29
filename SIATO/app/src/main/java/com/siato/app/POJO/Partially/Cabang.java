package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Cabang implements Parcelable {
    private Integer id;
    private String nama;

    public Cabang(Integer id, String nama) {
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
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeValue(this.id);
        dest.writeString(this.nama);
    }

    protected  Cabang (Parcel in){
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
    }

    public static final Parcelable.Creator<Cabang> CREATOR = new Parcelable.Creator<Cabang>(){
        @Override
        public Cabang createFromParcel(Parcel source) {
            return new Cabang(source);
        }

        @Override
        public Cabang[] newArray(int size) {
            return new Cabang[size];
        }
    };
}
