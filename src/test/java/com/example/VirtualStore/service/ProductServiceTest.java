package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class ProductServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private ProductService productService;
  @Test
  public void deleteProduct() {
    Product product = createProduct();
    saveEntity(product);
    int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product");
    productService.deleteProduct(product.getId());
    entityManager.flush();

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product")).isEqualTo(count - 1);
  }
  @Test
  public void getProductDtoById_test() {
    Product product = createProduct();
    saveEntity(product);
    ProductDto productDto = productService.getProductDtoById(product.getId());
    assertThat(productDto.getDescription()).isEqualTo(product.getDescription());
    assertThat(productDto.getCode()).isEqualTo(product.getCode());
    assertThat(productDto.getName()).isEqualTo(product.getName());
    assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
    assertThat(productDto.getBrand()).isEqualTo(product.getBrand());
    assertThat(productDto.getCategory()).isEqualTo(product.getCategory());
    assertThat(productDto.getColor()).isEqualTo(product.getColor());
    assertThat(productDto.getVersion()).isEqualTo(product.getVersion());
    assertThat(productDto.getStock()).isEqualTo(product.getStock());
    assertThat(productDto.getReleaseDate()).isEqualTo(product.getReleaseDate());
  }
  @Test
  public void saveProductByDto_test() {
    ProductDto productDto = mapper.productToProductDto(createProduct());
    int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product");
    productService.putProductByDto(productDto, null);
    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product")).isEqualTo(count+1);
    count += 1;
    productService.putProductByDto(productDto, 99L);
    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "Product")).isEqualTo(count+1);
  }
}
