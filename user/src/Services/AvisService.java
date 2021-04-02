/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Avis;
import Entities.Type;
import Entities.User;
import ServiceInterface.ServiceInterface;
import Tools.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author skand
 */
public class AvisService implements ServiceInterface<Avis> {

    private Connection con;
    private Statement ste;

    public AvisService() {
        con = DataBase.getInstance().getConnection();
    }

    @Override
    public boolean add(Avis t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert;
        requeteInsert = "INSERT INTO `pidev`.`avis` (`Note`,`Description`,`id_user`,`date_creation`) VALUES (?,?,?,?)";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setInt(1, t.getNote());
        PS.setString(2, t.getDescription());
        PS.setInt(3, t.getId_user());
        PS.setInt(4, t.getId_user());

        PS.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        ste = con.createStatement();
        String query = "delete from avis where id=" + id;
        ste.execute(query);
        return true;
    }

    @Override
    public boolean update(Avis t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Avis> readAll() throws SQLException {
        List<Avis> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select avis.*,user.nom from avis inner join user on user.id=avis.id_user");
        while (rs.next()) {
            Avis a = new Avis(rs.getInt("id"), rs.getInt("note"), rs.getString("description"), rs.getInt("id_user"), rs.getDate("date_creation"), rs.getString("nom"));
            list.add(a);
        }
        return list;
    }

    public int CalculeNote(int note) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from archiveavis where note='" + note + "'");
        int total = 0;
        while (rs.next()) {
            total++;
        }
        return total;
    }

    public void Archiver(Avis av) throws SQLException {
        String requeteInsert;
        requeteInsert = "INSERT INTO `pidev`.`archiveavis` (`Note`,`Description`) VALUES (?,?)";
        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setInt(1, av.getNote());
        PS.setString(2, av.getDescription());
        PS.executeUpdate();
        
        this.delete(av.getId());
    }

}
