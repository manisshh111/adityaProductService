package com.aditya.ProdcuctService;

import com.aditya.ProdcuctService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
