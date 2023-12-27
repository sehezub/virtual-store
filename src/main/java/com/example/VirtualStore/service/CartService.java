package com.example.VirtualStore.service;

import com.example.VirtualStore.domain.Cart;
import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.Payment;
import com.example.VirtualStore.dto.PaymentRequest;

public interface CartService {
  public Cart getCartById(Long Id);
  public CartItem addCartItem(Long cartId, Long productId, Long quantity);
  public Cart saveCart(Cart cart);
  public void removeProduct(String productCode, Long cartId);
  public Payment generatePayment(PaymentRequest paymentRequest);
}
