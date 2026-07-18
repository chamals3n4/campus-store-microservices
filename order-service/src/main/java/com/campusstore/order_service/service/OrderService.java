package com.campusstore.order_service.service;

import com.campusstore.order_service.client.ProductClient;
import com.campusstore.order_service.dto.OrderRequest;
import com.campusstore.order_service.dto.ProductResponse;
import com.campusstore.order_service.model.Order;
import com.campusstore.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository,ProductClient productClient){
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public Order createOrder(OrderRequest request){
        ProductResponse product = productClient.getProductById(request.getProductId());

        if (product.getStockQuantity() < request.getQuantity()){
            throw new RuntimeException("insufficient stock for product : " + product.getName());
        }

        productClient.reduceStock(request.getProductId(), request.getQuantity());

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // TODO : publish ordercreated event to kafka

        return savedOrder;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }


}
