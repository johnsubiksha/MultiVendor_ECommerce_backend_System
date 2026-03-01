package com.example.ECommerce.services;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productrepository;

    public Product addProduct(Product product, String sellerid) {
        product.setSellerId(sellerid);
        return productrepository.save(product);
    }

    // 2️⃣ View own products
    public List<Product> getSellerProducts(String sellerId) {
        return productrepository.findBySellerId(sellerId);
    }

    // 3️⃣ Update own product
    public Product updateProduct(String productId, Product updatedProduct, String sellerId) {

        Product product = productrepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSellerId().equals(sellerId)) {
            throw new RuntimeException("Unauthorized to update this product");
        }

        if(updatedProduct.getName() != null) {
            product.setName(updatedProduct.getName());
        }

        if(updatedProduct.getPrice() != null) {
            product.setPrice(updatedProduct.getPrice());
        }

        if(updatedProduct.getDescription() != null) {
            product.setDescription(updatedProduct.getDescription());
        }

        if(updatedProduct.getQuantity() != null) {
            product.setQuantity(updatedProduct.getQuantity());
        }

        return productrepository.save(product);
    }

    // 4️⃣ Delete own product
    public void deleteProduct(String productId, String sellerId) {

        Product product = productrepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSellerId().equals(sellerId)) {
            throw new RuntimeException("Unauthorized to delete this product");
        }

        productrepository.delete(product);
    }
}
