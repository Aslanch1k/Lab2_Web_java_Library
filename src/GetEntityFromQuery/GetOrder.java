package GetEntityFromQuery;

import Order.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that has methods that allows to get Order data from ResultSet
 * or to put it in Prepared Statement
 */
public class GetOrder {
    public static void getOrder(ResultSet resultSet, Order order) throws SQLException {
        order.setId(resultSet.getInt("id"));
        order.setUserId(resultSet.getInt("userId"));
        order.setOrderData(resultSet.getString("orderData"));
    }

    public static int getOrderAndInsert(PreparedStatement statement, Order order) throws SQLException {
        int resultSet = 0;
        statement.setInt(1, order.getUserId());
        statement.setString(2, order.getOrderData());
        resultSet = statement.executeUpdate();
        return resultSet;
    }
}
