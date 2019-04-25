package com.siato.app.POJO.Partially;

public class Supplier {
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
}
