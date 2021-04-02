/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Type;
import Entities.User;
import ServiceInterface.ServiceInterface;
import Tools.DataBase;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;

/**
 *
 * @author skand
 */
public class UserService implements ServiceInterface<User> {

    private Connection con;
    private Statement ste;

    public UserService() {
        con = DataBase.getInstance().getConnection();
    }

    @Override
    public boolean add(User t) throws SQLException {
        String requeteInsert;
        requeteInsert = "INSERT INTO `pidev`.`user` (`Nom`,`Prenom`,`Email`,`Password`,`type`,`gouvernorat`,`date_naissance`,`num_tel`,`photo`,`token`,`status`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setString(1, t.getNom());
        PS.setString(2, t.getPrenom());
        PS.setString(3, t.getEmail());
        PS.setString(4, t.getPassword());
        PS.setString(5, t.getType().toString());
        PS.setString(6, t.getGouvernorat());
        PS.setDate(7, t.getDate_naissance());
        PS.setInt(8, t.getNum_tel());
        PS.setString(9, t.getPhoto());
        PS.setString(10, t.getToken());
        PS.setInt(11, t.getStatus());

        PS.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        ste = con.createStatement();
        String query = "delete from user where id=" + id;
        ste.execute(query);
        return true;
    }

    @Override
    public boolean update(User t) throws SQLException {
        String requeteInsert;
        requeteInsert = "update `pidev`.`user` set `Nom`=?,`Prenom`=?,`Email`=?,`Password`=?,`type`=?,`date_naissance`=?,`gouvernorat`=? ,`num_tel`=?,`photo`=? where id=?";

        PreparedStatement PS = con.prepareStatement(requeteInsert);

        PS.setString(1, t.getNom());
        PS.setString(2, t.getPrenom());
        PS.setString(3, t.getEmail());
        PS.setString(4, t.getPassword());
        PS.setString(5, t.getType().toString());
        PS.setDate(6, t.getDate_naissance());
        PS.setString(7, t.getGouvernorat());
        PS.setInt(8, t.getNum_tel());
        PS.setString(9, t.getPhoto());
        PS.setInt(10, t.getId());

        PS.executeUpdate();

        return true;
    }

    @Override
    public List<User> readAll() throws SQLException {
        List<User> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from user");
        while (rs.next()) {
            User u = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), Type.valueOf(rs.getString("type")), rs.getString("email"), rs.getString("gouvernorat"), rs.getDate("date_naissance"), rs.getInt("num_tel"), rs.getInt("block"), rs.getString("photo"), rs.getString("token"), rs.getInt("status"));
            File file;
            file = new File(rs.getString("photo"));
            ImageView imageprofile = ImageViewBuilder.create()
                    .image(new Image(file.toURI().toString()))
                    .build();
            imageprofile.setFitHeight(100);
            imageprofile.setFitWidth(100);
            u.setImg(imageprofile);
            list.add(u);
        }
        return list;
    }

    public User connect(String email, String password) throws SQLException {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where email='" + email + "' and password='" + password + "'");
        while (rs.next()) {
            User u = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), Type.valueOf(rs.getString("type")), rs.getString("email"), rs.getString("gouvernorat"), rs.getDate("date_naissance"), rs.getInt("num_tel"), rs.getInt("block"), rs.getString("photo"), rs.getString("token"), rs.getInt("status"));
            File file;
            file = new File(rs.getString("photo"));
            ImageView imageprofile = ImageViewBuilder.create()
                    .image(new Image(file.toURI().toString()))
                    .build();
            imageprofile.setFitHeight(100);
            imageprofile.setFitWidth(100);
            u.setImg(imageprofile);
            return u;
        }
        return null;
    }

    public User getUserById(int id) throws SQLException {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where id=" + id);
        while (rs.next()) {
            User u = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), Type.valueOf(rs.getString("type")), rs.getString("email"), rs.getString("gouvernorat"), rs.getDate("date_naissance"), rs.getInt("num_tel"), rs.getInt("block"), rs.getString("photo"), rs.getString("token"), rs.getInt("status"));
            return u;
        }
        return null;
    }

    public List<User> search(String str) throws SQLException {
        List<User> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where nom like '" + str + "%' or prenom like '" + str + "%' or num_tel like '" + str + "%'");
        while (rs.next()) {
            User u = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("password"), Type.valueOf(rs.getString("type")), rs.getString("email"), rs.getString("gouvernorat"), rs.getDate("date_naissance"), rs.getInt("num_tel"), rs.getInt("block"), rs.getString("photo"), rs.getString("token"), rs.getInt("status"));
            File file;
            file = new File(rs.getString("photo"));
            ImageView imageprofile = ImageViewBuilder.create()
                    .image(new Image(file.toURI().toString()))
                    .build();
            imageprofile.setFitHeight(100);
            imageprofile.setFitWidth(100);
            u.setImg(imageprofile);
            list.add(u);
        }
        return list;
    }

    public boolean updateState(User t) throws SQLException {
        String requeteInsert;
        requeteInsert = "update `pidev`.`user` set `block`=? where id=?";

        PreparedStatement PS = con.prepareStatement(requeteInsert);

        PS.setInt(1, t.getBlock());
        PS.setInt(2, t.getId());

        PS.executeUpdate();

        return true;
    }

    public boolean Verify(User u, String token) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where token='" + token +"' and id='" + u.getId() + "'");
        int total = 0;
        while (rs.next()) {
            total++;
        }
        if (total == 1) {
            String requeteInsert;
            requeteInsert = "update `pidev`.`user` set `status`=? where id=?";

            PreparedStatement PS = con.prepareStatement(requeteInsert);

            PS.setInt(1, 1);
            PS.setInt(2, u.getId());

            PS.executeUpdate();

            return true;
        }
        return false;

    }
    
    public boolean VerifyEmail(String email) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where email='"+email+"'");
        int total = 0;
        while (rs.next()) {
            total++;
        }
        if (total == 1) {
            
            return true;
        }
        return false;

    }
    
    
}
