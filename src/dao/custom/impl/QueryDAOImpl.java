package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    public List<CustomEntity> searchOrder() throws Exception {
        ArrayList<CustomEntity> orders = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT O.id, O.date, C.id, C.name, SUM(OD.qty*od.unitPrice)\n" +
                "FROM `Order` O\n" +
                "INNER JOIN Customer C ON O.customerId = C.id\n" +
                "INNER JOIN orderdetail OD ON O.id = OD.orderId\n" +
                "GROUP BY O.id");
        while(rst.next()){
            orders.add(new CustomEntity(rst.getString(1),rst.getDate(2),rst.getString(3),rst.getString(4),rst.getBigDecimal(5)));
        }
        return orders;
    }
}
