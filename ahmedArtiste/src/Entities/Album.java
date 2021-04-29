/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javafx.scene.image.ImageView;


public class Album {
    private int id;
    private String nom;
    private Categorie categorie;
    private String photo;
    private String description;
    private int id_artiste;
    
    
    private ImageView image;

    public Album() {
    }

    public Album(String nom, Categorie categorie, String photo, String description, int id_artiste) {
        this.nom = nom;
        this.categorie = categorie;
        this.photo = photo;
        this.description = description;
        this.id_artiste = id_artiste;
    }

    public Album(int id, String nom, Categorie categorie, String photo, String description, int id_artiste) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.photo = photo;
        this.description = description;
        this.id_artiste = id_artiste;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    
    
}
