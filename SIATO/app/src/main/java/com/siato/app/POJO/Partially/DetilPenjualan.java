package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class DetilPenjualan implements Parcelable{
    private Integer id;
    private Penjualan penjualan;
    private Kendaraan kendaraan;
    private Pegawai pegawai;

    public DetilPenjualan(Integer id, Penjualan penjualan, Kendaraan kendaraan, Pegawai pegawai) {
        this.id = id;
        this.penjualan = penjualan;
        this.kendaraan = kendaraan;
        this.pegawai = pegawai;
    }

    public Integer getId() {
        return id;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.penjualan,flag);
        dest.writeParcelable(this.kendaraan,flag);
        dest.writeParcelable(this.pegawai,flag);
    }

    protected DetilPenjualan(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.penjualan = in.readParcelable(Penjualan.class.getClassLoader());
        this.kendaraan = in.readParcelable(Kendaraan.class.getClassLoader());
        this.pegawai = in.readParcelable(Pegawai.class.getClassLoader());
    }

    public static final Creator<DetilPenjualan> CREATOR = new Creator<DetilPenjualan>() {
        @Override
        public DetilPenjualan createFromParcel(Parcel source) {
            return new DetilPenjualan(source);
        }

        @Override
        public DetilPenjualan[] newArray(int size) {
            return new DetilPenjualan[size];
        }
    };
}
