package com.example.Glovo.repository;

import com.example.Glovo.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    private final String updateOrderCost =
            """
                    UPDATE "order"
                    SET order_cost = order_cost - (?)
                    WHERE order_id = (?)
                    """;
    private final String deleteOrder =
            """
                    DELETE FROM order_product WHERE order_id = (?);
                    DELETE FROM "order" WHERE order_id = (?)
                    """;
    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void updateOrderCost(BigDecimal productCost, List<Order> orders){
        for (Order order:orders) {
            jdbcTemplate.update(updateOrderCost,productCost,order.getId());
        }
    }
    public void deleteOrder(int id){
        jdbcTemplate.update(deleteOrder,id,id);
    }
}
