package com.siato.app.POJO;

import com.siato.app.POJO.Partially.Supplier;

public class PengadaanBarang {
    private Integer id;
    private Double total;
    private Integer status;
    private String tgl_transaksi;
    private Supplier supplier;

    public PengadaanBarang(Integer id, Double total, Integer status, String tgl_transaksi, Supplier supplier) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.tgl_transaksi = tgl_transaksi;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public Double getTotal() {
        return total;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public Supplier getSupplier() {
        return supplier;
    }
}

