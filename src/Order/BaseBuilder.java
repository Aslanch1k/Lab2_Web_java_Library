package Order;

/**
 * Abstract builder
 */
public abstract class BaseBuilder {
    protected Order order = new Order();
    public abstract Order gerOrder();
    public abstract void buildUserId(int userId);
    public abstract void buildOrderData(String orderData);
}
