package com.example.Glovo.repository;

import com.example.Glovo.model.Order;
import com.example.Glovo.model.OrderWithProduct;
import com.example.Glovo.model.Product;
import com.example.Glovo.repository.mapper.OrderRowMapper;
import com.example.Glovo.repository.mapper.OrderWithProductRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderWithProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String saveOrder =
            """
                    INSERT INTO "order" (date,order_cost) VALUES(?,?) RETURNING order_id
                    """;
    public final String saveOrderProduct =
            """
                    INSERT INTO order_product(order_id,product_id) VALUES (?,?)
                    """;
    private final String getAllOrders =
            """
                    SELECT "order".*, product.* FROM "order"
                    	INNER JOIN order_product ON "order".order_id = order_product.order_id
                    	INNER JOIN product ON  order_product.product_id = product.product_id
                    """;
    private final String getByOrderId =
            """
                    SELECT "order".*, product.* FROM "order"
                    	INNER JOIN order_product ON "order".order_id = order_product.order_id
                    	INNER JOIN product ON  order_product.product_id = product.product_id WHERE "order".order_id =  
                    """;
    public OrderWithProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<OrderWithProduct> getByOrderId(int id){
        try {
            return this.jdbcTemplate.query(getByOrderId + id, new OrderWithProductRowMapper());
        }catch (EmptyResultDataAccessException exc){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found",exc);
        }
    }
    public List<OrderWithProduct> getAllOrders(){
        return this.jdbcTemplate.query(getAllOrders,new OrderWithProductRowMapper());
    }
    public void saveOrder(Order order){
        List<Product> products = order.getProducts();
        BigDecimal sum = products.stream()
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            Order orderId = jdbcTemplate.queryForObject(saveOrder, new OrderRowMapper(), order.getDate(), sum);
            for (Product p : products) {
                jdbcTemplate.update(saveOrderProduct, orderId.getId(), p.getId());
            }
    }
}
