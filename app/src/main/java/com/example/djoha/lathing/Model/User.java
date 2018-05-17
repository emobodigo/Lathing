package com.example.djoha.lathing.Model;

public class User {
    private String username, Nama, email, alamat, kontak, password, foto, user_Id;
    private int post;

    public User(String user_ID, String username, String Nama, String email, String alamat, String kontak, String password, String foto, int post) {
        this.username = username;
        this.Nama = Nama;
        this.email = email;
        this.alamat = alamat;
        this.kontak = kontak;
        this.password = password;
        this.foto = foto;
        this.post = post;
        this.user_Id = user_ID;
    }

    public User() {
    }

    public String getUserId() {
        return user_Id;
    }

    public void setUserId(String userId) {
        this.user_Id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", Nama='" + Nama + '\'' +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                ", kontak='" + kontak + '\'' +
                ", password='" + password + '\'' +
                ", foto='" + foto + '\'' +
                ", post=" + post +
                '}';
    }
}
