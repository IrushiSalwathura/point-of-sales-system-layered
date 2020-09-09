package dao.custom;

import business.SuperBO;
import dao.SuperDAO;
import entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<CustomEntity> searchOrder() throws Exception;
}
