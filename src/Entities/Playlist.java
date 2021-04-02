/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;


public class Playlist {
    private int id;
    private int id_user;
    private int id_piste;
    private Date date;
    
    private String nom_piste;
    private String nom_album;
    private ImageView image;
    private String description;
    private MediaPlayer media;

    public Playlist() {
    }

    public Playlist(int id_user, int id_piste, Date date) {
        this.id_user = id_user;
        this.id_piste = id_piste;
        this.date = date;
    }

    public Playlist(int id, int id_user, int id_piste, Date date) {
        this.id = id;
        this.id_user = id_user;
        this.id_piste = id_piste;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_piste() {
        return id_piste;
    }

    public void setId_piste(int id_piste) {
        this.id_piste = id_piste;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNom_piste() {
        return nom_piste;
    }

    public void setNom_piste(String nom_piste) {
        this.nom_piste = nom_piste;
    }

    public String getNom_album() {
        return nom_album;
    }

    public void setNom_album(String nom_album) {
        this.nom_album = nom_album;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaPlayer getMedia() {
        return media;
    }

    public void setMedia(MediaPlayer media) {
        this.media = media;
    }

    
    
    @Override
    public String toString() {
        return "Playlist{" + "id=" + id + ", id_user=" + id_user + ", id_piste=" + id_piste + ", date=" + date + ", nom_piste=" + nom_piste + ", nom_album=" + nom_album + '}';
    }
    
    
    
    
    
}
