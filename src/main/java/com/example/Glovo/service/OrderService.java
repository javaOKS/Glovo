package com.example.Glovo.service;

import com.example.Glovo.model.Order;
import com.example.Glovo.model.OrderWithProduct;
import com.example.Glovo.model.Product;
import com.example.Glovo.repository.OrderRepository;
import com.example.Glovo.repository.OrderWithProductRepository;
import com.example.Glovo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderService {
    private final OrderWithProductRepository orderWithProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderWithProductRepository orderWithProductRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderWithProductRepository = orderWithProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    public Order getOrder(int id){
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
    public void deleteOrder(int id){
        this.orderRepository.deleteOrder(id);
    }
    public List<Product> getProducts(){
        return this.productRepository.getProducts();
    }
    public void saveProduct(Product product){
        this.productRepository.save(product);
    }
    public void updateProduct(int id,Product product){
        this.productRepository.update(id,product);
    }
    public void updateOrder(int id,Order order){
        this.orderWithProductRepository.update(id,order);
    }

    public void deleteProduct(int id){
        List<Order> orders = this.orderWithProductRepository.getOrdersToUpdate(id);
        Product product = this.productRepository.getProduct(id);
        this.orderRepository.updateOrderCost(product.getCost(),orders);
        this.orderWithProductRepository.delete(id);
        this.productRepository.deleteProduct(id);
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

