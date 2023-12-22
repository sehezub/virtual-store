package com.example.VirtualStore.repository;

import com.example.VirtualStore.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
