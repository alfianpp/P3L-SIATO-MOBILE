package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class JasaService implements Parcelable {
    private Integer id;
    private String nama;

    public JasaService(Integer id, String nama) {
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

    protected JasaService(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
    }

    public static final Creator<JasaService> CREATOR = new Creator<JasaService>() {
        @Override
        public JasaService createFromParcel(Parcel source) {
            return new JasaService(source);
        }

        @Override
        public JasaService[] newArray(int size) {
            return new JasaService[size];
        }
    };
}
