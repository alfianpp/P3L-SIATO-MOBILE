package com.siato.app;

import java.util.Date;

public class PengadaanBarang {
    private Integer id;
    private Integer id_supplier;
    private Double total;
    private Integer status;
    private Date Tgl_transaksi;

    public PengadaanBarang(Integer id, Integer id_supplier, Double total, Integer status, Date tgl_transaksi) {
        this.id = id;
        this.id_supplier = id_supplier;
        this.total = total;
        this.status = status;
        Tgl_transaksi = tgl_transaksi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Integer id_supplier) {
        this.id_supplier = id_supplier;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTgl_transaksi() {
        return Tgl_transaksi;
    }

    public void setTgl_transaksi(Date tgl_transaksi) {
        Tgl_transaksi = tgl_transaksi;
    }
}
