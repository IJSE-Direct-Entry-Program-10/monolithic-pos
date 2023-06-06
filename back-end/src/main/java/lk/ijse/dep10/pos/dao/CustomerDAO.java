package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.Customer;

import java.util.List;

public interface CustomerDAO extends SuperDAO<Customer, Integer> {

    List<Customer> findCustomers(String query) throws Exception;
}
