package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Konsumen implements Parcelable {
    private Integer id;
    private String nama;
    private String noTlp;

    public Konsumen(Integer id, String nama, String noTlp) {
        this.id = id;
        this.nama = nama;
        this.noTlp = noTlp;
    }

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTlp() {
        return noTlp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.noTlp);
    }

    protected Konsumen(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
        this.noTlp = in.readString();
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
