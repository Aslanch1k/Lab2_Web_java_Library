package Db;

import org.w3c.dom.Entity;
import java.sql.*;
import java.util.List;

/**
 * This class defines abstract dao ahd contains
 * defines functions for interaction with Database
 * (to form sql requests and to transfer data from
 * database to service)
 * @param <T>
 */
public abstract class AbstractDao<T extends Entity> {
    protected Connection connection;

    public abstract List<T> findByParam(String param, String valueOfParam);
    public abstract List<T> findAll();

    public abstract T findEntityById(int id);

    public abstract int delete(int id);

    public abstract boolean delete(T entity);

    public abstract int create(T entity);

    public abstract int update(int id, String param, String valueOfParam);

    public abstract List<T> sortByValue(String value);
    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}