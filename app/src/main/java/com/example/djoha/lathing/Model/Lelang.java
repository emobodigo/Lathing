package com.example.djoha.lathing.Model;

public class Lelang {

    private String akhir_bid, awal_bid, deskripsi, gambar, hargaawal, id_pelelang, nama_barang, nama_pelelang, harga_akhir;

    public Lelang() {
    }

    public Lelang(String akhir_bid, String awal_bid, String deskripsi, String gambar, String hargaawal,
                  String id_pelelang, String nama_barang, String nama_pelelang, String harga_akhir) {
        this.akhir_bid = akhir_bid;
        this.awal_bid = awal_bid;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.hargaawal = hargaawal;
        this.id_pelelang = id_pelelang;
        this.nama_barang = nama_barang;
        this.harga_akhir = harga_akhir;
        this.nama_pelelang = nama_pelelang;
    }

    public String getHarga_akhir() {
        return harga_akhir;
    }

    public void setHarga_akhir(String harga_akhir) {
        this.harga_akhir = harga_akhir;
    }

    public String getAkhir_bid() {
        return akhir_bid;
    }

    public void setAkhir_bid(String akhir_bid) {
        this.akhir_bid = akhir_bid;
    }

    public String getAwal_bid() {
        return awal_bid;
    }

    public void setAwal_bid(String awal_bid) {
        this.awal_bid = awal_bid;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getHargaawal() {
        return hargaawal;
    }

    public void setHargaawal(String hargaawal) {
        this.hargaawal = hargaawal;
    }

    public String getId_pelelang() {
        return id_pelelang;
    }

    public void setId_pelelang(String id_pelelang) {
        this.id_pelelang = id_pelelang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getNama_pelelang() {
        return nama_pelelang;
    }

    public void setNama_pelelang(String nama_pelelang) {
        this.nama_pelelang = nama_pelelang;
    }
}
