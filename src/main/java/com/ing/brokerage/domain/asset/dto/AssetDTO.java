package com.ing.brokerage.domain.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetDTO {

  private Long id;

  private String name;

  private Long size;

  private Long customerId;

}
