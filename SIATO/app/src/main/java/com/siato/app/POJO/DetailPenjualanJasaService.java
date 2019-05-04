package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.JasaService;
import com.siato.app.POJO.Partially.Kendaraan;
import com.siato.app.POJO.Partially.DetilPenjualan;
import com.siato.app.POJO.Partially.Penjualan;

public class DetailPenjualanJasaService implements Parcelable {
    private Integer id;
    private Integer id_detail_penjualan;
    private JasaService jasa_service;
    private Integer jumlah;
    private Double harga;

    public DetailPenjualanJasaService(Integer id, Integer id_detail_penjualan, JasaService jasa_service, Integer jumlah, Double harga) {
        this.id = id;
        this.id_detail_penjualan = id_detail_penjualan;
        this.jasa_service = jasa_service;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdDetailPenjualan() {
        return id_detail_penjualan;
    }

    public JasaService getJasaService() {
        return jasa_service;
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
        dest.writeParcelable(this.jasa_service, flags);
        dest.writeValue(this.jumlah);
        dest.writeValue(this.harga);
    }

    protected DetailPenjualanJasaService(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id_detail_penjualan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.jasa_service = in.readParcelable(JasaService.class.getClassLoader());
        this.jumlah = (Integer) in.readValue(Integer.class.getClassLoader());
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<DetailPenjualanJasaService> CREATOR = new Creator<DetailPenjualanJasaService>() {
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
