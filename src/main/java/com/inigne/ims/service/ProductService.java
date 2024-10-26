package com.inigne.ims.service;

import com.inigne.ims.model.Product;
import com.inigne.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


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
        if(product != null){
            product.setName(productDetails.getName());
            product.setPrice(product.getPrice());
            product.setStockQuantity(product.getStockQuantity());
            product.setReorderLevel(product.getReorderLevel());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
