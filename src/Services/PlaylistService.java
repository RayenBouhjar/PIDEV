/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Piste;
import Entities.Playlist;
import IService.IService;
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

public class PlaylistService implements IService<Playlist> {

    private Connection con;
    private Statement ste;

    public PlaylistService() {
        con = DataBase.getInstance().getConnection();
    }

    @Override
    public void add(Playlist t) throws SQLException {
        String requeteInsert;
        requeteInsert = "INSERT INTO `ahmedartiste`.`playlist` (`id_user`,`id_piste`,`date`) VALUES (?,?,?)";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setInt(1, t.getId_user());
        PS.setInt(2, t.getId_piste());
        PS.setDate(3, t.getDate());

        PS.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        ste = con.createStatement();
        String query = "delete from playlist where id=" + id;
        ste.execute(query);
    }

    @Override
    public void update(Playlist t) throws SQLException {
        String requeteInsert;
        requeteInsert = "update `ahmedartiste`.`playlist` set `id_user`=?,`id_piste`=?,`date`=? where id=?";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setInt(1, t.getId_user());
        PS.setInt(2, t.getId_piste());
        PS.setDate(3, t.getDate());
        PS.setInt(4, t.getId());

        PS.executeUpdate();
    }

    @Override
    public List<Playlist> readAll() throws SQLException {
        List<Playlist> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from playlist");
        while (rs.next()) {
            Playlist p = new Playlist(rs.getInt("id"), rs.getInt("id_user"), rs.getInt("id_piste"), rs.getDate("date"));
            list.add(p);
        }
        return list;
    }

    @Override
    public Playlist getById(int id) throws SQLException {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from playlist where id='" + id + "'");
        while (rs.next()) {
            Playlist p = new Playlist(rs.getInt("id"), rs.getInt("id_user"), rs.getInt("id_piste"), rs.getDate("date"));
            return p;
        }
        return null;
    }

    public List<Playlist> readByUser(int id_user) throws SQLException {
        List<Playlist> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select playlist.* , piste.nom ,album.nom , piste.photo,piste.description,piste.url_piste from playlist inner join piste on piste.id=playlist.id_piste inner join album on album.id=piste.id_album where id_user='" + id_user + "'");
        while (rs.next()) {
            Playlist p = new Playlist(rs.getInt("id"), rs.getInt("id_user"), rs.getInt("id_piste"), rs.getDate("date"));
            p.setNom_album(rs.getString("album.nom"));
            p.setNom_piste(rs.getString("piste.nom"));
            p.setDate(rs.getDate("date"));
            File file;
            file = new File(rs.getString("photo"));
            ImageView image = ImageViewBuilder.create()
                    .image(new Image(file.toURI().toString()))
                    .build();
            image.setFitHeight(100);
            image.setFitWidth(100);
            image.setCache(true);
            p.setImage(image);

            p.setDescription(rs.getString("description"));

            Media sound = new Media(new File(rs.getString("url_piste")).toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            p.setMedia(player);

            list.add(p);
        }
        return list;
    }

    public List<Piste> readByAlbumNom(String nom) throws SQLException {
        List<Piste> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select piste.*,album.nom from piste inner join album on piste.id_album=album.id where album.nom='" + nom + "'");
        while (rs.next()) {
            Piste a = new Piste(rs.getInt("id"), rs.getString("nom"), rs.getString("url_piste"), rs.getString("photo"), rs.getString("description"), rs.getInt("id_album"));
            a.setAlbum(rs.getString("album.nom"));
            System.out.println(rs.getString("album.nom"));
            File file;
            file = new File(rs.getString("photo"));
            ImageView image = ImageViewBuilder.create()
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
    
    public boolean Verify(int id_piste) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from playlist where id_piste='" + id_piste + "'");
        while (rs.next()) {
            return true;
        }
        return false;
    }

}
