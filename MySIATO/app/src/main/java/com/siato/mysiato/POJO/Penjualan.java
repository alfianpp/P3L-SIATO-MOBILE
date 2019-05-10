package com.siato.mysiato.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.mysiato.POJO.Partially.Cabang;
import com.siato.mysiato.POJO.Partially.Konsumen;
import com.siato.mysiato.POJO.Partially.Pegawai;

public class Penjualan implements Parcelable {
    private Integer id;
    private Cabang cabang;
    private String jenis;
    private Konsumen konsumen;
    private Double subtotal;
    private Double diskon;
    private Double total;
    private Double uang_diterima;
    private Pegawai cs;
    private Pegawai kasir;
    private Integer status;
    private String tgl_transaksi;

    public Penjualan(Integer id, Cabang cabang, String jenis, Konsumen konsumen, Double subtotal, Double diskon, Double total, Double uang_diterima, Pegawai cs, Pegawai kasir, Integer status, String tgl_transaksi) {
        this.id = id;
        this.cabang = cabang;
        this.jenis = jenis;
        this.konsumen = konsumen;
        this.subtotal = subtotal;
        this.diskon = diskon;
        this.total = total;
        this.uang_diterima = uang_diterima;
        this.cs = cs;
        this.kasir = kasir;
        this.status = status;
        this.tgl_transaksi = tgl_transaksi;
    }

    public Integer getId() {
        return id;
    }

    public Cabang getCabang() {
        return cabang;
    }

    public String getJenis() {
        return jenis;
    }

    public Konsumen getKonsumen() {
        return konsumen;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Double getDiskon() {
        return diskon;
    }

    public Double getTotal() {
        return total;
    }

    public Double getUangDiterima() {
        return uang_diterima;
    }

    public Pegawai getCs() {
        return cs;
    }

    public Pegawai getKasir() {
        return kasir;
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
        dest.writeParcelable(this.cabang, flags);
        dest.writeString(this.jenis);
        dest.writeParcelable(this.konsumen, flags);
        dest.writeValue(this.subtotal);
        dest.writeValue(this.diskon);
        dest.writeValue(this.total);
        dest.writeValue(this.uang_diterima);
        dest.writeParcelable(this.cs, flags);
        dest.writeParcelable(this.kasir, flags);
        dest.writeValue(this.status);
        dest.writeString(this.tgl_transaksi);
    }

    protected Penjualan(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cabang = in.readParcelable(Cabang.class.getClassLoader());
        this.jenis = in.readString();
        this.konsumen = in.readParcelable(Konsumen.class.getClassLoader());
        this.subtotal = (Double) in.readValue(Double.class.getClassLoader());
        this.diskon = (Double) in.readValue(Double.class.getClassLoader());
        this.total = (Double) in.readValue(Double.class.getClassLoader());
        this.uang_diterima = (Double) in.readValue(Double.class.getClassLoader());
        this.cs = in.readParcelable(Pegawai.class.getClassLoader());
        this.kasir = in.readParcelable(Pegawai.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tgl_transaksi = in.readString();
    }

    public static final Creator<Penjualan> CREATOR = new Creator<Penjualan>() {
        @Override
        public Penjualan createFromParcel(Parcel source) {
            return new Penjualan(source);
        }

        @Override
        public Penjualan[] newArray(int size) {
            return new Penjualan[size];
        }
    };
}
