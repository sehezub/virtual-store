package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.*;
import com.example.VirtualStore.dto.PaymentRequest;
import com.example.VirtualStore.exception.ConditionNotMet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Transactional
public class CartServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private CartService cartService;

  @Test
  public void addCartItem_productNotInCart_void() {
    Product product = createProduct();
    Cart cart = createCart(new ArrayList<>());
    saveEntity(cart);
    saveEntity(product);
    cartService.addCartItem(cart.getId(), product.getId(), 10L);
    assertThat(cart.getCartItemList().size()).isEqualTo(1);
    CartItem cartItem = cart.getCartItemList().get(0);
    assertThat(cartItem.getQuantity()).isEqualTo(10L);
    assertThat(cartItem.getCode()).isEqualTo(product.getCode());
  }
  @Test
  public void addCartItem_productAlreadyInCart() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);
    Cart cart = createCart(new ArrayList<>(Arrays.asList(cartItem)));
    saveEntity(product);
    saveEntity(cart);
    cartService.addCartItem(cart.getId(), product.getId(), 5L);
    assertThat(cart.getCartItemList().size()).isEqualTo(1);
    cartItem = cart.getCartItemList().get(0);
    assertThat(cartItem.getCode()).isEqualTo(product.getCode());
    assertThat(cartItem.getQuantity()).isEqualTo(15L);
  }
  @Test
  public void addCartItem_notEnoughStock_throwsIllegarArgumentException() {
    Product product = createProduct();
    product.setStock(1L);
    CartItem cartItem = createCartItem(product, 10L);
    Cart cart = createCart(new ArrayList<>(Arrays.asList(cartItem)));
    saveEntity(product);
    saveEntity(cart);
    assertThrows(IllegalArgumentException.class, () -> {
      cartService.addCartItem(cart.getId(), product.getId(), 10L);
    });
  }
  @Test
  public void removeProduct_productInCart_void() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);
    Cart cart = createCart(new ArrayList<>(Arrays.asList(cartItem)));

    saveEntity(cart);
    saveEntity(cartItem);
    cartService.removeProduct(product.getCode(), cart.getId());

    assertThat(cart.getCartItemList()).isEmpty();
  }
  @Test
  public void removeProduct_productNotInCart_void() {
    Product product1 = createProduct();
    Product product2 = createProduct();
    product2.setCode("XY");
    CartItem cartItem = createCartItem(product1, 10L);
    Cart cart = createCart(new ArrayList<>(Arrays.asList(cartItem)));

    saveEntity(cart);
    saveEntity(cartItem);
    saveEntity(product1);
    saveEntity(product2);
    cartService.removeProduct(product2.getCode(), cart.getId());

    assertThat(cart.getCartItemList().size()).isEqualTo(1);
  }
  @Test
  public void generatePayment_okayCase_void() {
    Product product = createProduct();
    saveEntity(product);
    Cart cart = createCart(new ArrayList<>(Arrays.asList(createCartItem(product, 10L))));
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
  public void generatePayment_emptyCart_throwsIllegalStateException() {
    Cart cart = createCart(new ArrayList<>());
    User user = createUser(cart);
    saveEntity(cart);
    saveEntity(user);
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setCardNumber("cn");
    paymentRequest.setCardType("ct");
    paymentRequest.setCartId(cart.getId());
    paymentRequest.setUserId(user.getId());
    assertThrows(IllegalStateException.class, () -> {
      cartService.generatePayment(paymentRequest);
    });
  }
  @Test
  public void generatePayment_productWasDeleted_throwsNoSuchElementException() {
    Product product = createProduct();
    saveEntity(product);
    Cart cart = createCart(new ArrayList<>(Arrays.asList(createCartItem(product, 10L))));
    saveEntity(cart);
    User user = createUser(cart);
    saveEntity(user);
    entityManager.remove(product);
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setCardNumber("cn");
    paymentRequest.setCardType("ct");
    paymentRequest.setCartId(cart.getId());
    paymentRequest.setUserId(user.getId());
    assertThrows(NoSuchElementException.class, () -> {
      cartService.generatePayment(paymentRequest);
    });
  }
  @Test
  public void generatePayment_productsPriceWasChanged_Payment() {
    Product product = createProduct();
    Cart cart = createCart(new ArrayList<>(Arrays.asList(createCartItem(product, 10L))));
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

    assertThrows(ConditionNotMet.class, () ->{
      cartService.generatePayment(paymentRequest);
    });
  }
}
