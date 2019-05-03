package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.JasaService;
import com.siato.app.POJO.Partially.Kendaraan;
import com.siato.app.POJO.Partially.DetilPenjualan;
import com.siato.app.POJO.Partially.Penjualan;

public class DetailPenjualanJasaService implements Parcelable {
    private Integer id;
    private DetilPenjualan detilPenjualan;
    private JasaService jasaService;
    private Integer jumlah;
    private Double harga;

    public DetailPenjualanJasaService(Integer id, DetilPenjualan detilPenjualan, JasaService jasaService, Integer jumlah, Double harga) {
        this.id = id;
        this.detilPenjualan = detilPenjualan;
        this.jasaService = jasaService;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public DetilPenjualan getDetilPenjualan() {
        return detilPenjualan;
    }

    public JasaService getJasaService() {
        return jasaService;
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
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.detilPenjualan,flag);
        dest.writeParcelable(this.jasaService,flag);
        dest.writeValue(this.jumlah);
        dest.writeValue(this.harga);
    }

    protected DetailPenjualanJasaService(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.detilPenjualan = in.readParcelable(Penjualan.class.getClassLoader());
        this.jasaService = in.readParcelable(Kendaraan.class.getClassLoader());
        this.jumlah = (Integer) in.readValue(Integer.class.getClassLoader());
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailPenjualanJasaService> CREATOR = new Parcelable.Creator<DetailPenjualanJasaService>() {
        @Override
        public DetailPenjualanJasaService createFromParcel(Parcel source) {
            return new DetailPenjualanJasaService(source);
        }

        @Override
        public DetailPenjualanJasaService[] newArray(int size) {
            return new DetailPenjualanJasaService[size];
        }
    };
}
