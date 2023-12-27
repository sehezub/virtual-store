package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.PaymentItem;
import com.example.VirtualStore.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class CartItemServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private CartItemService cartItemService;
  @Test
  public void toPaymentItem_allOK_void() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);
    saveEntity(product);
    saveEntity(cartItem);
    PaymentItem paymentItem = cartItemService.toPaymentItem(cartItem.getId());
    assertThat(paymentItem.getQuantity()).isEqualTo(cartItem.getQuantity());
    assertThat(paymentItem.getCode()).isEqualTo(cartItem.getCode());
    assertThat(paymentItem.getPrice()).isEqualTo(cartItem.getPrice());
    assertThat(paymentItem.getDescription()).isEqualTo(cartItem.getDescription());
  }
  @Test
  public void setQuantity_validCartItemId_void() {
    CartItem cartItem = createCartItem(createProduct(), 10L);
    saveEntity(cartItem);
    cartItemService.setQuantity(cartItem.getId(), 9L);
    assertThat(cartItem.getQuantity()).isEqualTo(9L);
  }
  @Test
  public void setQuantity_nonExistingCartItemId_throwsNoSuchElementException() {
    assertThrows(NoSuchElementException.class, () -> {
      cartItemService.setQuantity(1L, 9L);
    });
  }
  @Test
  public void createCartItem_NonExistingProduct_throwsNoSuchElementException() {
    assertThrows(NoSuchElementException.class, () -> {
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
