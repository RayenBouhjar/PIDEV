/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceInterface;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nour
 */
public interface ServiceInterface<T> {
    public boolean add(T t) throws SQLException;
    public boolean delete(int id) throws SQLException;
    public boolean update(T t) throws SQLException;
    public List<T> readAll() throws SQLException;
}
