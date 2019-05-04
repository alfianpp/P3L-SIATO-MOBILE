package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.DetilPenjualan;
import com.siato.app.POJO.Partially.Spareparts;

public class DetailPenjualanSpareparts implements Parcelable {
    private Integer id;
    private Integer id_detail_penjualan;
    private Spareparts spareparts;
    private Integer jumlah;
    private Double harga;

    public DetailPenjualanSpareparts(Integer id, Integer id_detail_penjualan, Spareparts spareparts, Integer jumlah, Double harga) {
        this.id = id;
        this.id_detail_penjualan = id_detail_penjualan;
        this.spareparts = spareparts;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdDetailPenjualan() {
        return id_detail_penjualan;
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
        dest.writeValue(this.id_detail_penjualan);
        dest.writeParcelable(this.spareparts, flags);
        dest.writeValue(this.jumlah);
        dest.writeValue(this.harga);
    }

    protected DetailPenjualanSpareparts(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id_detail_penjualan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.spareparts = in.readParcelable(Spareparts.class.getClassLoader());
        this.jumlah = (Integer) in.readValue(Integer.class.getClassLoader());
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailPenjualanSpareparts> CREATOR = new Parcelable.Creator<DetailPenjualanSpareparts>() {
        @Override
        public DetailPenjualanSpareparts createFromParcel(Parcel source) {
            return new DetailPenjualanSpareparts(source);
        }

        @Override
        public DetailPenjualanSpareparts[] newArray(int size) {
            return new DetailPenjualanSpareparts[size];
        }
    };
}
