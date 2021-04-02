/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;


public class Vues {
    private int id;
    private int id_user;
    private int id_piste;

    public Vues() {
    }

    public Vues(int id_user, int id_piste) {
        this.id_user = id_user;
        this.id_piste = id_piste;
    }

    public Vues(int id, int id_user, int id_piste) {
        this.id = id;
        this.id_user = id_user;
        this.id_piste = id_piste;
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
    
    
    
}
