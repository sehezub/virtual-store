package com.example.VirtualStore.service;

import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.PaymentItem;

public interface CartItemService {
  public CartItem getCartItemById(Long id);
  public CartItem saveCartItem(CartItem cartItem);
  public void deleteById(Long id);
  public PaymentItem toPaymentItem(Long id);
  public void setQuantity(Long id, Long quantity);
  public CartItem createCartItem(Long productId, Long quantity);
}
