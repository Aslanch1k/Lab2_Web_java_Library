package Db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class EntityTransaction defines transactions
 * and contains methods to manage it
 */
public class EntityTransaction {
    private Connection connection;

    public void initTransaction(AbstractDao dao, AbstractDao... daos) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            //todo add pool of connections and to replace here
            connection = DbConnection.getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            //
        }
        connection = null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            //
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            //
        }
    }
}
