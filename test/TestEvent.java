
import java.sql.Date;
import java.util.Calendar;
import models.Artist;
import models.Event;
import services.ServiceEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sadok Laouissi
 */
public class TestEvent {

    public static void main(String[] args) {

        ServiceEvent se = new ServiceEvent();

        Artist a = new Artist();
        a.setId(1);
        Event e = new Event();
        e.setArtist(a);
        e.setCapacity(20);
        e.setDescription("hneee");
        e.setLocation("ghadi");
        e.setName("title");
        e.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        //Ajout
     //   se.ajouter(e);

        //Supprimer
        //se.supprimer(e);
        //Edit
        // e.setId(2);
        // e.setName("khoukha");
        //se.modifier(e);
        //list all
        System.out.println("List All");
       // se.afficher().forEach(System.out::println);
       
       //getBy ID
        System.out.println("One By ID");
        System.out.println(se.getOneById(2));
       
           
       //Get All by artist id
        System.out.println("ALl by artist id");
       se.getAllByArtistId(1).forEach(System.out::println);
    }
}
