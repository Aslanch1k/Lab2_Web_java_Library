package Db.DAOs;

import Db.AbstractDao;
import GetEntityFromQuery.GetOrder;
import Order.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *DAO for managing Orders Data
 */
public class OrderDao extends AbstractDao<Order> {

    private static final String SQL_SELECT_ORDER_BY_ID = """
            SELECT * FROM orders WHERE id = ?
            """;
    private static final String SQL_SELECT_ALL_ORDERS = """
            SELECT * FROM orders
            """;
    private static final String SQL_INSERT_ORDER = """
            INSERT INTO orders(
                               userId, orderData
            ) VALUES (?, ?)
            """;

    private static final String SQL_DELETE_ORDER = """
            DELETE FROM orders WHERE id = ?
            """;
    @Override
    public List<Order> findByParam(String param, String valueOfParam) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                GetOrder.getOrder(resultSet, order);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return orders;
    }

    @Override
    public Order findEntityById(int id) {
        Order order = new Order();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            GetOrder.getOrder(resultSet, order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return order;
    }

    @Override
    public int delete(int id) {
        int resultSet;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_DELETE_ORDER);
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
        return 0;
    }

    @Override
    public List<Order> sortByValue(String value) {
        return null;
    }

    @Override
    public int create(Order entity) {
        int resultSet;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_INSERT_ORDER);
            resultSet = GetOrder.getOrderAndInsert(statement, entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public boolean delete(Order entity) {
        return false;
    }
}
