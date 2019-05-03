package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.DetilPenjualan;
import com.siato.app.POJO.Partially.Spareparts;

public class DetilPenjualanSparepart implements Parcelable {
    private Integer id;
    private DetilPenjualan detilPenjualan;
    private Spareparts spareparts;
    private Integer jumlah;
    private Double harga;

    public DetilPenjualanSparepart(Integer id, DetilPenjualan detilPenjualan, Spareparts spareparts, Integer jumlah, Double harga) {
        this.id = id;
        this.detilPenjualan = detilPenjualan;
        this.spareparts = spareparts;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public DetilPenjualan getDetilPenjualan() {
        return detilPenjualan;
    }

    public Spareparts getSpareparts() {
        return spareparts;
    }

    public Integer getJumlah() {
        return jumlah;
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
        dest.writeParcelable(this.detilPenjualan, flags);
        dest.writeParcelable(this.spareparts, flags);
        dest.writeValue(this.jumlah);
        dest.writeValue(this.harga);
    }

    protected DetilPenjualanSparepart(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.detilPenjualan = in.readParcelable(DetilPenjualan.class.getClassLoader());
        this.spareparts = in.readParcelable(Spareparts.class.getClassLoader());
        this.jumlah = (Integer) in.readValue(Integer.class.getClassLoader());
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetilPenjualanSparepart> CREATOR = new Parcelable.Creator<DetilPenjualanSparepart>() {
        @Override
        public DetilPenjualanSparepart createFromParcel(Parcel source) {
            return new DetilPenjualanSparepart(source);
        }

        @Override
        public DetilPenjualanSparepart[] newArray(int size) {
            return new DetilPenjualanSparepart[size];
        }
    };
}
