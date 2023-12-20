package com.example.VirtualStore.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String maskedCardNumber; //change name
  private String cardType; //change name
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private List<PaymentItem> paymentItemList = new ArrayList<>();
  private Float total;
}
