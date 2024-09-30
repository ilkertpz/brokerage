package com.ing.brokerage.domain.order.service;

import com.ing.brokerage.domain.order.dto.OrderCancelResponse;
import com.ing.brokerage.domain.order.dto.OrderCreateRequest;
import com.ing.brokerage.domain.order.dto.OrderCreateResponse;
import com.ing.brokerage.domain.order.dto.OrderDTO;
import com.ing.brokerage.domain.order.dto.OrderMatchResponse;
import com.ing.brokerage.domain.order.dto.OrderListRequest;
import java.util.List;

public interface IOrderService {

  OrderCreateResponse create(OrderCreateRequest orderCreateRequest);

  OrderCancelResponse cancel(Long orderId);

  OrderMatchResponse match(Long orderId);

  List<OrderDTO> getOrders(OrderListRequest orderListRequest);
}
