package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CartServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private CartService cartService;
  @Test
  public void deleteProduct_productInCart_void() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);
    Cart cart = createCart(Arrays.asList(cartItem));

    saveEntity(cart);
    //saveEntity(cartItem); ??
    cartService.deleteProduct(); // argument??

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
    //saveEntity(cartItem); ??
    cartService.deleteProduct(); // argument ?? prod 2

    assertThat(cart.getCartItemList().size()).isEqualTo(1);
  }
  @Test
  public void generatePayment_emptyCart_TODO() {
    //exception
    // implementar generatepayment en usuario
  }
  @Test
  public void generatePayment_nonEmptyCart_Payment() {
//    Product product = createProduct();
//    CartItem cartItem = createCartItem(product, 10L);
//    Cart cart = createCart(Arrays.asList(cartItem));
//
//    Payment payment = cartService.generatePayment(); // argument ??
//
//    assertThat(payment.getPaymentItemList().size()).isEqualTo(1);
//    PaymentItem paymentItem = payment.getPaymentItemList().get(0);
//    assertThat()
    // implementar generatepayment en usuario
  }
}
