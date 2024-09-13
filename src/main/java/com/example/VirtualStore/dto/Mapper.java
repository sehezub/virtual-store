package com.example.VirtualStore.dto;

import com.example.VirtualStore.domain.Product;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
  ProductDto productToProductDto(Product product);
  Product productDtoToProduct(ProductDto productDto);
}