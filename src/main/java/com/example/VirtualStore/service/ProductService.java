package com.example.VirtualStore.service;

import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.dto.ProductDto;

public interface ProductService {
  public Product getProductById(Long id);
  public Product getProductByCode(String code);
  public Product findProductByCode(String code);
  public ProductDto getProductDtoById(Long id);
  public Product productDtoToProduct(ProductDto productDto);
  public ProductDto putProductByDto(ProductDto productDto, Long id);
  public Product saveProduct(Product product);
  public void deleteProduct(Long id);
}
