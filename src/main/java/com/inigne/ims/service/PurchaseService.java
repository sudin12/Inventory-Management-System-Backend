package com.inigne.ims.service;

import com.inigne.ims.model.Product;
import com.inigne.ims.model.Purchase;
import com.inigne.ims.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductService productService;

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found with id " + id));
    }

    public Purchase addPurchase(Purchase purchase, Long productId, int quantityPurchased, double amount, String supplierName) {
        Product product = productService.getProductById(productId);
        purchase.setProduct(product);
        purchase.setQuantityPurchased(quantityPurchased);
        purchase.setAmount(amount);
        purchase.setSupplierName(supplierName);
        purchase.calculateTotalAmount(); // Calculate total amount

        int newQuantity = product.getStockQuantity() + quantityPurchased;
        product.setStockQuantity(newQuantity);
        productService.updateProduct(productId, product);

        return purchaseRepository.save(purchase);
    }

    public Purchase updatePurchase(Long id, Purchase updatedPurchase) {
        Purchase existingPurchase = getPurchaseById(id);
        if (existingPurchase != null) {
            existingPurchase.setProduct(updatedPurchase.getProduct());
            existingPurchase.setQuantityPurchased(updatedPurchase.getQuantityPurchased());
            existingPurchase.setAmount(updatedPurchase.getAmount());
            existingPurchase.setDate(updatedPurchase.getDate());
            existingPurchase.setSupplierName(updatedPurchase.getSupplierName());
            existingPurchase.calculateTotalAmount(); // Calculate total amount
            return purchaseRepository.save(existingPurchase);
        } else {
            throw new RuntimeException("Purchase not found with id " + id);
        }
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}