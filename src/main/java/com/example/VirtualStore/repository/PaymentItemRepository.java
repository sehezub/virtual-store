package com.example.VirtualStore.repository;

import com.example.VirtualStore.domain.PaymentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentItemRepository extends JpaRepository<PaymentItem, Long> {
}
