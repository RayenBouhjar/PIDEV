/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import models.Pack;
import services.ServicePack;

/**
 *
 * @author Rednaks
 */
public class CRUDTest {
    public static void main(String[] args) {
        Pack p = new Pack("Baguette", 1.20F);
        Pack p1 = new Pack("Coka", 1.40F);
        
        
        //Ajout
        ServicePack sp = new ServicePack();
        sp.ajouter(p);
        sp.ajouter(p1);
        //Afficher
        sp.afficher().forEach(System.out::println);
        
        p = sp.afficher().get(0);
        p1 = sp.afficher().get(1);
        //Supprimer
        sp.supprimer(p1);
        //Afficher
        sp.afficher().forEach(System.out::println);
        //modifier
        p.setNom("Coka");
        sp.modifier(p);
         //Afficher
        sp.afficher().forEach(System.out::println);
    }
}
