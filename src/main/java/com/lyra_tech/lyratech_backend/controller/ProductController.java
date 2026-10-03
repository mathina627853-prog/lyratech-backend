package com.lyra_tech.lyratech_backend.controller;

import com.lyra_tech.lyratech_backend.entity.Product;
import com.lyra_tech.lyratech_backend.repository.ProductRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://lyratech.local", "http://localhost"})
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // GET one product
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable Integer id) {

        return productRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE product
    @PostMapping
    public Product createProduct(
            @RequestBody Product product) {

        return productRepository.save(product);
    }

    // UPDATE product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer id,
            @RequestBody Product updatedProduct) {

        return productRepository
                .findById(id)
                .map(product -> {

                    product.setProductName(
                            updatedProduct.getProductName()
                    );

                    product.setCategoryId(
                            updatedProduct.getCategoryId()
                    );

                    product.setPrice(
                            updatedProduct.getPrice()
                    );

                    Product savedProduct =
                            productRepository.save(product);

                    return ResponseEntity.ok(savedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Integer id) {

        if (!productRepository.existsById(id)) {

            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}