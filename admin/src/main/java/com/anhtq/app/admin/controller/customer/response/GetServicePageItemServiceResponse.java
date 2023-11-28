package com.anhtq.app.admin.controller.customer.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetServicePageItemServiceResponse {

  private Long id;

  private String name;

  private String sortDescription;

  private String image;

  private Long price;

  private LocalDateTime lastUpdate;

  private String lastUpdated;

  public GetServicePageItemServiceResponse(
      Long id, String name, String sortDescription, String image, Long price, LocalDateTime lastUpdate) {
    this.id = id;
    this.name = name;
    this.sortDescription = sortDescription;
    this.image = image;
    this.price = price;
    this.lastUpdate = lastUpdate;
  }
}
