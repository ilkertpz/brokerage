package com.ing.brokerage.generator;

import com.ing.brokerage.domain.order.constant.Side;
import com.ing.brokerage.domain.order.constant.Status;
import com.ing.brokerage.domain.order.dto.OrderCreateRequest;
import com.ing.brokerage.domain.order.dto.OrderListRequest;
import com.ing.brokerage.domain.order.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDataGenerator {

  public static OrderCreateRequest generateOrderCreateRequestWithSideBuy(){
    OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
    orderCreateRequest.setCustomerId(1L);
    orderCreateRequest.setAssetName("Test Asset");
    orderCreateRequest.setPrice(new BigDecimal("1.5"));
    orderCreateRequest.setSide(Side.BUY);
    orderCreateRequest.setSize(5L);

    return orderCreateRequest;
  }

  public static OrderCreateRequest generateOrderCreateRequestWithSideSell(){
    OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
    orderCreateRequest.setCustomerId(1L);
    orderCreateRequest.setAssetName("Test Asset");
    orderCreateRequest.setPrice(new BigDecimal("1.5"));
    orderCreateRequest.setSide(Side.SELL);
    orderCreateRequest.setSize(5L);

    return orderCreateRequest;
  }

  public static OrderCreateRequest generateOrderCreateRequestWithoutSide(){
    OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
    orderCreateRequest.setCustomerId(1L);
    orderCreateRequest.setAssetName("Test Asset");
    orderCreateRequest.setPrice(new BigDecimal("1.5"));
    orderCreateRequest.setSize(5L);

    return orderCreateRequest;
  }

  public static Order generateOrder(){
    Order order = new Order();
    order.setCustomerId(1L);
    order.setId(1L);
    order.setAssetName("Test Asset");
    order.setPrice(new BigDecimal("1.5"));
    order.setSide(Side.SELL);
    order.setStatus(Status.PENDING);
    order.setCreateDate(LocalDateTime.now());
    order.setSize(5L);

    return order;
  }

  public static Order generateOrderWithMatchedStatus(){
    Order order = new Order();
    order.setCustomerId(1L);
    order.setId(1L);
    order.setAssetName("Test Asset");
    order.setPrice(new BigDecimal("1.5"));
    order.setSide(Side.SELL);
    order.setStatus(Status.MATCHED);
    order.setCreateDate(LocalDateTime.now());
    order.setSize(5L);

    return order;
  }

  public static Order generateCanceledOrder(){
    Order order = new Order();
    order.setCustomerId(1L);
    order.setId(1L);
    order.setAssetName("Test Asset");
    order.setPrice(new BigDecimal("1.5"));
    order.setSide(Side.SELL);
    order.setStatus(Status.CANCELED);
    order.setCreateDate(LocalDateTime.now());
    order.setSize(5L);

    return order;
  }

  public static Order generateMatchedOrder(){
    Order order = new Order();
    order.setCustomerId(1L);
    order.setId(1L);
    order.setAssetName("Test Asset");
    order.setPrice(new BigDecimal("1.5"));
    order.setSide(Side.SELL);
    order.setStatus(Status.MATCHED);
    order.setCreateDate(LocalDateTime.now());
    order.setSize(5L);

    return order;
  }

  public static List<Order> generateOrderList(){
    Order order = new Order();
    order.setCustomerId(1L);
    order.setId(1L);
    order.setAssetName("Test Asset");
    order.setPrice(new BigDecimal("1.5"));
    order.setSide(Side.SELL);
    order.setStatus(Status.PENDING);
    order.setCreateDate(LocalDateTime.now());
    order.setSize(5L);

    Order order2 = new Order();
    order2.setCustomerId(1L);
    order2.setId(2L);
    order2.setAssetName("Test Asset2");
    order2.setPrice(new BigDecimal("3.5"));
    order2.setSide(Side.SELL);
    order2.setStatus(Status.MATCHED);
    order2.setCreateDate(LocalDateTime.now());
    order2.setSize(3L);

    return List.of(order,order2);
  }

  public static OrderListRequest generateOrderListRequest(){
    OrderListRequest orderListRequest = new OrderListRequest();

    orderListRequest.setCustomerId(1L);
    orderListRequest.setStartDateTime(LocalDateTime.now().minusHours(1));
    orderListRequest.setStartDateTime(LocalDateTime.now().plusHours(1));

    return orderListRequest;
  }
}
