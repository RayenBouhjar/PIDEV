/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Vues;
import ServiceInterface.ServiceInterface;
import Tools.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VuesService implements ServiceInterface<Vues> {

    private Connection con;
    private Statement ste;

    public VuesService() {
        con = DataBase.getInstance().getConnection();
    }

    @Override
    public boolean add(Vues t) throws SQLException {
        String requeteInsert;
        requeteInsert = "INSERT INTO `ahmedartiste`.`vues` (`id_user`,`id_piste`) VALUES (?,?)";

        PreparedStatement PS = con.prepareStatement(requeteInsert);
        PS.setInt(1, t.getId_user());
        PS.setInt(2, t.getId_piste());

        PS.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Vues t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vues> readAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean Verify(int id_user,int id_piste) throws SQLException {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from vues where id_user='" + id_user + "' and id_piste='"+id_piste+"'");
        while (rs.next()) {
            return true;
        }
        return false;
    }
    
    public int CalculVue(int id_piste) throws SQLException {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from vues where id_piste='"+id_piste+"'");
        int total=0;
        while (rs.next()) {
            total++;
        }
        return total;
    }

}
