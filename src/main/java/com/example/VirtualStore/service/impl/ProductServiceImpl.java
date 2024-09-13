package com.example.VirtualStore.service.impl;

import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.dto.Mapper;
import com.example.VirtualStore.dto.ProductDto;
import com.example.VirtualStore.exception.EntityConflict;
import com.example.VirtualStore.exception.ResourceNotInDB;
import com.example.VirtualStore.repository.ProductRepository;
import com.example.VirtualStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  ProductRepository productRepository;
  @Autowired
  Mapper mapper;
  public Product getProductById(Long id) {
    return productRepository.findById(id).orElseThrow( () ->
        new ResourceNotInDB("", "id", id.toString(), "Product"));
  }
  public Product getProductByCode(String code) {
    return Optional.ofNullable(productRepository.findByCode(code)).orElseThrow( () ->
        new ResourceNotInDB("", "code", code, "Product"));
  }

  public Product findProductByCode(String code) {
    return productRepository.findByCode(code);
  }

  public ProductDto getProductDtoById(Long id) {
    Product product = getProductById(id);
    return mapper.productToProductDto(product);}
  public Product productDtoToProduct(ProductDto productDto) { return mapper.productDtoToProduct(productDto); }
  public ProductDto putProductByDto(ProductDto productDto, Long id) {
    Product product = productDtoToProduct(productDto);
    if ( productRepository.existsById(id) ) { deleteProduct(id); }
    product.setId(id);
    product = productRepository.save(product);
    return getProductDtoById(product.getId());
  }
  public Product saveProduct(Product product) { return productRepository.save(product); }
  public void deleteProduct(Long id) { productRepository.deleteById(id); }
}