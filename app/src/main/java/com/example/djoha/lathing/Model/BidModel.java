package com.example.djoha.lathing.Model;

public class BidModel {

    private String id_bid, jumlah_bid, id_penawar, waktu_bid;

    public BidModel(String jumlah_bid, String id_penawar, String waktu_bid) {
        this.jumlah_bid = jumlah_bid;
        this.id_penawar = id_penawar;
        this.waktu_bid = waktu_bid;
    }

    public String getJumlah_bid() {
        return jumlah_bid;
    }

    public void setJumlah_bid(String jumlah_bid) {
        this.jumlah_bid = jumlah_bid;
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
}
