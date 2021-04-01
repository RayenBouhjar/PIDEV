/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.Pack;
import models.Participation;
import models.User;
import utils.DataSource;

/**
 *
 * @author aissa
 */
public class ServicePack implements IService<Pack> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Pack t) {
        try {
            String requete = "INSERT INTO pack (nom,prix,description) VALUES (?,?,?)";

            PreparedStatement pst ;
            pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setFloat(2, t.getPrix());
             pst.setString(3, t.getDescription());
            pst.executeUpdate();
            System.out.println("Pack ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }//To change body of generated methods, choose Tools | Templates.
    }
    
    public void subscribe(int id_user,int id_pack) {
        try {
            String requete = "INSERT INTO user_pack (id_user,id_pack) VALUES (?,?)";

            PreparedStatement pst ;
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_user);
            pst.setInt(2, id_pack);
            pst.executeUpdate();
            System.out.println("User-Pack ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(Pack t) {
        try {
            String requete = "DELETE FROM pack WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());

            //
            int deleted = pst.executeUpdate();

            if (deleted > 0) {
                System.out.println("Pack supprimée !");
            } else {
                System.out.println("Nope !");
            }
            
            

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Pack t) {
        try {
            String requete = "UPDATE pack SET nom=?,prix=?,description=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(4, t.getId());
            pst.setString(1, t.getNom());
            pst.setFloat(2, t.getPrix());
            pst.setString(3, t.getDescription());
            pst.executeUpdate();
            System.out.println("Pack modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pack> afficher() {
        List<Pack> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM pack";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Pack(rs.getInt(1), rs.getString(2),rs.getString(4), rs.getFloat(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    
    public List<String> getAllPrices() {
        List<String> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM pack GROUP By `prix`";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(String.valueOf(rs.getFloat(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    
    public int getPackNumberByPrice(float prix) {
        int i=0;

        try {
            String requete = "SELECT Count(*) FROM pack WHERE prix=?";
            
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setFloat(1, prix);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
               
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return i;
    }

    public boolean SendSMS(User u, String m) {
        boolean check = false;
        Twilio.init("ACf548e2d6c0e8fb83bb0d3b68cd8b7e78", "b8f0fe34b4f1320110d4e8fd31d7dfdf");
        
        /* Require getPhone User
       
       for (Participation p : list){
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+216"+p.getUser().getPhone()),
                new com.twilio.type.PhoneNumber("+17136365204"),
                m)
            .create();
        }
         */

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21655727242"),
                new com.twilio.type.PhoneNumber("+14156491056"),
                m)
                .create();
        
        //if(message.getStatus())
        System.out.println(message.getStatus());
        if(Message.Status.FAILED!=message.getStatus()){
            check=true;
        }
        return check;
    }
    

  public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "trackhub22@gmail.com";
        //Your gmail password
        String password = "pidevpidev";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        MimeMessage message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static MimeMessage prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("My First Email from Java App");
            String htmlCode = "<h1> Vous êtes inscrits </h1> <br/> <h2><b>Merci </b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(ServicePack.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
