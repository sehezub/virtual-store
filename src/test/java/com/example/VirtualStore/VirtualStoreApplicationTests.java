package com.example.VirtualStore;

import com.example.VirtualStore.domain.*;
import com.example.VirtualStore.dto.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class VirtualStoreApplicationTests {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected EntityManager entityManager;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected Mapper mapper;

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

	protected User createUser(Cart cart) {
		User user = new User();
		user.setCart(cart);
		user.setName("xy");
		user.setPaymentList(new ArrayList<Payment>());
		return user;
	}

	protected Product createProduct() {
		Product product = new Product();
		product.setName("A");
		product.setDescription("B");
		product.setReleaseDate(1L);
		product.setStock(100L);
		product.setBrand("C");
		product.setPrice(1F);
		product.setCategory("D");
		product.setVersion("E");
		product.setColor("F");
		product.setCode("G");
		return product;
	}

	protected void saveEntity(Object entity) {
		entityManager.persist(entity); //puts the entity in the persistence context
	}

}
