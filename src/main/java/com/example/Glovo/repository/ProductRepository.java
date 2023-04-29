package com.example.Glovo.repository;

import com.example.Glovo.model.Product;
import com.example.Glovo.repository.mapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String getProducts =
         """
                  SELECT * FROM product
                 """;
    private final String get=
            """
                    SELECT * FROM product WHERE product_id = 
                    """;
    private final String delete =
            """
                DELETE FROM product WHERE product_id = 
                    """;
    private final String save =
            """
                INSERT INTO product (name,cost) VALUES(?,?) 
                    """;
    private final String update =
            """
                    UPDATE product
                    SET name = ?,cost = ?
                    WHERE product_id = ?
                    """;
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Product> getProducts(){
        return  this.jdbcTemplate.query(getProducts, new ProductRowMapper());
        }
    public void deleteProduct(int id){
        this.jdbcTemplate.update(delete + id);
    }
    public Product getProduct(int id) {
        return this.jdbcTemplate.queryForObject(get + id, new ProductRowMapper());
    }
    public void save(Product product){
        this.jdbcTemplate.update(save,product.getName(),product.getCost());
    }
    public void update(int id,Product product){
        this.jdbcTemplate.update(update,product.getName(),product.getCost(),id);
    }

}
