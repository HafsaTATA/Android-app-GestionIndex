package com.firstapp.mtix.model;

public class Client {
    private int id;
    private String nomPrenom;

    public Client(int id, String nomPrenom) {
        this.id = id;
        this.nomPrenom = nomPrenom;
    }

    public int getId() {
        return id;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }
}
