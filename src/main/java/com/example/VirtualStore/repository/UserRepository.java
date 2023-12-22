package com.example.VirtualStore.repository;

import com.example.VirtualStore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
