package com.campusstore.order_service.client;

import com.campusstore.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service",url = "http://localhost:8082")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);

    @PutMapping("/products/{id}/reduce-stock")
    void reduceStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}
