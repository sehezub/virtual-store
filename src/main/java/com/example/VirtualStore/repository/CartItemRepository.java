package com.example.VirtualStore.repository;

import com.example.VirtualStore.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
