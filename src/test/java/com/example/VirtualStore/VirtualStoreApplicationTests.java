package com.example.VirtualStore;

import com.example.VirtualStore.domain.Cart;
import com.example.VirtualStore.domain.CartItem;
import com.example.VirtualStore.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
public class VirtualStoreApplicationTests {
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected EntityManager entityManager;
	@Autowired
	protected ObjectMapper objectMapper;

	protected CartItem createCartItem(Product product, Long quantity) {
		CartItem cartItem = new CartItem();
		cartItem.setCode(product.getCode());
		cartItem.setDescription(product.getDescription());
		cartItem.setPrice(product.getPrice());
		cartItem.setQuantity(quantity);
		return cartItem;
	}

	protected Cart createCart(List<CartItem> cartItems) {
		Cart cart = new Cart();
		cart.setCartItemList(cartItems);
		return cart;
	}

	protected Product createProduct() {
		Product product = new Product();
		product.setName("A");
		product.setDescription("B");
		product.setReleaseDate(1L);
		product.setStock(1L);
		product.setBrand("C");
		product.setPrice(1F);
		product.setCategory("D");
		product.setVersion("E");
		product.setColor("F");
		product.setCode("G");
		return product;
	}

	protected void saveEntity(Object entity) {
		entityManager.persist(entity);
		entityManager.flush();
	}
}
