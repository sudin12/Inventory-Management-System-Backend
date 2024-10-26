package com.inigne.ims.controller;

import com.inigne.ims.model.Transaction;
import com.inigne.ims.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@Valid @RequestBody Transaction transaction) {
        Long productId = transaction.getProduct().getId();
        int quantitySold = transaction.getQuantitySold() != null ? transaction.getQuantitySold() : 0;
        double tax = transaction.getTax() != null ? transaction.getTax() : 0.0;
        double discount = transaction.getDiscount() != null ? transaction.getDiscount() : 0.0;
        Transaction newTransaction = transactionService.addTransaction(transaction, productId, quantitySold, tax, discount);
        return ResponseEntity.ok(newTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}