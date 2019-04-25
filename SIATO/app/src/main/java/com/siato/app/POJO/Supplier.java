package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Supplier implements Parcelable {
    private Integer id;
    private String nama;
    private String alamat;
    private String nama_sales;
    private String nomor_telepon_sales;

    public Supplier(Integer id, String nama, String alamat, String nama_sales, String nomor_telepon_sales) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.nama_sales = nama_sales;
        this.nomor_telepon_sales = nomor_telepon_sales;
    }

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNamaSales() {
        return nama_sales;
    }

    public String getNomorTeleponSales() {
        return nomor_telepon_sales;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.alamat);
        dest.writeString(this.nama_sales);
        dest.writeString(this.nomor_telepon_sales);
    }

    protected Supplier(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
        this.alamat = in.readString();
        this.nama_sales = in.readString();
        this.nomor_telepon_sales = in.readString();
    }

    public static final Parcelable.Creator<Supplier> CREATOR = new Parcelable.Creator<Supplier>() {
        @Override
        public Supplier createFromParcel(Parcel source) {
            return new Supplier(source);
        }

        @Override
        public Supplier[] newArray(int size) {
            return new Supplier[size];
        }
    };
}
