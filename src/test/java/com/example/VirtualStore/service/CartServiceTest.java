package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.*;
import com.example.VirtualStore.dto.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private CartService cartService;
  @Test
  public void deleteProduct_productInCart_void() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);
    Cart cart = createCart(Arrays.asList(cartItem));

    saveEntity(cart);
    saveEntity(cartItem);
    cartService.deleteProduct(product.getCode(), cart.getId());

    assertThat(cart.getCartItemList()).isEmpty();
  }
  @Test
  public void deleteProduct_productNotInCart_void() {
    Product product1 = createProduct();
    Product product2 = createProduct();
    product2.setCode("XY");
    CartItem cartItem = createCartItem(product1, 10L);
    Cart cart = createCart(Arrays.asList(cartItem));

    saveEntity(cart);
    saveEntity(cartItem);
    saveEntity(product1);
    saveEntity(product2);
    cartService.deleteProduct(product2.getCode(), cart.getId());

    assertThat(cart.getCartItemList().size()).isEqualTo(1);
  }
  @Test
  public void generatePayment_okayCase_void() {
    Product product = createProduct();
    saveEntity(product);
    Cart cart = createCart(Arrays.asList(createCartItem(product, 10L)));
    saveEntity(cart);
    User user = createUser(cart);
    saveEntity(user);
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setCardNumber("cn");
    paymentRequest.setCardType("ct");
    paymentRequest.setCartId(cart.getId());
    paymentRequest.setUserId(user.getId());
    Payment payment = cartService.generatePayment(paymentRequest);
    assertThat(payment.getCardType()).isEqualTo(paymentRequest.getCardType());
    assertThat(payment.getMaskedCardNumber()).isEqualTo(paymentRequest.getCardNumber());
  }
  @Test
  public void generatePayment_emptyCart_TODO() {
    Cart cart = createCart(new ArrayList<>());
    User user = createUser(cart);
    saveEntity(cart);
    saveEntity(user);
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setCardNumber("cn");
    paymentRequest.setCardType("ct");
    paymentRequest.setCartId(cart.getId());
    paymentRequest.setUserId(user.getId());
    Exception exception = assertThrows(______.class, () -> {
      cartService.generatePayment(paymentRequest);
    });
  }
  @Test
  public void generatePayment_productWasDeleted_exception() {
    Product product = createProduct();
    saveEntity(product);
    Cart cart = createCart(Arrays.asList(createCartItem(product, 10L)));
    saveEntity(cart);
    User user = createUser(cart);
    saveEntity(user);
    entityManager.remove(product);
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setCardNumber("cn");
    paymentRequest.setCardType("ct");
    paymentRequest.setCartId(cart.getId());
    paymentRequest.setUserId(user.getId());
    Exception exception = assertThrows(______.class, () -> {
      cartService.generatePayment(paymentRequest);
    });
  }
  @Test
  public void generatePayment_productsPriceWasChanged_Payment() {
    Product product = createProduct();
    saveEntity(product);
    Cart cart = createCart(Arrays.asList(createCartItem(product, 10L)));
    saveEntity(cart);
    User user = createUser(cart);
    saveEntity(user);
    product.setPrice(product.getPrice()+100L);
    saveEntity(product);
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setCardNumber("cn");
    paymentRequest.setCardType("ct");
    paymentRequest.setCartId(cart.getId());
    paymentRequest.setUserId(user.getId());
    Payment payment = cartService.generatePayment(paymentRequest);
  }
}
