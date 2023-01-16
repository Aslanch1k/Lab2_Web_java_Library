package Order;

/**
 * Just builder
 */
public class OrderBuilder extends BaseBuilder{

    @Override
    public Order gerOrder() {
        return order;
    }

    @Override
    public void buildUserId(int userId) {
        order.setUserId(userId);
    }

    @Override
    public void buildOrderData(String orderData) {
        order.setOrderData(orderData);
    }
}
