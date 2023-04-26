package com.example.Glovo.service;

import com.example.Glovo.model.Order;
import com.example.Glovo.model.OrderWithProduct;
import com.example.Glovo.model.Product;
import com.example.Glovo.repository.OrderWithProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderService {
    private final OrderWithProductRepository orderWithProductRepository;

    public OrderService( OrderWithProductRepository orderWithProductRepository) {
        this.orderWithProductRepository = orderWithProductRepository;
    }
    public Order getOrderWithProduct(int id){
        List<OrderWithProduct> orderWithProducts = this.orderWithProductRepository.getByOrderId(id);
        Order order = orderWithProducts.get(0).getOrder();
        List<Product> products = orderWithProducts.stream()
                .map(OrderWithProduct::getProduct)
                .toList();
        order.setProducts(products);
        return order;
    }
    public void save(Order order){
        this.orderWithProductRepository.saveOrder(order);
    }

    public List<Order> getAllOrders(){
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<OrderWithProduct> orderWithProducts = this.orderWithProductRepository.getAllOrders();
        Order order = orderWithProducts.get(0).getOrder();
        int index = 0;
        while(orderWithProducts.size() > index){
              if(orderWithProducts.get(index).getOrder().equals(order)){
                  products.add(orderWithProducts.get(index).getProduct());
                  index++;
              }
              else {
                  order.setProducts(products);
                  orders.add(order);
                  order = orderWithProducts.get(index).getOrder();
                  products = new ArrayList<>();
                  products.add(orderWithProducts.get(index).getProduct());
                  index++;
              }
        }
        order.setProducts(products);
        orders.add(order);

        return orders;
   }
}

