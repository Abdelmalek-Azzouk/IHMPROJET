package com.project.model;

public class Client extends User {
    private String numeroPermis;

    // Constructor
    public Client(int idUtilisateur, String nom, String prenom, String email, String motDePasse, String numeroPermis) {
        super(idUtilisateur, nom, prenom, email, motDePasse, "client");
        this.numeroPermis = numeroPermis;
    }

    // Getter and Setter for numeroPermis
    public String getNumeroPermis() {
        return numeroPermis;
    }

    public void setNumeroPermis(String numeroPermis) {
        this.numeroPermis = numeroPermis;
    }
}