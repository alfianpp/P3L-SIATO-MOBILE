package com.siato.app;

public class Kendaraan {
    private String nomor_polisi;
    private String merk;
    private String tipe;
    private String id_pemilik;

    public Kendaraan(String nomor_polisi, String merk, String tipe, String id_pemilik) {
        this.nomor_polisi = nomor_polisi;
        this.merk = merk;
        this.tipe = tipe;
        this.id_pemilik = id_pemilik;
    }

    public String getNomorPolisi() {
        return nomor_polisi;
    }

    public String getMerk() {
        return merk;
    }

    public String getTipe() {
        return tipe;
    }

    public String getIDPemilik() {
        return id_pemilik;
    }
}
