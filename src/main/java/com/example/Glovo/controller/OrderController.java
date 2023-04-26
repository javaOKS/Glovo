package com.example.Glovo.controller;

import com.example.Glovo.model.Order;
import com.example.Glovo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    public Order getOrderWithProducts(@PathVariable int id) {
        return orderService.getOrderWithProduct(id);
    }
    @PostMapping
    public void save(@RequestBody Order order){
        orderService.save(order);
    }
    @GetMapping("/product")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
