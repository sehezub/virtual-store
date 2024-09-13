package com.example.VirtualStore.service.impl;

import com.example.VirtualStore.domain.*;
import com.example.VirtualStore.dto.PaymentRequest;
import com.example.VirtualStore.exception.ConditionNotMet;
import com.example.VirtualStore.exception.ResourceNotInDB;
import com.example.VirtualStore.repository.CartRepository;
import com.example.VirtualStore.service.CartItemService;
import com.example.VirtualStore.service.CartService;
import com.example.VirtualStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
  @Autowired
  CartRepository cartRepository;
  @Autowired
  CartItemService cartItemService;
  @Autowired
  ProductService productService;
  public Cart getCartById(Long id) {
    return cartRepository.findById(id).orElseThrow(() ->
        new ResourceNotInDB("", "id", id.toString(), "Cart"));
  }

  @Override
  public CartItem addCartItem(Long cartId, Long productId, Long quantity) {
    Cart cart = cartRepository.getById(cartId);
    CartItem cartItem = cartItemService.createCartItem(productId, quantity);

    // if the product is already in the cart, sums the quantities
    cart.getCartItemList().removeIf( c -> {
      if ( c.getCode().equals(cartItem.getCode()) ){
        cartItem.setQuantity(c.getQuantity() + cartItem.getQuantity());
        return true;
      }
      return false;
    });

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

    // conditions that need to be met in order to create a payment request
    boolean nonEmptyCart = !cart.getCartItemList().isEmpty();
    boolean existingProducts = true;
    boolean nonZeroQuantity= true;
    boolean inStockRange = true;
    boolean allItemsActive = true;
    boolean samePrice = true;
    // list of conditions that were not satisfied
    ArrayList<String> notSatisfiedConditions = new ArrayList<>();

    for ( CartItem c : cart.getCartItemList() ) {
      Product product = productService.getProductByCode(c.getCode());
      if ( productService.findProductByCode(c.getCode()) == null ) { existingProducts = false; }
      if ( c.getQuantity() < 0L ) { nonZeroQuantity = false;}
      if ( c.getQuantity() >  product.getStock() ) { inStockRange = false; }
      if ( !product.isActive() ) { allItemsActive = false; }
      if ( !product.getPrice().equals(c.getPrice()) ) { samePrice = false; }
    }

    if ( !nonEmptyCart ) {
      notSatisfiedConditions.add("Cart is not empty");
    }
    // checks that every product in the cart exist in the database
    // if not, then deletes it from the cart
    if ( !existingProducts ) {
      notSatisfiedConditions.add("All products in the cart reference to an existing product");
      cart.getCartItemList().removeIf( c -> productService.findProductByCode(c.getCode()) == null);
    }
    // checks that the quantity of every product in the cart is at least one
    // deletes the ones that have not met this
    if ( !nonZeroQuantity ) {
      notSatisfiedConditions.add("Non-zero quantity for products in the cart");
      cart.getCartItemList().removeIf(
          c -> (productService.getProductByCode(c.getCode()).getStock().equals(0L)));
    }
    // checks that the quantity of every product in the cart is less than the actual stock
    // sets the quantity of those products that have not met the criteria to the actual stock
    // or deletes them when stock <= 0
    if ( !inStockRange ) {
      notSatisfiedConditions.add("All products in the cart are in the range set by the stock");
      cart.getCartItemList().removeIf(
          c -> (productService.getProductByCode(c.getCode()).getStock() <= 0L));
      cart.getCartItemList().forEach(c -> {
        Product product = productService.getProductByCode(c.getCode());
        if ( c.getQuantity() > product.getStock() ) { c.setQuantity(product.getStock()); } });
    }
    // checks that all the products in the cart are active, deletes the ones that not
    if ( !allItemsActive ) {
      notSatisfiedConditions.add("All products in the cart are currently active");
      cart.getCartItemList().removeIf(
          c -> !productService.getProductByCode(c.getCode()).isActive());
    }
    // checks that all the products in the cart have the same prices from when they were
    // first added to the cart
    if ( !samePrice ) {
      notSatisfiedConditions.add("Prices remain the same since added to the cart");
      cart.getCartItemList().forEach( c ->
          c.setPrice(productService.getProductByCode(c.getCode()).getPrice()));
    }
    // if the conditions have not met, throw an exception
    if ( !notSatisfiedConditions.isEmpty() ) {
      throw new ConditionNotMet("The conditions to generate a payment did not met",
          notSatisfiedConditions);}

    // creates payment object
    Payment payment = new Payment();
    payment.setCardType(paymentRequest.getCardType());
    payment.setMaskedCardNumber(paymentRequest.getCardNumber());

    ArrayList<PaymentItem> paymentItemList = cart.getCartItemList().stream()
        .map(cartItem -> cartItemService.toPaymentItem(cartItem.getId())).collect(Collectors.toCollection(ArrayList::new));
    payment.setPaymentItemList(paymentItemList);
    return payment;
  }

  public void updateCart(Long cartId) {
    Cart cart = getCartById(cartId);
    //remove deleted, inactive or out of stock products in the cart
    cart.getCartItemList().removeIf(
        c -> (productService.findProductByCode(c.getCode()) == null) ||
            (!productService.getProductByCode(c.getCode()).isActive()) ||
            (productService.getProductByCode(c.getCode()).getStock() <= 0L)
    );
    //update changed products in te cart
    cart.getCartItemList()
        .forEach(c -> {
          Product product = productService.getProductByCode(c.getCode());
          if ( !product.getPrice().equals(c.getPrice()) )
            {c.setPrice(product.getPrice());}
          if ( c.getQuantity() > product.getStock() )
            {c.setQuantity(product.getStock());}
          if ( !c.getDescription().equals(product.getDescription()) )
            {c.setDescription(product.getDescription());}
        });
  }
}