package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.util.JdbcTemplate;
import lk.ijse.dep10.pos.dao.custom.CustomerDAO;
import lk.ijse.dep10.pos.dao.util.RowMapper;
import lk.ijse.dep10.pos.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection connection;
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper = ((rs, rowNum) -> {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String contact = rs.getString("contact");
        return new Customer(id, name, address, contact);
    });

    public void setConnection(Connection connection) {
        this.connection = connection;
        jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public long count() throws Exception {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customer", Long.class);
    }

    @Override
    public Customer save(Customer customer) throws Exception {
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

    @Override
    public void update(Customer customer) throws Exception {
        jdbcTemplate.update("UPDATE customer SET name=?, address=?, contact=? WHERE id=?",
                customer.getName(), customer.getAddress(), customer.getContact(), customer.getId());
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        jdbcTemplate.update("DELETE FROM customer WHERE id=?", id);
    }

    public Optional<Customer> findById(Integer id) throws Exception {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("SELECT * FROM customer WHERE id=?",customerRowMapper, id));
    }

    public List<Customer> findAll() throws Exception {
        return jdbcTemplate.query("SELECT * FROM customer", customerRowMapper);
    }

    public boolean existsById(Integer id) throws Exception {
        return findById(id).isPresent();
    }

    public List<Customer> findCustomers(String query) throws Exception {
        return jdbcTemplate.query("SELECT * FROM customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ? OR contact LIKE ?",
                customerRowMapper, "%" + query + "%");
    }
}
