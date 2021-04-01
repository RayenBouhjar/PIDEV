/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author aissa
 */
public class Pack {

    private int id;
    private String nom,description;
    private float prix;
    

    public Pack(int id, String nom, float prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public Pack(String nom, String description, float prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public Pack(int id, String nom, String description, float prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public Pack(String nom, float prix) {
        this.nom = nom;
        this.prix = prix;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Pack{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", prix=" + prix + '}';
    }

}
