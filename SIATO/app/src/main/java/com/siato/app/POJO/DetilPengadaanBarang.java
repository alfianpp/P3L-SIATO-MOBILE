package com.siato.app.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.siato.app.POJO.Partially.PengadaanBarang;
import com.siato.app.POJO.Partially.Spareparts;

public class DetilPengadaanBarang implements Parcelable {
    private Integer id;
    private PengadaanBarang pengadaanBarang;
    private Spareparts spareparts;
    private Integer jmlPesan;
    private Integer jmlDatang;
    private Double harga;

    public DetilPengadaanBarang(Integer id, PengadaanBarang pengadaanBarang, Spareparts spareparts, Integer jmlPesan, Integer jmlDatang, Double harga) {
        this.id = id;
        this.pengadaanBarang = pengadaanBarang;
        this.spareparts = spareparts;
        this.jmlPesan = jmlPesan;
        this.jmlDatang = jmlDatang;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public PengadaanBarang getPengadaanBarang() {
        return pengadaanBarang;
    }

    public Spareparts getSpareparts() {
        return spareparts;
    }

    public Integer getJmlPesan() {
        return jmlPesan;
    }

    public Integer getJmlDatang() {
        return jmlDatang;
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
        dest.writeParcelable(this.pengadaanBarang, flag);
        dest.writeParcelable(this.spareparts,flag);
        dest.writeValue(this.jmlPesan);
        dest.writeValue(this.jmlDatang);
        dest.writeValue(this.harga);
    }

    protected DetilPengadaanBarang(Parcel in){
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pengadaanBarang = in.readParcelable(PengadaanBarang.class.getClassLoader());
        this.spareparts = in.readParcelable(Spareparts.class.getClassLoader());
        this.jmlPesan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.jmlDatang = (Integer) in.readValue(Integer.class.getClassLoader());
        this.harga = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetilPengadaanBarang> CREATOR = new Parcelable.Creator<DetilPengadaanBarang>() {
        @Override
        public DetilPengadaanBarang createFromParcel(Parcel source) {
            return new DetilPengadaanBarang(source);
        }

        @Override
        public DetilPengadaanBarang[] newArray(int size) {
            return new DetilPengadaanBarang[size];
        }
    };
}
