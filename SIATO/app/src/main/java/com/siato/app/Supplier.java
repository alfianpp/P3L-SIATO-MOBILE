package com.siato.app;

import org.w3c.dom.Text;

class Supplier {
    private Integer id;
    private String nama;
    private String alamat;
    private String nama_sales;
    private String nomor_telepon_sales;

    public Supplier(Integer id, String nama, String alamat, String nama_sales, String nomor_telepon_sales) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.nama_sales = nama_sales;
        this.nomor_telepon_sales = nomor_telepon_sales;
    }

    public Supplier() {

    }

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNama_sales() {
        return nama_sales;
    }

    public String getNomor_telepon_sales() {
        return nomor_telepon_sales;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNama_sales(String nama_sales) {
        this.nama_sales = nama_sales;
    }

    public void setNomor_telepon_sales(String nomor_telepon_sales) {
        this.nomor_telepon_sales = nomor_telepon_sales;
    }
}
