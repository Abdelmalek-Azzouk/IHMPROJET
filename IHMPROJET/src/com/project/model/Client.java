package com.project.model;

public class Client {
    private int idClient; // Corrected from String to int for consistency with database schema
    private String name;
    private String surname;
    private String email;
    private String password;
    private String numeroTelephone;
    private String address;

    // Constructor
    public Client(int idClient, String name, String surname, String email, String password, String numeroTelephone, String address) {
        this.idClient = idClient;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.numeroTelephone = numeroTelephone;
        this.address = address;
    }

    // Getters and setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
