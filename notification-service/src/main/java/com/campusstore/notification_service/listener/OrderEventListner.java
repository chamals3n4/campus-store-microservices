package com.campusstore.notification_service.listener;

import com.campusstore.notification_service.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListner {

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void handleOrderCreated(OrderCreatedEvent event){
        System.out.println("=================================");
        System.out.println("NOTIFICATION: New order received!");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Product ID: " + event.getProductId());
        System.out.println("Quantity: " + event.getQuantity());
        System.out.println("Placed at: " + event.getCreatedAt());
        System.out.println("(Simulated: confirmation email sent)");
        System.out.println("=================================");
    }
}
