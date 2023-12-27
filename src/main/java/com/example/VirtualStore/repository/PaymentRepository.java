package com.example.VirtualStore.repository;

import com.example.VirtualStore.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
