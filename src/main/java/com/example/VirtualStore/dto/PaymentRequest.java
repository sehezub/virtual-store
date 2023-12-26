package com.example.VirtualStore.dto;

import lombok.Data;

@Data
public class PaymentRequest {
  private String cardNumber;
  private String cardType;
  private Long userId;
  private Long cartId;
}
