package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class JasaService implements Parcelable {
    private Integer id;
    private String nama;
    private Double harga;

    public JasaService(Integer id, String nama, Double harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }


    public static final Parcelable.Creator<JasaService> CREATOR = new Parcelable.Creator<JasaService>() {
        @Override
        public JasaService createFromParcel(Parcel in) {
            return new JasaService(in);
        }

        @Override
        public JasaService[] newArray(int size) {
            return new JasaService[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public Double getHarga() {
        return harga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.nama);
        dest.writeValue(this.harga);
    }
    protected JasaService(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

}
