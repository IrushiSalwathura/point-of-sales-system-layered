package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public List<Customer> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while(rst.next()){
            customers.add(new Customer(rst.getString(1),rst.getString(2),
                    rst.getString(3)));
        }
        return customers;
    }

    public Customer find(String pk) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE id=?");
        while(rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),
                    rst.getString(3));
        }
        return null;
    }

    public boolean save(Customer entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?)",entity.getId(),
                entity.getName(),entity.getAddress());
    }

    public boolean delete(String pk) throws Exception {
        return CrudUtil.execute("DELETE FROM Customer WHERE id=?",pk);
    }

    public boolean update(Customer entity) throws Exception {
        return CrudUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?",entity.getName(),entity.getAddress(),entity.getId());
    }

    public String getLastCustomerId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer ORDER BY id DESC LIMIT 1");
        if(!rst.next()){
            return null;
        }else{
            return rst.getString(1);
        }

    }
}