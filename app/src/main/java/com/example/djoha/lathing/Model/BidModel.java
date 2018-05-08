package com.example.djoha.lathing.Model;

public class BidModel {

    private String id_bid, id_penawar, waktu_bid;
    private int jumlah_bid;

    public BidModel(String id_bid, String id_penawar, String waktu_bid, int jumlah_bid) {
        this.id_bid = id_bid;
        this.id_penawar = id_penawar;
        this.waktu_bid = waktu_bid;
        this.jumlah_bid = jumlah_bid;
    }

    public String getId_bid() {
        return id_bid;
    }

    public void setId_bid(String id_bid) {
        this.id_bid = id_bid;
    }

    public String getId_penawar() {
        return id_penawar;
    }

    public void setId_penawar(String id_penawar) {
        this.id_penawar = id_penawar;
    }

    public String getWaktu_bid() {
        return waktu_bid;
    }

    public void setWaktu_bid(String waktu_bid) {
        this.waktu_bid = waktu_bid;
    }

    public int getJumlah_bid() {
        return jumlah_bid;
    }

    public void setJumlah_bid(int jumlah_bid) {
        this.jumlah_bid = jumlah_bid;
    }
}
