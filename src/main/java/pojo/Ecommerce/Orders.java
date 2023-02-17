package pojo.Ecommerce;

import java.util.List;

public class Orders {

    private List<OrderDetails> orderDetails;

    public List<OrderDetails> getOrders() {
        return orderDetails;
    }

    public void setOrders(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
