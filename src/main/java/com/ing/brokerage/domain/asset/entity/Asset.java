package com.ing.brokerage.domain.asset.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset  {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "size", nullable = false)
  private Long size;

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

}
