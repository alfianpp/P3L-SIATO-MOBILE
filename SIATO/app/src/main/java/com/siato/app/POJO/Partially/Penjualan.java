package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class Penjualan implements Parcelable {
    private Integer id;
    private Cabang cabang;
    private String Jenis;
    private Konsumen konsumen;
    private Double subtotal;
    private Double diskon;
    private Double total;
    private Double uang_diterima;
    private Pegawai pegawai;
    private Integer id_kasir;
    private Integer status;
    private String tglTransaksi;

    public Penjualan(Integer id, Cabang cabang, String jenis, Konsumen konsumen, Double subtotal, Double diskon, Double total, Double uang_diterima, Pegawai pegawai, Integer id_kasir, Integer status, String tglTransaksi) {
        this.id = id;
        this.cabang = cabang;
        Jenis = jenis;
        this.konsumen = konsumen;
        this.subtotal = subtotal;
        this.diskon = diskon;
        this.total = total;
        this.uang_diterima = uang_diterima;
        this.pegawai = pegawai;
        this.id_kasir = id_kasir;
        this.status = status;
        this.tglTransaksi = tglTransaksi;
    }

    public Integer getId() {
        return id;
    }

    public Cabang getCabang() {
        return cabang;
    }

    public String getJenis() {
        return Jenis;
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

    public Double getUang_diterima() {
        return uang_diterima;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public Integer getId_kasir() {
        return id_kasir;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.cabang,flag);
        dest.writeString(this.Jenis);
        dest.writeParcelable(this.konsumen,flag);
        dest.writeValue(this.subtotal);
        dest.writeValue(this.total);
        dest.writeValue(this.uang_diterima);
        dest.writeParcelable(this.pegawai,flag);
        dest.writeValue(this.id_kasir);
        dest.writeValue(this.status);
        dest.writeString(this.tglTransaksi);
    }

    protected Penjualan(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cabang = in.readParcelable(Cabang.class.getClassLoader());
        this.Jenis = in.readString();
        this.konsumen = in.readParcelable(Konsumen.class.getClassLoader());
        this.subtotal = (Double) in.readValue(Double.class.getClassLoader());
        this.total = (Double) in.readValue(Double.class.getClassLoader());
        this.uang_diterima = (Double) in.readValue(Double.class.getClassLoader());
        this.pegawai = in.readParcelable(Pegawai.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id_kasir = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tglTransaksi = in.readString();
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
