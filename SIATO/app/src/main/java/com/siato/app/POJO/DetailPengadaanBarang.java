package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.Spareparts;

public class DetailPengadaanBarang implements Parcelable {
    private Integer id;
    private Integer id_pengadaan_barang;
    private Spareparts spareparts;
    private Integer jumlah_pesan;
    private Integer jumlah_datang;
    private Double harga;

    public DetailPengadaanBarang(Integer id, Integer id_pengadaan_barang, Spareparts spareparts, Integer jumlah_pesan, Integer jumlah_datang, Double harga) {
        this.id = id;
        this.id_pengadaan_barang = id_pengadaan_barang;
        this.spareparts = spareparts;
        this.jumlah_pesan = jumlah_pesan;
        this.jumlah_datang = jumlah_datang;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdPengadaanBarang() {
        return id_pengadaan_barang;
    }

    public Spareparts getSpareparts() {
        return spareparts;
    }

    public Integer getJumlahPesan() {
        return jumlah_pesan;
    }

    public Integer getJumlahDatang() {
        return jumlah_datang;
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
        dest.writeValue(this.id_pengadaan_barang);
        dest.writeParcelable(this.spareparts, flags);
        dest.writeValue(this.jumlah_pesan);
        dest.writeValue(this.jumlah_datang);
        dest.writeValue(this.harga);
    }

    protected DetailPengadaanBarang(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id_pengadaan_barang = (Integer) in.readValue(Integer.class.getClassLoader());
        this.spareparts = in.readParcelable(Spareparts.class.getClassLoader());
        this.jumlah_pesan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.jumlah_datang = (Integer) in.readValue(Integer.class.getClassLoader());
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<DetailPengadaanBarang> CREATOR = new Creator<DetailPengadaanBarang>() {
        @Override
        public DetailPengadaanBarang createFromParcel(Parcel source) {
            return new DetailPengadaanBarang(source);
        }

        @Override
        public DetailPengadaanBarang[] newArray(int size) {
            return new DetailPengadaanBarang[size];
        }
    };
}
