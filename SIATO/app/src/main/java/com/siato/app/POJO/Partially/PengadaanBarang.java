package com.siato.app.POJO.Partially;

import android.os.Parcel;
import android.os.Parcelable;

public class PengadaanBarang implements Parcelable{
    private Integer id;
    private Supplier supplier;

    public PengadaanBarang(Integer id, Supplier supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.supplier, flags);
    }

    protected PengadaanBarang(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.supplier = in.readParcelable(Supplier.class.getClassLoader());
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
