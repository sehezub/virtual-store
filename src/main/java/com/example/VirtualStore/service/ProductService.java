package com.example.VirtualStore.service;

import com.example.VirtualStore.domain.Product;

public interface ProductService {
  public Product getProductById(Long id);
  public Product getProductByCode(String code);
}
