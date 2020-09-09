package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    public List<Order> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `Order`");
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()){
            orders.add(new Order(rst.getString(1),rst.getDate(2),(rst.getString(3))));
        }
        return orders;
    }

    public Order find(String pk) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` WHERE id=?");
        while(rst.next()){
            return new Order(rst.getString(1),rst.getDate(2),rst.getString(3));
        }
        return null;
    }

    public boolean save(Order entity) throws Exception {
        return CrudUtil.execute("INSERT INTO `Order` VALUES(?,?,?)",entity.getId(),entity.getDate(),entity.getCustomerId());
    }

    public boolean update(Order entity) throws Exception {
        return CrudUtil.execute("UPDATE `Order` SET date=? WHERE id=? AND customerId=?",entity.getDate(),entity.getId(),entity.getCustomerId());
    }

    public boolean delete(String pk) throws Exception {
        return CrudUtil.execute("DELETE FROM `Order` WHERE id=?",pk);
    }

    public String getLastOrderId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` ORDER BY id DESC LIMIT 1");
        if(!rst.next()){
            return null;
        }else{
            return rst.getString(1);
        }
    }

}
