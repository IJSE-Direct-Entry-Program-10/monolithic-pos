package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.custom.ItemDAO;
import lk.ijse.dep10.pos.dao.util.JdbcTemplate;
import lk.ijse.dep10.pos.dao.util.RowMapper;
import lk.ijse.dep10.pos.entity.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    private Connection connection;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        String code = rs.getString("code");
        String description = rs.getString("description");
        int qty = rs.getInt("qty");
        BigDecimal unitPrice = rs.getBigDecimal("unit_price");
        new Item(code, description, qty, unitPrice);
    };

    public void setConnection(Connection connection) {
        this.connection = connection;
        jdbcTemplate = new JdbcTemplate(connection);
    }

    public long count() throws Exception {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM item", Long.class);
    }

    public Item save(Item item) throws Exception {
        jdbcTemplate.update("INSERT INTO item (code, description, qty, unit_price) VALUES (?, ?, ?, ?)",
                item.getCode(), item.getDescription(), item.getQty(), item.getUnitPrice());
        return item;
    }

    public void update(Item item) throws Exception {
        jdbcTemplate.update("UPDATE item SET description=?, unit_price=?, qty=? WHERE code=?",
                item.getDescription(), item.getUnitPrice(), item.getQty(), item.getCode());
    }

    public void deleteById(String code) throws Exception {
        jdbcTemplate.update("DELETE FROM item WHERE code=?", code);
    }

    public Optional<Item> findById(String code) throws Exception {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("SELECT * FROM item WHERE code=?", itemRowMapper, code));
    }

    public List<Item> findAll() throws Exception {
        return jdbcTemplate.query("SELECT * FROM item", itemRowMapper);
    }

    public boolean existsById(String code) throws Exception {
        return findById(code).isPresent();
    }
}
