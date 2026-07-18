package com.campusstore.product_service.service;

import com.campusstore.product_service.model.Product;
import com.campusstore.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }

    public void reduceStock(Long id, int quantity) {
        Product product = getProductById(id);
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product id: " + id);
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }
}