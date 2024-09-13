package com.example.VirtualStore.service.impl;

import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.PaymentItem;
import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.exception.ResourceNotInDB;
import com.example.VirtualStore.repository.CartItemRepository;
import com.example.VirtualStore.service.CartItemService;
import com.example.VirtualStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
public class CartItemServiceImpl implements CartItemService {
  @Autowired
  CartItemRepository cartItemRepository;
  @Autowired
  ProductService productService;
  public CartItem getCartItemById(Long id) {
    return cartItemRepository.findById(id).orElseThrow( () ->
        new ResourceNotInDB("", "id", id.toString(), "CartItem"));
  }

  public CartItem saveCartItem(CartItem cartItem) { return cartItemRepository.save(cartItem); }

  public void deleteById(Long id) {
    cartItemRepository.deleteById(id);
  }

  public PaymentItem toPaymentItem(Long id) {
    CartItem cartItem = getCartItemById(id);
    PaymentItem paymentItem = new PaymentItem();
    paymentItem.setCode(cartItem.getCode());
    paymentItem.setQuantity(cartItem.getQuantity());
    paymentItem.setDescription(cartItem.getDescription());
    paymentItem.setPrice(cartItem.getPrice());
    return paymentItem;
  }

  @Override
  public void setQuantity(Long id, Long quanity) {
    CartItem cartItem = getCartItemById(id);
    cartItem.setQuantity(quanity);
    cartItemRepository.save(cartItem);
  }

  public CartItem createCartItem(Long productId, Long quantity) {
    Product product = productService.getProductById(productId);
    CartItem cartItem = new CartItem();
    cartItem.setQuantity(quantity);
    cartItem.setProductId(productId);
    cartItem.setCode(product.getCode());
    cartItem.setDescription(product.getDescription());
    cartItem.setPrice(product.getPrice());
    return cartItem;
  }
}

