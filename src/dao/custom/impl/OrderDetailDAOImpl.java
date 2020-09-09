package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    public List<OrderDetail> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * from OrderDetail");
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        while(rst.next()){
            orderDetails.add(new OrderDetail(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getBigDecimal(4)));
        }
        return orderDetails;
    }

    public OrderDetail find(String pk) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM OrderDetail WHERE id=?");
        while(rst.next()){
            return new OrderDetail(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getBigDecimal(4));
        }
        return null;
    }

    public boolean save(OrderDetail entity) throws Exception {
        return CrudUtil.execute("INSERT INTO OrderDetail VALUES (?,?,?,?)",entity.getOrderId(),entity.getItemCode(),entity.getQty(),entity.getUnitPrice());
    }

    public boolean update(OrderDetail entity) throws Exception {
        return CrudUtil.execute("UPDATE OrderDetail SET qty=?,uniPrice=? WHERE orderId=? AND itemCode=?",entity.getQty(),entity.getUnitPrice(),entity.getOrderId(),entity.getItemCode());
    }

    public boolean delete(String pk) throws Exception {
        return CrudUtil.execute("DELETE FROM OrderDetail WHERE id=?",pk);
    }
}
