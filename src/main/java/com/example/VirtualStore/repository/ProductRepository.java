package com.example.VirtualStore.repository;

import com.example.VirtualStore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Product findByCode(String code);
}
