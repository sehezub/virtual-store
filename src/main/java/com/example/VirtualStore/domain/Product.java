package com.example.VirtualStore.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  @Column(name="release_date")
  private Long releaseDate;
  private Long stock;
  private String brand;
  private Float price;
  private String category;
  private String version;
  private String color;
  private String code;
  @Column(name="is_active")
  private boolean isActive;
}
