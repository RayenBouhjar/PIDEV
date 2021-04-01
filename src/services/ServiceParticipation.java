/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;
import com.twilio.rest.api.v2010.account.recording.AddOnResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import models.Event;
import models.Participation;
import models.User;
import utils.DataSource;

/**
 *
 * @author Sadok Laouissi
 */
public class ServiceParticipation implements IService<Participation> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServiceEvent serviceEvent = new ServiceEvent();
    public static final String ACCOUNT_SID = System.getenv("ACce7522459d72541a5b32ffbec74b50b0");
    public static final String AUTH_TOKEN = System.getenv("3153251426949b7d248a6c2c6f0fe685");

    @Override
    public void ajouter(Participation t) {

        try {
            String requete = "INSERT INTO participation (id_event,id_user,date_participation) VALUES (?,?,?)";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getEvent().getId());
            pst.setInt(2, t.getUser().getId());
            pst.setDate(3, t.getDate());
            pst.executeUpdate();
            System.out.println("Participation ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Participation t) {
        try {
            String requete = "DELETE FROM participation WHERE id_participation=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());

            //
            int deleted = pst.executeUpdate();

            if (deleted > 0) {
                System.out.println("Participation supprimée !");
            } else {
                System.out.println("Nope !");
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void supprimerByIdUserAndEvent(int id_user,int id_event) {
        try {
            String requete = "DELETE FROM participation WHERE id_user=? AND id_event=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_user);
            pst.setInt(2, id_event);
            //
            int deleted = pst.executeUpdate();

            if (deleted > 0) {
                System.out.println("Participation supprimée !");
            } else {
                System.out.println("Nope !");
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Participation t) {

    }

    @Override
    public List<Participation> afficher() {
        List<Participation> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM participation";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Participation p = new Participation();
                Event e = serviceEvent.getOneById(rs.getInt(2));
                /*Need User Service*/
                User user = new User();
                user.setId(rs.getInt(3));
                user.setNom("Laouissi");
                user.setPrenom("Sadok");
                /*Need User Service*/
                p.setEvent(e);
                p.setId(rs.getInt(1));
                p.setUser(user);
                p.setDate(rs.getDate(4));
                list.add(p);
                //  list.add(new Participation(rs.getInt(1), rs.getString(2), rs.getFloat(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }

    public Participation getOneById(int id) {
        Participation p = new Participation();

        try {
            String requete = "SELECT * FROM participation where id_participation=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Event e = serviceEvent.getOneById(rs.getInt(2));
                /*Need User Service*/
                User user = new User();
                user.setId(rs.getInt(3));
                user.setNom("Laouissi");
                user.setPrenom("Sadok");
                /*Need User Service*/
                p.setEvent(e);
                p.setId(rs.getInt(1));
                p.setUser(user);
                p.setDate(rs.getDate(4));

                //  list.add(new Participation(rs.getInt(1), rs.getString(2), rs.getFloat(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return p;
    }

    public List<Participation> getAllPartsByEventId(int id) {
        List<Participation> list = new ArrayList<>();
        

        try {
            String requete = "SELECT * FROM participation where id_event=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Participation p = new Participation();
                Event e = serviceEvent.getOneById(rs.getInt(2));
                /*Need User Service*/
                User user = new User();
                user.setId(rs.getInt(3));
                user.setNom("Laouissi");
                user.setPrenom("Sadok");
                /*Need User Service*/
                p.setEvent(e);
                p.setId(rs.getInt(1));
                p.setUser(user);
                p.setDate(rs.getDate(4));

               list.add(p);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    public Participation getPartByEventAndUserId(int id_event,int id_user) {
        Participation p = new Participation();
        

        try {
            String requete = "SELECT * FROM participation where id_event=? AND id_user=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_event);
            pst.setInt(2, id_user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                
                Event e = serviceEvent.getOneById(rs.getInt(2));
                /*Need User Service*/
                User user = new User();
                user.setId(rs.getInt(3));
                user.setNom("Chelli");
                user.setPrenom("Sarah");
                /*Need User Service*/
                p.setEvent(e);
                p.setId(rs.getInt(1));
                p.setUser(user);
                p.setDate(rs.getDate(4));

               
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return p;
    }
    public void ParticipationToPDF(Participation p) {

        try {
            System.out.println("Pdf Started...");
            String filepath = "C:\\Generated_files\\";
            String fileName = p.getEvent().getName().trim() + "_"
                    + p.getEvent().getDate() + "_"
                    + p.getUser().getNom() + "_" + p.getUser().getPrenom() + ".pdf";

            String file = filepath + fileName;
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL);

            Font RowFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);

            Chunk titre = new Chunk(p.getEvent().getName(), chapterFont);

            Paragraph description = new Paragraph("Description : " + p.getEvent().getDescription(), paragraphFont);
            Paragraph date = new Paragraph("Date d'évenement : " + p.getEvent().getDate().toString(), paragraphFont);
            Paragraph localisation = new Paragraph("Localisation : " + p.getEvent().getLocation(), paragraphFont);

            document.add(titre);
            document.add(description);
            document.add(date);
            localisation.setSpacingAfter(20f);
            document.add(localisation);

            //PDF TABLE
            PdfPTable table = new PdfPTable(2);

            //First Row
            PdfPCell c1 = new PdfPCell(new Phrase("Nom", RowFont));
            table.addCell(c1);
            PdfPCell nom = new PdfPCell(new Phrase(p.getUser().getNom()));
            table.addCell(nom);

            //Second Row
            PdfPCell c2 = new PdfPCell(new Phrase("Prénom", RowFont));
            table.addCell(c2);
            PdfPCell prenom = new PdfPCell(new Phrase(p.getUser().getPrenom()));
            table.addCell(prenom);

            //Third Row
            PdfPCell c3 = new PdfPCell(new Phrase("Date de participation", RowFont));
            table.addCell(c3);
            PdfPCell dateP = new PdfPCell(new Phrase(p.getDate().toString()));
            table.addCell(dateP);

            table.setSpacingAfter(50f);
            document.add(table);

            //Add Logo
            document.add(Image.getInstance(filepath + "logo.jpg"));

            document.close();
            System.out.println("Pdf finished...");

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public boolean notifyAllParticipants(List<Participation> list, String m) {
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
        if(Status.FAILED!=message.getStatus()){
            check=true;
        }
        return check;
    }

    public boolean exportParticipantsToExcell(List<Participation> list) {
        boolean check = false;
        WritableWorkbook workbook = null;
        String filepath = "C:\\Generated_files\\";
        String fileName = list.get(0).getEvent().getName().trim() + "_"
                + list.get(0).getEvent().getDate() + "_Participants" + ".xls";
        System.out.println("Excell Started...");
        try {
            /* On créé un nouveau worbook et on l'ouvre en écriture */
            workbook = Workbook.createWorkbook(new File(filepath + fileName));

            /* On créé une nouvelle feuille (test en position 0) et on l'ouvre en écriture */
            WritableSheet sheet = workbook.createSheet("Participants", 0);

            Font RowFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);

            /*Header*/
            Label nom = new Label(0, 0, "Nom");
            sheet.addCell(nom);
            Label prenom = new Label(1, 0, "Prénom");
            sheet.addCell(prenom);
            Label date = new Label(2, 0, "Date Participation");
            sheet.addCell(date);

            /* Insert Participants */
            for (int j = 0; j < list.size(); j++) {

                sheet.addCell(new Label(0, j + 1, list.get(j).getUser().getNom()));
                sheet.addCell(new Label(1, j + 1, list.get(j).getUser().getPrenom()));
                sheet.addCell(new Label(2, j + 1, list.get(j).getDate().toString()));

            }

            /* On ecrit le classeur */
            workbook.write();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                /* On ferme le worbook pour libérer la mémoire */
                try {
                    workbook.close();
                    check = true;
                    System.out.println("Excell Finished...");
                } catch (WriteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return check;
    }

}
