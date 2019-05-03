package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.Kendaraan;
import com.siato.app.POJO.Partially.Pegawai;

public class DetailPenjualan implements Parcelable {
    private Integer id;
    private Integer id_penjualan;
    private Kendaraan kendaraan;
    private Pegawai montir;

    public DetailPenjualan(Integer id, Integer id_penjualan, Kendaraan kendaraan, Pegawai montir) {
        this.id = id;
        this.id_penjualan = id_penjualan;
        this.kendaraan = kendaraan;
        this.montir = montir;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdPenjualan() {
        return id_penjualan;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public Pegawai getMontir() {
        return montir;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.id_penjualan);
        dest.writeParcelable(this.kendaraan, flags);
        dest.writeParcelable(this.montir, flags);
    }

    protected DetailPenjualan(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id_penjualan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.kendaraan = in.readParcelable(Kendaraan.class.getClassLoader());
        this.montir = in.readParcelable(Pegawai.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailPenjualan> CREATOR = new Parcelable.Creator<DetailPenjualan>() {
        @Override
        public DetailPenjualan createFromParcel(Parcel source) {
            return new DetailPenjualan(source);
        }

        @Override
        public DetailPenjualan[] newArray(int size) {
            return new DetailPenjualan[size];
        }
    };
}
