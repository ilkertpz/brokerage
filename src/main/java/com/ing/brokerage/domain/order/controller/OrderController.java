package com.ing.brokerage.domain.order.controller;


import com.ing.brokerage.domain.order.dto.OrderCreateRequest;
import com.ing.brokerage.domain.order.dto.OrderCancelResponse;
import com.ing.brokerage.domain.order.dto.OrderCreateResponse;
import com.ing.brokerage.domain.order.dto.OrderDTO;
import com.ing.brokerage.domain.order.dto.OrderListRequest;
import com.ing.brokerage.domain.order.dto.OrderMatchResponse;
import com.ing.brokerage.domain.order.service.IOrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brokerage/order")
@RequiredArgsConstructor
public class OrderController {

  private final IOrderService orderService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
  public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody @Valid OrderCreateRequest orderCreateRequest) {
    return ResponseEntity.ok(orderService.create(orderCreateRequest));
  }

  @PutMapping("/cancel/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
  public ResponseEntity<OrderCancelResponse> cancelOrder(@PathVariable long id) {
    return ResponseEntity.ok(orderService.cancel(id));
  }

  @PutMapping("/match/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<OrderMatchResponse> matchOrder(@PathVariable long id) {
    return ResponseEntity.ok(orderService.match(id));
  }

  @PostMapping("/get-list")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<OrderDTO>> getOrders(@RequestBody @Valid OrderListRequest orderListRequest) {
    return ResponseEntity.ok(orderService.getOrders(orderListRequest));
  }

}

