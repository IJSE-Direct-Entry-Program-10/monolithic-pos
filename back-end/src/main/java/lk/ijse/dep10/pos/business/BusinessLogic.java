package lk.ijse.dep10.pos.business;

import lk.ijse.dep10.pos.dao.CustomerDAO;
import lk.ijse.dep10.pos.dao.impl.CustomerDAOImpl;
import lk.ijse.dep10.pos.dto.CustomerDTO;
import lk.ijse.dep10.pos.entity.Customer;

import java.sql.Connection;

public class BusinessLogic {

    public static CustomerDTO saveCustomer(CustomerDTO c) throws Exception {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        Connection connection = null;
        customerDAO.setConnection(connection);
        Customer customerEntity = new Customer(c.getName(), c.getAddress(), c.getContact());
        Customer customer = customerDAO.saveCustomer(customerEntity);
        c.setId(customer.getId());
        connection.close();
        return c;
    }
}
