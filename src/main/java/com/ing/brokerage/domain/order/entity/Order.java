package com.ing.brokerage.domain.order.entity;

import com.ing.brokerage.domain.order.constant.Status;
import com.ing.brokerage.domain.order.constant.Side;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "asset_name", nullable = false)
  private String assetName;

  @Enumerated(EnumType.STRING)
  @Column(name = "side", nullable = false)
  private Side side;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  @Column(name = "size", nullable = false)
  private Long size;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

  @Column(name = "create_date", nullable = false)
  private LocalDateTime createDate;
}
