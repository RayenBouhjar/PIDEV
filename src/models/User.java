/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Sadok Laouissi
 */
public class User {
    private int id;
    private String nom,prenom;
    private int phone;

    public User(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(String nom, String prenom, int phone) {
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
    }

    
    public User(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + '}';
    }
    
    
}
