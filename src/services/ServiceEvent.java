/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import models.Artist;
import models.Event;
import utils.DataSource;

/**
 *
 * @author Sadok Laouissi
 */
public class ServiceEvent implements IService<Event> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Event t) {
        try {
            String requete = "INSERT INTO event (`id_artist`, `localisation`, `date`, `prix`, `capacity`, `event_name`, `event_desc`)  VALUES (?,?,?,?,?,?,?)";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getArtist().getId());
            pst.setString(2, t.getLocation());
            pst.setDate(3, t.getDate());
            pst.setFloat(4, t.getPrice());
            pst.setInt(5, t.getCapacity());
            pst.setString(6, t.getName());
            pst.setString(7, t.getDescription());
            pst.executeUpdate();
            System.out.println("event ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Event t) {
        try {
            String requete = "DELETE FROM event WHERE id_event=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());

            //
            int deleted = pst.executeUpdate();

            if (deleted > 0) {
                System.out.println("Event supprimée !");
            } else {
                System.out.println("Nope !");
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Event t) {
        try {
            String requete = "UPDATE event SET localisation=?,"
                    + "date=?,prix=?, capacity=?, event_name=?"
                    + ", event_desc=? WHERE id_event=?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(7, t.getId());
            pst.setString(1, t.getLocation());
            pst.setDate(2, t.getDate());
            pst.setFloat(3, t.getPrice());
            pst.setInt(4, t.getCapacity());
            pst.setString(5, t.getName());
            pst.setString(6, t.getDescription());
            pst.executeUpdate();
            System.out.println("Event modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public List<Event> afficher() {
        List<Event> list = new ArrayList<>();
        Artist a = new Artist();
        try {
            String requete = "SELECT * FROM event";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a.setId(rs.getInt(2));
                Event e = new Event();
                e.setId(rs.getInt(1));
                e.setArtist(a);
                e.setLocation(rs.getString(3));
                e.setDate(rs.getDate(4));
                e.setPrice(rs.getFloat(5));
                e.setCapacity(rs.getInt(6));
                e.setName(rs.getString(7));
                e.setDescription(rs.getString(8));
                
                list.add(e);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }

    
    
    public Event getOneById(int id){
        Event e = new Event();
        Artist a = new Artist();
        try {
            String requete = "SELECT * FROM event WHERE id_event=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a.setId(rs.getInt(2));
                e.setId(rs.getInt(1));
                e.setArtist(a);
                e.setLocation(rs.getString(3));
                e.setDate(rs.getDate(4));
                e.setPrice(rs.getFloat(5));
                e.setCapacity(rs.getInt(6));
                e.setName(rs.getString(7));
                e.setDescription(rs.getString(8));
                
                
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return e;
    }
    
    public List<Event> getAllByArtistId(int artist_id) {
        List<Event> list = new ArrayList<>();
        Artist a = new Artist();
        try {
            String requete = "SELECT * FROM event Where id_artist=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,artist_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a.setId(rs.getInt(2));
                Event e = new Event();
                e.setId(rs.getInt(1));
                e.setArtist(a);
                e.setLocation(rs.getString(3));
                e.setDate(rs.getDate(4));
                e.setPrice(rs.getFloat(5));
                e.setCapacity(rs.getInt(6));
                e.setName(rs.getString(7));
                e.setDescription(rs.getString(8));
                
                list.add(e);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    
    public List<Event> getAllParticipedEventsByUserId(int user_id) {
        List<Event> list = new ArrayList<>();
        Artist a = new Artist();
        try {
            String requete = "SELECT e.*, p.* FROM EVENT e JOIN participation p "
                    + "ON p.id_event = e.id_event JOIN user u ON u.id = p.id_user WHERE u.id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,user_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                a.setId(rs.getInt(2));
                Event e = new Event();
                e.setId(rs.getInt(1));
                e.setArtist(a);
                e.setLocation(rs.getString(3));
                e.setDate(rs.getDate(4));
                e.setPrice(rs.getFloat(5));
                e.setCapacity(rs.getInt(6));
                e.setName(rs.getString(7));
                e.setDescription(rs.getString(8));
                
                list.add(e);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
}
