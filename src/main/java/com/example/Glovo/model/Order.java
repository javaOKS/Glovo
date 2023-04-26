package com.example.Glovo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class Order {
    private int id;
    private Date date;
    private BigDecimal cost;
    private List<Product> products;

}
