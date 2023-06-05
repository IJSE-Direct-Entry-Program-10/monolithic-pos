package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAO {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public long countItems() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM item");
        ResultSet rst = stm.executeQuery();
        rst.next();
        return rst.getLong(1);
    }

    public Item saveItem(Item item) throws Exception {
        PreparedStatement stm = connection
                .prepareStatement("INSERT INTO item (code, description, qty, unit_price) VALUES (?, ?, ?, ?)");
        stm.setString(1, item.getCode());
        stm.setString(2, item.getDescription());
        stm.setInt(3, item.getQty());
        stm.setBigDecimal(4, item.getUnitPrice());
        stm.executeUpdate();
        return item;
    }

    public void updateItem(Item item) throws Exception {
        PreparedStatement stm = connection
                .prepareStatement("UPDATE item SET description=?, unit_price=?, qty=? WHERE code=?");
        stm.setString(1, item.getDescription());
        stm.setBigDecimal(2, item.getUnitPrice());
        stm.setInt(3, item.getQty());
        stm.setString(4, item.getCode());
        stm.executeUpdate();
    }

    public void deleteItemByCode(String code) throws Exception {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM item WHERE code=?");
        stm.setString(1, code);
        stm.executeUpdate();
    }

    public Optional<Item> findItemByCode(String code) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
        stm.setString(1, code);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            String description = rst.getString("description");
            int qty = rst.getInt("qty");
            BigDecimal unitPrice = rst.getBigDecimal("unit_price");
            Item item = new Item(code, description, qty, unitPrice);
            return Optional.of(item);
        }
        return Optional.empty();
    }

    public List<Item> findAllItems() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM item");
        ResultSet rst = stm.executeQuery();
        List<Item> itemList = new ArrayList<>();
        while (rst.next()) {
            String code = rst.getString("code");
            String description = rst.getString("description");
            int qty = rst.getInt("qty");
            BigDecimal unitPrice = rst.getBigDecimal("unit_price");
            Item item = new Item(code, description, qty, unitPrice);
            itemList.add(item);
        }
        return itemList;
    }

    public boolean existsItemByCode(String code) throws Exception {
        return findItemByCode(code).isPresent();
    }
}
