package lk.ijse.dep10.pos.dao.custom.impl;

import lk.ijse.dep10.pos.dao.custom.QueryDAO;
import lk.ijse.dep10.pos.dto.OrderDTO2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QueryDAOImpl implements QueryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<OrderDTO2> findOrdersByQuery(String query) throws Exception {
        String sql = "SELECT CONCAT('OD', LPAD(o.id, 3, 0))          as orderId, " +
                "       o.datetime as orderDate, " +
                "       CONCAT('C', LPAD(oc.customer_id, 3, 0)) as customerId, " +
                "       c.name as customerName, " +
                "       SUM(od.qty * od.unit_price)             as orderTotal " +
                "FROM `order` o " +
                "         LEFT OUTER JOIN order_customer oc ON o.id = oc.order_id " +
                "         LEFT OUTER JOIN customer c ON oc.customer_id = c.id " +
                "         INNER JOIN order_detail od ON o.id = od.order_id " +
                "WHERE CONCAT('OD', LPAD(o.id, 3, 0)) LIKE ?1 " +
                "   OR o.datetime LIKE ?2 " +
                "   OR CONCAT('C', LPAD(oc.customer_id, 3, 0)) LIKE ?3 " +
                "   OR c.name LIKE ?4 " +
                "GROUP BY o.id;";
        query = "%" + query + "%";
        List<OrderDTO2> orderList = getSession().createNativeQuery(sql)
                .setParameter(1, query).setParameter(2, query).setParameter(3, query).setParameter(4, query)
                .setResultTransformer(Transformers.aliasToBean(OrderDTO2.class)).list();
        return orderList.stream().peek(orderDTO2 -> {
            if (orderDTO2.getCustomerName() == null) orderDTO2.setCustomerId(null);
        }).collect(Collectors.toList());
    }
}
