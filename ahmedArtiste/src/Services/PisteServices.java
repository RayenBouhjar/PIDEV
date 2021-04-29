/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Album;
import Entities.Categorie;
import Entities.Piste;
import ServiceInterface.ServiceInterface;
import Tools.DataBase;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class PisteServices implements ServiceInterface<Piste> {

    private Connection con;
    private Statement ste;

    public PisteServices() {
        con = DataBase.getInstance().getConnection();
    }

    @Override
    public boolean add(Piste t) throws SQLException {
        String requeteInsert;
        requeteInsert = "INSERT INTO `ahmedartiste`.`piste` (`Nom`,`url_piste`,`photo`,`description`,`id_album`) VALUES (?,?,?,?,?)";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setString(1, t.getNom());
        PS.setString(2, t.getUrl_piste());
        PS.setString(3, t.getPhoto());
        PS.setString(4, t.getDescription());
        PS.setInt(5, t.getId_album());

        PS.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        ste = con.createStatement();
        String query = "delete from piste where id=" + id;
        ste.execute(query);
        return true;
    }

    @Override
    public boolean update(Piste t) throws SQLException {
        String requeteInsert;
        requeteInsert = "UPDATE  `ahmedartiste`.`piste` set `Nom`=?,`piste`=?,`photo`=?,`description`=?,`id_album`=? where id=?";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setString(1, t.getNom());
        PS.setString(2, t.getUrl_piste());
        PS.setString(3, t.getPhoto());
        PS.setString(4, t.getDescription());
        PS.setInt(5, t.getId_album());
        PS.setInt(6, t.getId());

        PS.executeUpdate();

        return true;
    }

    @Override
    public List<Piste> readAll() throws SQLException {
        List<Piste> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select piste.*,album.nom from piste inner join album on piste.id_album=album.id");
        while (rs.next()) {
            Piste a = new Piste(rs.getInt("id"), rs.getString("nom"), rs.getString("url_piste"), rs.getString("photo"), rs.getString("description"), rs.getInt("id_album"));
            a.setAlbum(rs.getString("album.nom"));

            File file;
            file = new File(rs.getString("photo"));
            ImageView image=ImageViewBuilder.create()
                .image(new Image(file.toURI().toString()))
                .build();
            image.setFitHeight(100);
            image.setFitWidth(100);
            image.setCache(true);
            a.setImage(image);
            
            Media sound = new Media(new File(rs.getString("url_piste")).toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            a.setMedia(player);

            list.add(a);
        }
        return list;
    }

    public List<String> getAlbums() throws SQLException {
        List<String> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from album");
        while (rs.next()) {
            list.add(rs.getString("nom"));
        }
        return list;
    }

    public int getAlbumByNom(String nom) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from album where nom='" + nom + "'");
        while (rs.next()) {
            return rs.getInt("id");
        }
        return 0;
    }
    
    public List<Piste> readByAlbumNom(String nom) throws SQLException {
        List<Piste> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select piste.*,album.nom from piste inner join album on piste.id_album=album.id where album.nom='"+nom+"'");
        while (rs.next()) {
            Piste a = new Piste(rs.getInt("id"), rs.getString("nom"), rs.getString("url_piste"), rs.getString("photo"), rs.getString("description"), rs.getInt("id_album"));
            a.setAlbum(rs.getString("album.nom"));
            System.out.println(rs.getString("album.nom"));
            File file;
            file = new File(rs.getString("photo"));
            ImageView image=ImageViewBuilder.create()
                .image(new Image(file.toURI().toString()))
                .build();
            image.setFitHeight(100);
            image.setFitWidth(100);
            image.setCache(true);
            a.setImage(image);
            Media sound = new Media(new File(rs.getString("url_piste")).toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            a.setMedia(player);

            list.add(a);
        }
        return list;
    }
    
    public List<Piste> readByAlbumId(int id_album) throws SQLException {
        List<Piste> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from piste where piste.id_album='"+id_album+"'");
        while (rs.next()) {
            Piste a = new Piste(rs.getInt("id"), rs.getString("nom"), rs.getString("url_piste"), rs.getString("photo"), rs.getString("description"), rs.getInt("id_album"));
            
            list.add(a);
        }
        return list;
    }
    

}
