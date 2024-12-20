package com.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:C:/Users/AmaTek/eclipse-workspace/IHMPROJET/src/DBfiles/vehicle_rental.db";
        
        // SQL statements for creating all the tables
        String[] sqlStatements = {
            """
            CREATE TABLE IF NOT EXISTS Vehicule (
                idVehicule INTEGER PRIMARY KEY AUTOINCREMENT,
                marque TEXT NOT NULL,
                modele TEXT NOT NULL,
                annee INTEGER NOT NULL,
                type TEXT NOT NULL,
                carburant TEXT NOT NULL,
                prixLocationJour REAL NOT NULL,
                etat TEXT NOT NULL
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Utilisateur (
                id_utilisateur INTEGER PRIMARY KEY,
                nom TEXT,
                prenom TEXT,
                email TEXT,
                mot_de_passe TEXT,
                role TEXT,
                password TEXT
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Client (
                idClient INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT,
                prenom TEXT,
                email TEXT,
                adresse TEXT,
                numeroTelephone TEXT,
                password TEXT
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Reservation (
                idReservation INTEGER PRIMARY KEY AUTOINCREMENT,
                idClient INTEGER NOT NULL,
                idVehicule INTEGER NOT NULL,
                dateDebut DATE NOT NULL,
                dateFin DATE NOT NULL,
                statut TEXT NOT NULL,
                FOREIGN KEY (idClient) REFERENCES Client(idClient),
                FOREIGN KEY (idVehicule) REFERENCES Vehicule(idVehicule)
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Retour (
                idRetour INTEGER PRIMARY KEY AUTOINCREMENT,
                idReservation INTEGER NOT NULL,
                dateRetour DATE NOT NULL,
                montant REAL NOT NULL,
                FOREIGN KEY (idReservation) REFERENCES Reservation(idReservation)
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Paiement (
                idPaiement INTEGER PRIMARY KEY AUTOINCREMENT,
                idReservation INTEGER NOT NULL,
                datePaiement DATE NOT NULL,
                montant REAL NOT NULL,
                FOREIGN KEY (idReservation) REFERENCES Reservation(idReservation)
            );
            """
        };

        // Establishing the connection and creating the tables
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // Loop through each SQL statement and execute it
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
            System.out.println("All tables created or already exist.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
