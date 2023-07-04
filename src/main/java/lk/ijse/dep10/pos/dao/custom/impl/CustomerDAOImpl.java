package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.CrudDAOImpl;
import lk.ijse.dep10.pos.dao.custom.CustomerDAO;
import lk.ijse.dep10.pos.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDAOImpl extends CrudDAOImpl<Customer, Integer> implements CustomerDAO {

    @Override
    public List<Customer> findCustomers(String query) throws Exception {
        return null;
    }

    @Override
    public Optional<Customer> findCustomerByIdOrContact(String idOrContact) throws Exception {
        return Optional.empty();
    }

    @Override
    public boolean existsCustomerByContactAndNotId(String contact, Integer id) throws Exception {
        return false;
    }
}
