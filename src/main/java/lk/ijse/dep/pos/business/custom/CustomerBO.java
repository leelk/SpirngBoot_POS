package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {

    void saveCustomer(CustomerDTO customer);

    void updateCustomer(CustomerDTO customer);

    void deleteCustomer(String customerId) ;

    List<CustomerDTO> findAllCustomers() ;

    String getLastCustomerId();

    CustomerDTO findCustomer(String customerId) ;

    List<String> getAllCustomerIDs() ;

}
