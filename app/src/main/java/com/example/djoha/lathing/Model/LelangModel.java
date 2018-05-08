package com.example.djoha.lathing.Model;

public class LelangModel {

    private String nama_barang;
    private String akhir_bid;
    private String awal_bid;
    private String id_pelelang;
    private String gambar;
    private String deskripsi;
    private String harga_awal;
    private String harga_akhir;
    private String nama_pelelang;


    public LelangModel(String nama_barang, String akhir_bid, String awal_bid, String id_pelelang, String gambar, String deskripsi, String harga_awal, String harga_akhir, String nama_pelelang) {
        this.nama_barang = nama_barang;
        this.akhir_bid = akhir_bid;
        this.awal_bid = awal_bid;
        this.id_pelelang = id_pelelang;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.harga_awal = harga_awal;
        this.harga_akhir = harga_akhir;
        this.nama_pelelang = nama_pelelang;
    }

    public String getNama_pelelang() {
        return nama_pelelang;
    }

    public void setNama_pelelang(String nama_pelelang) {
        this.nama_pelelang = nama_pelelang;
    }

    public String getAwal_bid() {
        return awal_bid;
    }

    public void setAwal_bid(String awal_bid) {
        this.awal_bid = awal_bid;
    }

    public String getHarga_akhir() {
        return harga_akhir;
    }

    public void setHarga_akhir(String harga_akhir) {
        this.harga_akhir = harga_akhir;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getAkhir_bid() {
        return akhir_bid;
    }

    public void setAkhir_bid(String akhir_bid) {
        this.akhir_bid = akhir_bid;
    }

    public String getId_pelelang() {
        return id_pelelang;
    }

    public void setId_pelelang(String id_pelelang) {
        this.id_pelelang = id_pelelang;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga_awal() {
        return harga_awal;
    }

    public void setHarga_awal(String harga_awal) {
        this.harga_awal = harga_awal;
    }
}
