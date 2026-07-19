package com.campusstore.order_service.event;

import java.time.LocalDateTime;

public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String createdAt;

    public OrderCreatedEvent(){}

    public OrderCreatedEvent(Long orderId,Long productId,Integer quantity,String createdAt){
        this.orderId=orderId;
        this.productId=productId;
        this.quantity=quantity;
        this.createdAt=createdAt;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
