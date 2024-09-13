package com.example.VirtualStore.controller;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.dto.ProductDto;
import com.example.VirtualStore.repository.ProductRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
public class ProductControllerTest extends VirtualStoreApplicationTests {
  //todo test https responses better
  @Autowired
  ProductRepository productRepository;
  @Test
  @SneakyThrows
  public void getProduct_validId_returnProduct() {
    Product product = createProduct();
    saveEntity(product);

    mockMvc.perform(get("/products/" + product.getId().toString()))
        .andExpect(status().isOk());
  }
  @Test
  @SneakyThrows
  public void getProduct_nonValidId_throwException() {

    mockMvc.perform(get("/products/1"))
        .andExpect(status().isNotFound());
  }
  @Test
  @SneakyThrows
  public void deleteProduct_validId_void() {
    Product product = createProduct();
    saveEntity(product);
    int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product");

    mockMvc.perform(delete("/products/" + product.getId().toString()))
        .andExpect(status().isOk());
    entityManager.flush();

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product")).isEqualTo(count-1);
  }
  @Test
  @SneakyThrows
  public void deleteProduct_nonValidId_throwException() {
    mockMvc.perform(delete("/products/1"))
        .andExpect(status().isOk());
  }
  @Test
  @SneakyThrows
  public void saveProduct_void() {
    ProductDto productDto = mapper.productToProductDto(createProduct());
    int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product");

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(productDto)));

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product")).isEqualTo(count+1);
  }
  @Test
  @SneakyThrows
  public void putProduct_validId_returnProduct() {
    Product product = createProduct();
    saveEntity(product);

    ProductDto productDto = mapper.productToProductDto(product);
    productDto.setName("newName");
    productDto.setCode("newCode");

    mockMvc.perform(put("/products/" + product.getId().toString())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(productDto)));

    assertThat(product.getName()).isEqualTo(productDto.getName());
    assertThat(product.getCode()).isEqualTo(productDto.getCode());
  }
}
