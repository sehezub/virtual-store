package com.example.VirtualStore.service;

import com.example.VirtualStore.VirtualStoreApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceTest extends VirtualStoreApplication {
  @Autowired
  UserService userService;
}
