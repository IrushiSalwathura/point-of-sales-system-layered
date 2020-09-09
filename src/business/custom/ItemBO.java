package business.custom;

import business.SuperBO;
import util.ItemTM;

import java.util.List;

public interface ItemBO extends SuperBO {
    String getNewItemCode() throws Exception;
    List<ItemTM> getAllItems() throws Exception;
    boolean saveItem(String code, String description, double unitPrice, int qtyOnHand) throws Exception;
    boolean updateItem(String description, double unitPrice, int qtyOnHand, String code) throws Exception;
    boolean deleteItem(String itemCode) throws Exception;
}
