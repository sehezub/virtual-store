package com.example.VirtualStore.controller;

import com.example.VirtualStore.dto.ProductDto;
import com.example.VirtualStore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ProductController {
  @Autowired
  ProductService productService;

  @GetMapping("/products/{id}")
  public ProductDto getProduct(@PathVariable Long id) { return productService.getProductDtoById(id); }

  @DeleteMapping("/products/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }

  @PutMapping("/products/{id}")
  public ProductDto replaceProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
    return productService.putProductByDto(productDto, id);
  }
  @PostMapping("/products")
  public ProductDto saveProduct(@RequestBody ProductDto productDto){
    return productService.putProductByDto(productDto, null);
  }
  // todo implement patch, ex: change product.is_active
}
