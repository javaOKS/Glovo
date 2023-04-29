package com.example.Glovo.repository.mapper;

import com.example.Glovo.model.Order;
import com.example.Glovo.model.OrderWithProduct;
import com.example.Glovo.model.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderWithProductRowMapper implements RowMapper<OrderWithProduct> {
    @Override
    public OrderWithProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order orderBuild = Order.builder()
                .id(rs.getInt(1))
                .date(rs.getDate(2))
                .cost(rs.getBigDecimal(3))
                .build();
        Product productBuild = Product.builder()
                .id(rs.getInt(4))
                .name(rs.getString(5))
                .cost(rs.getBigDecimal(6))
                .build();

        return OrderWithProduct.builder()
                .order(orderBuild)
                .product(productBuild)
                .build();
    }
}
