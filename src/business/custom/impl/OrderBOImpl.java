package business.custom.impl;

import DBConnect.DBConnection;
import business.custom.OrderBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.QueryDAO;
import entity.CustomEntity;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOType.ORDERDETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);

    public boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception {
        try {
            Connection connection = DBConnection.getInstance().geConnection();
            try {
                connection.setAutoCommit(false);
                boolean result = orderDAO.save(new Order(order.getOrderId(), order.getOrderDate(), order.getCustomerId()));
                if (!result) {
                    connection.rollback();
                    return false;
                }
                for (OrderDetailTM orderDetail : orderDetails) {
                    result = orderDetailDAO.save(new OrderDetail(order.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
                    if (!result) {
                        connection.rollback();
                        return false;
                    }

                    Object i = itemDAO.find(orderDetail.getItemCode());
                    Item item = (Item) i;
                    item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                    result = itemDAO.update(item);

                    if (!result) {
                        connection.rollback();
                        return false;
                    }

                    connection.commit();
                    return true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNewOrderId() throws Exception {
        try {
            String lastOrderId = orderDAO.getLastOrderId();
            if (lastOrderId == null){
                return "OD001";
            }else{
                int maxId=  Integer.parseInt(lastOrderId.replace("OD",""));
                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "OD00" + maxId;
                } else if (maxId < 100) {
                    id = "OD0" + maxId;
                } else {
                    id = "OD" + maxId;
                }
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderTM> searchOrder() throws Exception{
        try {
            List<CustomEntity> searchOrders =  queryDAO.searchOrder();
            List<OrderTM> allOrders = new ArrayList<>();
            for (CustomEntity searchOrder : searchOrders) {
                allOrders.add(new OrderTM(searchOrder.getOrderId(),searchOrder.getOrderDate(),
                        searchOrder.getCustomerName()
                        ,searchOrder.getCustomerId(),searchOrder.getTotal()));
            }
            return allOrders;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
