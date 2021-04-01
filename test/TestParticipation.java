
import java.util.Calendar;
import models.Participation;
import models.User;
import services.ServiceEvent;
import services.ServiceParticipation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sadok Laouissi
 */
public class TestParticipation {

    public static void main(String[] args) {
        ServiceParticipation sp = new ServiceParticipation();
        ServiceEvent se = new ServiceEvent();
        User user = new User();
        user.setId(1);
        user.setNom("laouissi");
        user.setPrenom("sadok");
        
        
        Participation p = new Participation();
        p.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        p.setUser(user);
        p.setEvent(se.getOneById(3));
        
        //Add
       /* System.out.println("Ajouter");
        sp.ajouter(p);
        sp.ajouter(p);

        //Supprimer
        p.setId(1);
        System.out.println("Supprimer");
        sp.supprimer(p);
        */
        //Lister
        System.out.println("Lister");
        sp.afficher().forEach(System.out::println);
        
        
        //getOne
        System.out.println("Get One By id...");
        Participation pr = sp.getOneById(2);
        System.out.println(pr);
        
        //To PDF
        User u = new User();
        u.setNom("Laouissi");
        u.setPrenom("Sadok");
        pr.setUser(u);
        sp.ParticipationToPDF(pr);
        
        //Notify all
    //    sp.notifyAllParticipants(sp.afficher(),"Test");
        
        //Export to Excell
       // sp.exportParticipantsToExcell(sp.afficher());
       
      // se.getAllParticipedEventsByUserId(2).forEach(System.out::println);
      
     // sp.supprimerByIdUserAndEvent(1, 2);
    }
}
