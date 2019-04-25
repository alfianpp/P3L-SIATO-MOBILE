package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.Supplier;

public class PengadaanBarang implements Parcelable {
    private Integer id;
    private Supplier supplier;
    private Double total;
    private Integer status;
    private String tgl_transaksi;

    public PengadaanBarang(Integer id, Supplier supplier, Double total, Integer status, String tgl_transaksi) {
        this.id = id;
        this.supplier = supplier;
        this.total = total;
        this.status = status;
        this.tgl_transaksi = tgl_transaksi;
    }

    public Integer getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Double getTotal() {
        return total;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTglTransaksi() {
        return tgl_transaksi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.supplier, flags);
        dest.writeValue(this.total);
        dest.writeValue(this.status);
        dest.writeString(this.tgl_transaksi);
    }

    protected PengadaanBarang(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.supplier = in.readParcelable(Supplier.class.getClassLoader());
        this.total = (Double) in.readValue(Double.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tgl_transaksi = in.readString();
    }

    public static final Parcelable.Creator<PengadaanBarang> CREATOR = new Parcelable.Creator<PengadaanBarang>() {
        @Override
        public PengadaanBarang createFromParcel(Parcel source) {
            return new PengadaanBarang(source);
        }

        @Override
        public PengadaanBarang[] newArray(int size) {
            return new PengadaanBarang[size];
        }
    };
}

