package com.inigne.ims.service;

import com.inigne.ims.model.Product;
import com.inigne.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TwilioService twilioService;

    private static final int LOW_STOCK_THRESHOLD = 10;

    // Method to update stock and check for low stock immediately
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setStockQuantity(quantity);
        productRepository.save(product);

        // Check if stock is low after updating
        if (quantity <= LOW_STOCK_THRESHOLD) {
            twilioService.sendLowStockAlert(product.getName(), quantity);
        }
    }

    // Scheduled task to check all products periodically for low stock
    @Scheduled(fixedRate = 600000)
    public void scheduledLowStockCheck() {
        List<Product> lowStockProducts = productRepository.findByStockQuantityLessThanEqual(LOW_STOCK_THRESHOLD);

        for (Product product : lowStockProducts) {
            twilioService.sendLowStockAlert(product.getName(), product.getStockQuantity());
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setReorderLevel(productDetails.getReorderLevel());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
