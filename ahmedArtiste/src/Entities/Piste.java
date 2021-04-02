/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;


public class Piste {
    private int id;
    private String nom;
    private String url_piste;
    private String photo;
    private String description;
    private int id_album;

    private ImageView image;
    private String album;
    private MediaPlayer media;
    
    public Piste() {
    }

    public Piste(String nom, String url_piste, String photo, String description,int id_album) {
        this.nom = nom;
        this.url_piste = url_piste;
        this.photo = photo;
        this.description = description;
        this.id_album=id_album;
    }

    public Piste(int id, String nom, String url_piste, String photo, String description,int id_album) {
        this.id = id;
        this.nom = nom;
        this.url_piste = url_piste;
        this.photo = photo;
        this.description = description;
        this.id_album=id_album;
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

    public String getUrl_piste() {
        return url_piste;
    }

    public void setUrl_piste(String url_piste) {
        this.url_piste = url_piste;
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

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public MediaPlayer getMedia() {
        return media;
    }

    public void setMedia(MediaPlayer media) {
        this.media = media;
    }
    
    
    
}
