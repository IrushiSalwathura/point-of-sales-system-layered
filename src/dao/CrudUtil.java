package dao;

import DBConnect.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static  <T> T execute(String sql,Object...params) throws SQLException {
        Connection connection = DBConnection.getInstance().geConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        int i=0;
        for (Object param : params) {
            i++;
            pstm.setObject(i,param);
        }
        if(sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }
        return (T) ((Boolean) (pstm.executeUpdate()>0));

    }
}
