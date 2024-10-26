package com.inigne.ims.controller;

import com.inigne.ims.model.Purchase;
import com.inigne.ims.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@Validated
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Purchase> addPurchase(@Valid @RequestBody Purchase purchase) {
        Long productId = purchase.getProduct().getId();
        int quantityPurchased = purchase.getQuantityPurchased() != null ? purchase.getQuantityPurchased() : 0;
        double amount = purchase.getAmount() != null ? purchase.getAmount() : 0.0;
        String supplierName = purchase.getSupplierName();
        Purchase newPurchase = purchaseService.addPurchase(purchase, productId, quantityPurchased, amount, supplierName);
        return ResponseEntity.ok(newPurchase);
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable long id) {
        Purchase purchase = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(purchase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}