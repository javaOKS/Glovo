package com.example.Glovo.controller;

import com.example.Glovo.model.Order;
import com.example.Glovo.model.Product;
import com.example.Glovo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        return this.orderService.getOrder(id);
    }
    @GetMapping("/products")
    public List<Product> getProducts(){
        return this.orderService.getProducts();
    }
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id){
        this.orderService.deleteOrder(id);
    }
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id){
        this.orderService.deleteProduct(id);
    }
    @PostMapping("/product")
    public void saveProduct(@RequestBody Product product){
        this.orderService.saveProduct(product);
    }
    @PostMapping
    public void save(@RequestBody Order order){
        this.orderService.save(order);
    }
    @PutMapping("/product/{id}")
    public void updateProduct(@PathVariable int id,@RequestBody Product product){
        this.orderService.updateProduct(id,product);
    }
    @PutMapping("/{id}")
    public void updateOrder(@PathVariable int id,@RequestBody Order order){
        this.orderService.updateOrder(id,order);
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return this.orderService.getAllOrders();
    }
}
