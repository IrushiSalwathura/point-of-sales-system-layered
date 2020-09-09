package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    public List<Item> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
        ArrayList<Item> items = new ArrayList<>();
        while(rst.next()){
            items.add(new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4)));
        }
        return items;
    }

    public Item find(String pk) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE id=?");
        while(rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
        }
        return null;
    }

    public boolean save(Item entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?)",entity.getCode(),entity.getDescription(),entity.getUnitPrice(),entity.getUnitPrice());
    }

    public boolean update(Item entity) throws Exception {
        return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getCode());
    }

    public boolean delete(String pk) throws Exception {
        return CrudUtil.execute("DELETE FROM Item WHERE code=?",pk);
    }

    public String getLastItemCode() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item ORDER BY code DESC LIMIT 1 ");
        if(rst.next()){
            return null;
        }else{
            return rst.getString(1);
        }
    }
}
