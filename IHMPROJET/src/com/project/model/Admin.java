package com.project.model;

public class Admin extends User {

    // Constructor
    public Admin(int idUtilisateur, String nom, String prenom, String email, String motDePasse) {
        super(idUtilisateur, nom, prenom, email, motDePasse, "administrateur");
    }

    // Additional admin-specific methods can be added here
}
