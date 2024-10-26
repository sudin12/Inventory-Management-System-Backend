package com.inigne.ims.service;

import com.inigne.ims.model.Product;
import com.inigne.ims.model.Transaction;
import com.inigne.ims.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductService productService;

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
    }

    public Transaction addTransaction(Transaction transaction, Long productId, int quantitySold, double tax, double discount) {
        Product product = productService.getProductById(productId);
        Double price = product.getPrice();
        if (price != null) {
            double totalAmount = calculateTotalAmount(price, quantitySold, tax, discount);
            transaction.setTotalAmount(totalAmount);
            transaction.setTax(tax);
            transaction.setDiscount(discount);
            transaction.setProduct(product);
            transaction.setQuantitySold(quantitySold);
            if (transaction.getName() == null) {
                transaction.setName(product.getName()); // Set the name field if not provided
            }

            int newQuantity = product.getStockQuantity() - quantitySold;
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Not enough stock");
            }
            product.setStockQuantity(newQuantity);
        } else {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = getTransactionById(id);
        if (existingTransaction != null) {
            existingTransaction.setProduct(updatedTransaction.getProduct());
            existingTransaction.setQuantitySold(updatedTransaction.getQuantitySold());
            existingTransaction.setTotalAmount(updatedTransaction.getTotalAmount());
            existingTransaction.setTax(updatedTransaction.getTax());
            existingTransaction.setDiscount(updatedTransaction.getDiscount());
            existingTransaction.setDate(updatedTransaction.getDate());
            existingTransaction.setName(updatedTransaction.getName()); // Set the name field
            return transactionRepository.save(existingTransaction);
        } else {
            throw new RuntimeException("Transaction not found with id " + id);
        }
    }

    private double calculateTotalAmount(double price, int quantitySold, double tax, double discount) {
        double subtotal = price * quantitySold;
        double totalWithTax = subtotal + (subtotal * tax / 100);
        return totalWithTax - (totalWithTax * discount / 100);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}