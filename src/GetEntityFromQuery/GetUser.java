package GetEntityFromQuery;

import User.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that has methods that allows to get User data from ResultSet
 * or to put it in Prepared Statement
 */
public class GetUser {

    public static void getUser(ResultSet resultSet, User user) throws SQLException {
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setStatus(resultSet.getString("status"));
        user.setBlockStatus(resultSet.getBoolean("blockStatus"));
        user.setFine(resultSet.getInt("fine"));
    }

    public static int getUserAndInsert(PreparedStatement statement, User user) throws SQLException {
        int resultSet = 0;
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getStatus());
        resultSet = statement.executeUpdate();
        return resultSet;
    }
}
