package com.example.Glovo.repository.mapper;

import com.example.Glovo.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .id(rs.getInt(1))
                .name(rs.getString(2))
                .cost(rs.getBigDecimal(3))
                .build();
    }
}
