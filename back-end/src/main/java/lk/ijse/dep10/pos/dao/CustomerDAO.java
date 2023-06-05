package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO {

    private Connection connection;

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public long countCustomers() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM customer");
        ResultSet rst = stm.executeQuery();
        rst.next();
        return rst.getLong(1);
    }

    public Customer saveCustomer(Customer customer) throws Exception {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (name, address, contact) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, customer.getName());
        stm.setString(2, customer.getAddress());
        stm.setString(3, customer.getContact());
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        generatedKeys.next();
        customer.setId(generatedKeys.getInt(1));
        return customer;
    }

    public void updateCustomer(Customer customer) throws Exception {
        PreparedStatement stm = connection.prepareStatement("UPDATE customer SET name=?, address=?, contact=? WHERE id=?");
        stm.setString(1, customer.getName());
        stm.setString(2, customer.getAddress());
        stm.setString(3, customer.getContact());
        stm.setInt(4, customer.getId());
        stm.executeUpdate();
    }

    public void deleteCustomerById(int id) throws Exception {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    public Optional<Customer> findCustomerById(int id) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
        stm.setInt(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            String name = rst.getString("name");
            String contact = rst.getString("contact");
            String address = rst.getString("address");
            Customer customer = new Customer(id, name, address, contact);
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    public List<Customer> findAllCustomers() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer");
        ResultSet rst = stm.executeQuery();
        List<Customer> customerList = new ArrayList<>();
        while (rst.next()) {
            int id = rst.getInt("id");
            String name = rst.getString("name");
            String contact = rst.getString("contact");
            String address = rst.getString("address");
            Customer customer = new Customer(id, name, address, contact);
            customerList.add(customer);
        }
        return customerList;
    }

    public boolean existsCustomerById(int id) throws Exception {
        return findCustomerById(id).isPresent();
    }
}
