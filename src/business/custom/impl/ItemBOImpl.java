package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import entity.Customer;
import entity.Item;
import util.ItemTM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private static ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);

    public String getNewItemCode() throws Exception {
        String lastItemCode = itemDAO.getLastItemCode();
        if(lastItemCode == null){
            return "I001";
        }else{
            int maxId=  Integer.parseInt(lastItemCode.replace("I",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }
    }

    public List<ItemTM> getAllItems() throws Exception {
        List<Item> allItems = itemDAO.findAll();
        ArrayList<ItemTM> items = new ArrayList<>();
        for (Item item : allItems) {
            items.add(new ItemTM(item.getCode(),item.getDescription(),item.getUnitPrice().doubleValue(),item.getQtyOnHand()));
        }
        return items;
    }

    public boolean saveItem(String code, String description, double unitPrice, int qtyOnHand) throws Exception {
        return itemDAO.save(new Item(code,description, BigDecimal.valueOf(unitPrice),qtyOnHand));
    }

    public boolean updateItem(String description, double unitPrice, int qtyOnHand, String code) throws Exception {
        return itemDAO.update(new Item(code,description,BigDecimal.valueOf(unitPrice),qtyOnHand));
    }

    public boolean deleteItem(String itemCode) throws Exception {
        return itemDAO.delete(itemCode);
    }
}
