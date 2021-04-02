/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;
import javafx.scene.image.ImageView;


public class User {
    private int id;
    private String nom;
    private String prenom;
    private String password;
    private Type type;
    private String email;
    private String gouvernorat;
    private Date date_naissance;
    private int num_tel;
    private int block;
    private String photo;
    private String token;
    private int status;
    
    private ImageView img;

    public User(int id, String nom, String prenom, String password, Type type, String email, String gouvernorat,Date date_naissance,int num_tel,int block,String photo,String token,int status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.type = type;
        this.email = email;
        this.gouvernorat = gouvernorat;
        this.date_naissance=date_naissance;
        this.num_tel=num_tel;
        this.block=block;
        this.photo=photo;
        this.token=token;
        this.status=status;
    }

    public User( String nom, String prenom, String password, Type type, String email, String gouvernorat,Date date_naissance,int num_tel,String photo,String token,int status) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.type = type;
        this.email = email;
        this.gouvernorat = gouvernorat;
        this.date_naissance=date_naissance;
        this.num_tel=num_tel;
        this.photo=photo;
        this.token=token;
        this.status=status;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    
}
