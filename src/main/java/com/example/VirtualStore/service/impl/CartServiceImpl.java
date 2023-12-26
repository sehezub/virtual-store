package com.example.VirtualStore.service.impl;

import com.example.VirtualStore.domain.Cart;
import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.Payment;
import com.example.VirtualStore.domain.Product;
import com.example.VirtualStore.dto.PaymentRequest;
import com.example.VirtualStore.repository.CartItemRepository;
import com.example.VirtualStore.repository.CartRepository;
import com.example.VirtualStore.repository.ProductRepository;
import com.example.VirtualStore.service.CartItemService;
import com.example.VirtualStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {
  @Autowired
  CartRepository cartRepository;
  @Autowired
  CartItemService cartItemService;
  @Autowired
  ProductService productService;
  public Cart getCartById(Long id) {
    Cart cart = cartRepository.findById(id).orElse(null);
    if (cart == null) { throw new NoSuchElementException("No cart with the given id");
    }
    return cart;
  }

  @Override
  public CartItem addCartItem(Long cartId, Long productId, Long quantity) {
    Cart cart = cartRepository.getById(cartId);
    CartItem cartItem = cartItemService.createCartItem(productId, quantity);
    Stream<CartItem> repeatedCartItems = cart.getCartItemList().stream()
        .filter(cartItm -> cartItm.getCode().equals(cartItem.getCode()));

    if (repeatedCartItems.count() == 0){
      cart.getCartItemList().add(cartItem);
      return cartItem;
    }
    Long totalQuantity = repeatedCartItems
        .map(item -> item.getQuantity())
        .reduce((quantity1, quantity2) -> quantity1 + quantity2).orElse(0L) + quantity;
    cart.getCartItemList().removeIf(cartItm -> cartItm.getCode().equals(cartItem.getCode()));
    cartItem.setQuantity(totalQuantity);
    cart.getCartItemList().add(cartItem);
    return cartItem;
  }

  @Override
  public Cart saveCart(Cart cart) {
    return cartRepository.save(cart);
  }

  @Override
  public void removeProduct(Long cartId, Long productId) {

  }

  public Payment generatePayment(PaymentRequest paymentRequest) {
    return null;
  }
}
