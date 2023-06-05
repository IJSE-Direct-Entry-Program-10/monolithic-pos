package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.OrderCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderCustomerDAO {

    private Connection connection;

    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    public long countOrderCustomers() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM order_customer");
        ResultSet rst = stm.executeQuery();
        rst.next();
        return rst.getLong(1);
    }

    public OrderCustomer saveOrderCustomer(OrderCustomer orderCustomer) throws Exception {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO order_customer (order_id, customer_id) VALUES (?, ?)");
        stm.setInt(1, orderCustomer.getOrderId());
        stm.setInt(2, orderCustomer.getCustomerId());
        stm.executeUpdate();
        return orderCustomer;
    }

    public void updateOrderCustomer(OrderCustomer orderCustomer) throws Exception {
        PreparedStatement stm = connection.prepareStatement("UPDATE order_customer SET customer_id = ? WHERE order_id=?");
        stm.setInt(1, orderCustomer.getCustomerId());
        stm.setInt(2, orderCustomer.getOrderId());
        stm.executeUpdate();
    }

    public void deleteOrderCustomerByOrderId(int orderId) throws Exception {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM order_customer WHERE order_id = ?");
        stm.setInt(1, orderId);
        stm.executeUpdate();
    }

    public Optional<OrderCustomer> findOrderCustomerByOrderId(int orderId) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM order_customer WHERE order_id = ?");
        stm.setInt(1, orderId);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            int customerId = rst.getInt("customer_id");
            OrderCustomer orderCustomer = new OrderCustomer(orderId, customerId);
            return Optional.of(orderCustomer);
        }
        return Optional.empty();
    }

    public List<OrderCustomer> findAllOrderCustomers() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM order_customer");
        ResultSet rst = stm.executeQuery();
        List<OrderCustomer> orderCustomerList = new ArrayList<>();
        while (rst.next()) {
            int orderId = rst.getInt("order_id");
            int customerId = rst.getInt("customer_id");
            OrderCustomer orderCustomer = new OrderCustomer(orderId, customerId);
            orderCustomerList.add(orderCustomer);
        }
        return orderCustomerList;
    }

    public boolean existsOrderCustomerByOrderId(int orderId) throws Exception {
        return findOrderCustomerByOrderId(orderId).isPresent();
    }
}
