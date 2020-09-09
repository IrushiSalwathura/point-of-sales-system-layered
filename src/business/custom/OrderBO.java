package business.custom;

import business.SuperBO;
import util.OrderDetailTM;
import util.OrderTM;

import java.util.List;

public interface OrderBO extends SuperBO {
    boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception;
    String getNewOrderId() throws Exception;
    List<OrderTM> searchOrder() throws Exception;
}
