package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.PaymentItem;
import com.example.VirtualStore.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class CartItemServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private CartItemService cartItemService;
  @Test
  public void createCartItem_NonExistingProduct_throws_____() {
    Exception exception = assertThrows(____.class, () -> {
      cartItemService.createCartItem(-1L, 10L);
    });
  }
  @Test
  public void createCartItem_validProduct_void() {
    Product product = createProduct();
    saveEntity(product);
    CartItem cartItem = cartItemService.createCartItem(product.getId(), 10L);
    assertThat(cartItem.getPrice()).isEqualTo(product.getPrice());
    assertThat(cartItem.getCode()).isEqualTo(product.getCode());
    assertThat(cartItem.getDescription()).isEqualTo(product.getDescription());
    assertThat(cartItem.getQuantity()).isEqualTo(10L);
  }
}
