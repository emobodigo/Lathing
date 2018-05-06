package com.example.djoha.lathing.Model;

public class LelangModel {

    private String nama_barang, akhir_bid, id_pelelang, gambar, deskripsi, harga_awal;

    public LelangModel(String nama_barang, String akhir_bid, String id_pelelang, String gambar, String deskripsi, String harga_awal) {
        this.nama_barang = nama_barang;
        this.akhir_bid = akhir_bid;
        this.id_pelelang = id_pelelang;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.harga_awal = harga_awal;
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
