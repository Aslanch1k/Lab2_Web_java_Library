package Db.DAOs;
import Db.AbstractDao;
import GetEntityFromQuery.GetUser;
import User.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *DAO for managing users Data
 */
public class UserDao extends AbstractDao<User> {

    private static final String SQL_FIND_USER_BY_ID = """
            SELECT * FROM users WHERE id = ?
            """;
    private static final String SQL_DELETE_USER = """
            DELETE FROM users WHERE id = ?
            """;
    private static final String SQL_SELECT_ALL_USERS = """
            SELECT * FROM users
            """;
    private static final String SQL_SELECT_BY_PARAM = """
            SELECT * FROM users WHERE {0} = ?
            """;
    private static final String SQL_CREATE_TABLE_SEAS_TIC = """
            CREATE TABLE seasonticketforuser{0}(
                id MEDIUMINT AUTO_INCREMENT NOT NULL,
                bookId MEDIUMINT,
                startDate DATE NOT NULL DEFAULT(CURRENT_DATE),
                finishDate DATE NOT NULL DEFAULT(DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY )),
                PRIMARY KEY(id)
            )
            """;

    private static final String SQL_DROP_TABLE_SEAS_TIC = """
            DROP TABLE seasonticketforuser{0}
            """;

    private static final String SQL_UPDATE_USER = """
            UPDATE users SET {0} = ? WHERE id = ?
            """;
    private static final String SQL_INSERT_USER = """
            INSERT INTO users(
                              login, password, status
            ) VALUES (?, ?, ?)
            """;

    private static final String SQL_LOG_IN_USER = """
            SELECT * FROM users WHERE (login=?) AND (password=?)
            """;

    public User checkForExisting(String login, String password){
        User user = new User();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_LOG_IN_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            GetUser.getUser(resultSet, user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return user;
    }

    public boolean createTable(String login){
        boolean resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_CREATE_TABLE_SEAS_TIC, login));
            resultSet = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    public boolean deleteTable(int login){
        boolean resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_DROP_TABLE_SEAS_TIC, login));
            resultSet = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public List<User> findByParam(String param, String valueOfParam) {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_SELECT_BY_PARAM, param));
            statement.setString(1, valueOfParam);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                GetUser.getUser(resultSet, user);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return users;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                GetUser.getUser(resultSet, user);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return users;
    }

    @Override
    public User findEntityById(int id) {
        User user = new User();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            GetUser.getUser(resultSet, user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return user;
    }


    @Override
    public int delete(int id) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_USER);
            statement.setInt(1, id);
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public int update(int id, String param, String valueOfParam) {
        int resultSet;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(MessageFormat.format(SQL_UPDATE_USER, param));
            statement.setString(1, valueOfParam);
            statement.setInt(2, id);
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public List<User> sortByValue(String value) {
        return null;
    }

    @Override
    public int create(User entity) {
        int resultSet;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_INSERT_USER);
            resultSet = GetUser.getUserAndInsert(statement, entity);
            createTable(entity.getLogin());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }


    @Override
    public boolean delete(User entity) {
        return false;
    }
}
