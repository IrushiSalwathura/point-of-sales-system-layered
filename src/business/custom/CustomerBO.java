package business.custom;

import business.SuperBO;
import util.CustomerTM;

import java.util.List;

public interface CustomerBO extends SuperBO {
    String getNewCustomerId() throws Exception;
    List<CustomerTM> getAllCustomers() throws Exception;
    boolean saveCustomer(String id, String name, String address) throws Exception;
    boolean updateCustomer(String name, String address, String id) throws Exception;
    boolean deleteCustomer(String customerId) throws Exception;
}
