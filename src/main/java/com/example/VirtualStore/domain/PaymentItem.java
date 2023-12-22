package com.example.VirtualStore.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Payment_Item")
public class PaymentItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long quantity;
  private String description;
  private Float price; //unit price
  private String code;

  public Float getTotal() {
    return quantity * price;
  }
}
