package com.siato.app;

public class Kendaraan {
    private String nomor_kendaraan;
    private String merk;
    private String tipe;
    private String pemilik;

    public Kendaraan(String nomor_kendaraan, String merk, String tipe, String pemilik) {
        this.nomor_kendaraan = nomor_kendaraan;
        this.merk = merk;
        this.tipe = tipe;
        this.pemilik = pemilik;
    }

    public String getNomor_kendaraan() {
        return nomor_kendaraan;
    }

    public void setNomor_kendaraan(String nomor_kendaraan) {
        this.nomor_kendaraan = nomor_kendaraan;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }
}
