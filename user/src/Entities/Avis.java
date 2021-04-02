/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;


public class Avis {
    private int id;
    private int note;
    private String description;
    private int id_user;
    private Date date_creation;
    
    private String nom;

    public Avis() {
    }

    public Avis(int note, String description, int id_user,Date date_creation) {
        this.note = note;
        this.description = description;
        this.id_user = id_user;
        this.date_creation=date_creation;
    }

    public Avis(int id, int note, String description, int id_user,Date date_creation,String nom) {
        this.id = id;
        this.note = note;
        this.description = description;
        this.id_user = id_user;
        this.date_creation=date_creation;
        this.nom=nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
}
