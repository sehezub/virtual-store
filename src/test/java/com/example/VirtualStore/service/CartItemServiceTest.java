package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplicationTests;
import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.Payment;
import com.example.VirtualStore.domain.PaymentItem;
import com.example.VirtualStore.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional
public class CartItemServiceTest extends VirtualStoreApplicationTests {
  @Autowired
  private CartItemService cartItemService;

  @Test
  public void changeQuantity_valid_void() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);
    Long oldQuantity = cartItem.getQuantity();

    saveEntity(cartItem);
    //usar changequantity llamando a cartItemService.findbyid(cartitem.getid)

    //assertThat(cartItemService.findbyid(cartitem.getid).getQuantity()).isEqualTo(6L);
    //assertThat(cartItemService.findbyid(cartitem.getid).getPrice()).isEqualTo(6*cartItem.getPrice());
  }
  @Test
  public void toPaymentItem_valid_paymentItem() {
    Product product = createProduct();
    CartItem cartItem = createCartItem(product, 10L);

    saveEntity(cartItem);
    PaymentItem paymentItem = cartItemService.toPaymentItem();//toma id o cartitem

    assertThat(paymentItem.getCode()).isEqualTo(cartItem.getCode());
    assertThat(paymentItem.getPrice()).isEqualTo(cartItem.getPrice());
    assertThat(paymentItem.getDescription()).isEqualTo(cartItem.getDescription());
    assertThat(paymentItem.getQuantity()).isEqualTo(cartItem.getQuantity());
  }
}
