package com.example.VirtualStore.service.impl;

import com.example.VirtualStore.domain.*;
import com.example.VirtualStore.dto.PaymentRequest;
import com.example.VirtualStore.exception.ModifiedProductException;
import com.example.VirtualStore.repository.CartItemRepository;
import com.example.VirtualStore.repository.CartRepository;
import com.example.VirtualStore.repository.ProductRepository;
import com.example.VirtualStore.service.CartItemService;
import com.example.VirtualStore.service.CartService;
import com.example.VirtualStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
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
    ArrayList<CartItem> repeatedCartItems = cart.getCartItemList().stream()
        .filter(cartItm -> cartItm.getCode().equals(cartItem.getCode()))
        .collect(Collectors.toCollection(ArrayList::new));

    if (repeatedCartItems.isEmpty()){
      cart.getCartItemList().add(cartItem);
      return cartItem;
    }

    Long totalQuantity = repeatedCartItems.stream()
        .map(item -> item.getQuantity())
        .reduce((quantity1, quantity2) -> quantity1 + quantity2).orElse(0L) + quantity;

    if ( totalQuantity > productService.getProductById(productId).getStock() ) {
      throw new IllegalArgumentException("Not enough stock of the product to add to the cart");
    }

    cart.getCartItemList().removeIf(cartItm -> cartItm.getCode().equals(cartItem.getCode()));
    cartItem.setQuantity(totalQuantity);
    cart.getCartItemList().add(cartItem);
    return cartItem;
  }

  @Override
  public Cart saveCart(Cart cart) {
    return cartRepository.save(cart);
  }

  public void removeProduct(String productCode, Long cartId) {
   Cart cart = cartRepository.getById(cartId);
   cart.getCartItemList().removeIf(cartItem -> cartItem.getCode().equals(productCode));
  }

  public Payment generatePayment(PaymentRequest paymentRequest) {
    Cart cart = getCartById(paymentRequest.getCartId());
    if (cart.getCartItemList().isEmpty()) {
      throw new IllegalStateException("Cant generate a payment when the cart is empty");
    }
    Payment payment = new Payment();
    payment.setCardType(paymentRequest.getCardType());
    payment.setMaskedCardNumber(paymentRequest.getCardNumber());


    for (int i = 0; i < cart.getCartItemList().size(); i++) {
      CartItem cartItem = cart.getCartItemList().get(i);
      Product product = productService.getProductByCode(cart.getCartItemList().get(i).getCode());
      System.out.print("------" + product.getCode());
      if ( !product.getPrice().equals(cartItem.getPrice()) ) {
        throw new ModifiedProductException("The product's price was modified since it was added to the cart");
      }
    }
    ArrayList<PaymentItem> paymentItemList = cart.getCartItemList().stream()
        .map(cartItem -> cartItemService.toPaymentItem(cartItem.getId())).collect(Collectors.toCollection(ArrayList::new));
    payment.setPaymentItemList(paymentItemList);
    return payment;
  }
}
