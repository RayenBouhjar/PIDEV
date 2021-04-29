/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Album;
import Entities.Categorie;
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


public class AlbumService implements ServiceInterface<Album>{

    private Connection con;
    private Statement ste;

    public AlbumService() {
        con = DataBase.getInstance().getConnection();
    }
    
    @Override
    public boolean add(Album t) throws SQLException {
        String requeteInsert;
        requeteInsert = "INSERT INTO `ahmedartiste`.`album` (`Nom`,`Categorie`,`photo`,`description`,`id_artiste`) VALUES (?,?,?,?,?)";
          
        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setString(1, t.getNom());
        PS.setString(2, String.valueOf(t.getCategorie()));
        PS.setString(3, t.getPhoto());
        PS.setString(4, t.getDescription());
        PS.setInt(5,t.getId_artiste());

        PS.executeUpdate();
        
        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        ste=con.createStatement();
        String query="delete from album where id="+id;
        ste.execute(query);
        return true;
    }

    @Override
    public boolean update(Album t) throws SQLException {
        String requeteInsert;
        requeteInsert = "UPDATE `ahmedartiste`.`album` set `Nom`=?,`Categorie`=?,`photo`=?,`description`=?,`id_artiste`=? where id=?";
          
        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setString(1, t.getNom());
        PS.setString(2, String.valueOf(t.getCategorie()));
        PS.setString(3, t.getPhoto());
        PS.setString(4, t.getDescription());
        PS.setInt(5,t.getId_artiste());
        PS.setInt(6,t.getId());

        PS.executeUpdate();
        
        return true;
    }

    @Override
    public List<Album> readAll() throws SQLException {
        List<Album> list= new ArrayList<>();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from album");
        while (rs.next()) {     
            File file;
            file = new File(rs.getString("photo"));
            ImageView image=ImageViewBuilder.create()
                .image(new Image(file.toURI().toString()))
                .build();
            image.setFitHeight(100);
            image.setFitWidth(100);
            image.setCache(true);
            
            Album a=new Album(rs.getInt("id"), rs.getString("nom"),  Categorie.valueOf(rs.getString("categorie")),  rs.getString("photo"), rs.getString("description"),  rs.getInt("id_artiste"));
            a.setImage(image);
            list.add(a);
        }
        return list; 
    }
    
    public List<Album> readByArtiste(int id_artiste) throws SQLException {
        List<Album> list= new ArrayList<>();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from album where id_artiste="+id_artiste);
        while (rs.next()) {     
            File file;
            file = new File(rs.getString("photo"));
            ImageView image=ImageViewBuilder.create()
                .image(new Image(file.toURI().toString()))
                .build();
            image.setFitHeight(100);
            image.setFitWidth(100);
            
            Album a=new Album(rs.getInt("id"), rs.getString("nom"),  Categorie.valueOf(rs.getString("categorie")),  rs.getString("photo"), rs.getString("description"),  rs.getInt("id_artiste"));
            a.setImage(image);
            list.add(a);
        }
        return list; 
    }
    
}
