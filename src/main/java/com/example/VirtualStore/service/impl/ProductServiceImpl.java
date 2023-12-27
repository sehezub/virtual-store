package com.example.VirtualStore.service.impl;

import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.repository.ProductRepository;
import com.example.VirtualStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  ProductRepository productRepository;
  public Product getProductById(Long id) {
    Product product = productRepository.findById(id).orElse(null);
    if (product == null) { throw new NoSuchElementException("No product with the given id");
    }
    return product;
  }

  public Product getProductByCode(String code) {
    Product product = productRepository.findByCode(code);
    if (product == null) {throw new NoSuchElementException("No product with the given code");}
    return product;
  }
}
