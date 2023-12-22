package com.example.VirtualStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository<Payment> extends JpaRepository<Payment, Long> {
}
