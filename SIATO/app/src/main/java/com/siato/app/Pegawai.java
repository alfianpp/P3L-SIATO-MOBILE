package com.siato.app;

public class Pegawai {
    private Integer id;
    private String username;
    private String nama;
    private String nomor_telepon;
    private String alamat;
    private Double gaji;
    private Integer role;
    private String api_key;

    public Pegawai(Integer id, String username, String nama, String nomor_telepon, String alamat, Double gaji, Integer role, String api_key) {
        this.id = id;
        this.username = username;
        this.nama = nama;
        this.nomor_telepon = nomor_telepon;
        this.alamat = alamat;
        this.gaji = gaji;
        this.role = role;
        this.api_key = api_key;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorTelepon() {
        return nomor_telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public Double getGaji() {
        return gaji;
    }

    public Integer getRole() {
        return role;
    }

    public String getApiKey() {
        return api_key;
    }
}
