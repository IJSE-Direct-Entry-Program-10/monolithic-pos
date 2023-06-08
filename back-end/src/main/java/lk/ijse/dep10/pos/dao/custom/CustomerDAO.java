package lk.ijse.dep10.pos.dao.custom;

import lk.ijse.dep10.pos.dao.CrudDAO;
import lk.ijse.dep10.pos.entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, Integer> {

    List<Customer> findCustomers(String query) throws Exception;

    default boolean existsCustomerByContact(String contact) throws Exception {
        throw new IllegalStateException("Method is yet to be implemented");
    }
}
