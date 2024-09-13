package com.example.VirtualStore.dto;

import lombok.Data;

@Data
public class ProductDto {
  private String name;
  private String description;
  private Long releaseDate;
  private Long stock;
  private String brand;
  private Float price;
  private String category;
  private String version;
  private String color;
  private String code;
  private boolean isActive;
}
