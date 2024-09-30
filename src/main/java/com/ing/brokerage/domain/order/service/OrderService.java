package com.ing.brokerage.domain.order.service;


import com.ing.brokerage.domain.asset.dto.AssetDTO;
import com.ing.brokerage.domain.asset.service.IAssetService;
import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.service.ICustomerService;
import com.ing.brokerage.domain.order.constant.Status;
import com.ing.brokerage.domain.order.dto.OrderCreateRequest;
import com.ing.brokerage.domain.order.entity.Order;
import com.ing.brokerage.domain.order.mapper.OrderMapper;
import com.ing.brokerage.domain.order.constant.Side;
import com.ing.brokerage.domain.order.dto.OrderCancelResponse;
import com.ing.brokerage.domain.order.dto.OrderCreateResponse;
import com.ing.brokerage.domain.order.dto.OrderDTO;
import com.ing.brokerage.domain.order.dto.OrderListRequest;
import com.ing.brokerage.domain.order.dto.OrderMatchResponse;
import com.ing.brokerage.domain.order.exception.OrderException;
import com.ing.brokerage.domain.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final ICustomerService customerService;
  private final IAssetService assetService;

  @Override
  @Transactional
  public OrderCreateResponse create(OrderCreateRequest orderCreateRequest) {
    CustomerDTO customerDTO = checkCustomerUsableBalance(orderCreateRequest);
    CustomerDTO updatedCustomerDTO = customerService.update(customerDTO);

    Order order = orderMapper.toEntity(orderCreateRequest);
    Order createdOrder = orderRepository.save(order);

    return orderMapper.toCreateResponse(createdOrder, updatedCustomerDTO);
  }

  @Override
  @Transactional
  public OrderCancelResponse cancel(Long orderId) {
    Order order = checkCancelRules(orderId);

    order.setStatus(Status.CANCELED);
    Order canceledOrder = orderRepository.save(order);

    CustomerDTO customerDTO = updateCustomerUsableBalance(order);

    return OrderCancelResponse.builder()
        .order(orderMapper.toDTO(canceledOrder))
        .customer(customerDTO)
        .build();

  }

  @Override
  @Transactional
  public OrderMatchResponse match(Long orderId) {
    Order order = checkMatchRules(orderId);
    order.setStatus(Status.MATCHED);
    Order updatedOrder = orderRepository.save(order);

    Optional<AssetDTO> assetDTOOpt = assetService.findByNameAndCustomerId(order.getAssetName(), order.getCustomerId());

    AssetDTO assetResult = assetDTOOpt.isPresent() ? updateAsset(assetDTOOpt.get(), order) : createAsset(order);

    OrderMatchResponse response = new OrderMatchResponse();
    response.setAssetDTO(assetResult);
    response.setOrderDTO(orderMapper.toDTO(updatedOrder));
    return response;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderDTO> getOrders(OrderListRequest orderListRequest) {
    List<Order> orderList =
        orderRepository.findAllByCustomerIdAndCreateDateBetween(orderListRequest.getCustomerId(),
            orderListRequest.getStartDateTime(), orderListRequest.getEndDateTime());

    return orderMapper.toDTO(orderList);
  }

  //private methods

  private Order findById(Long orderId) {
    Optional<Order> orderOpt = orderRepository.findById(orderId);
    return orderOpt.orElseThrow(() -> new OrderException("Could not find Order"));
  }

  private CustomerDTO checkCustomerUsableBalance(OrderCreateRequest orderCreateRequest) {
    CustomerDTO customerDTO = customerService.findByCustomerId(orderCreateRequest.getCustomerId());

    BigDecimal newUsableBalance;
    BigDecimal totalPrice = orderCreateRequest.getPrice().multiply(BigDecimal.valueOf(orderCreateRequest.getSize()));
    if (Side.BUY.equals(orderCreateRequest.getSide())) {
      if (totalPrice.compareTo(customerDTO.getUsableBalance()) > 0) {
        throw new OrderException("The total price exceeds customer's total balance");
      }
      newUsableBalance = customerDTO.getUsableBalance().subtract(totalPrice);
    } else if (Side.SELL.equals(orderCreateRequest.getSide())) {
      newUsableBalance = customerDTO.getUsableBalance().add(totalPrice);
    } else {
      throw new OrderException("Unknown order-side type");
    }

    customerDTO.setUsableBalance(newUsableBalance);
    return customerDTO;
  }


  private Order checkCancelRules(Long orderId) {
    Order order = findById(orderId);
    if (Status.CANCELED.equals(order.getStatus())) {
      throw new OrderException("The order has already been canceled before");
    } else if (Status.MATCHED.equals(order.getStatus())) {
      throw new OrderException("Matched order can not be canceled");
    }
    return order;
  }

  private CustomerDTO updateCustomerUsableBalance(Order order) {
    CustomerDTO customerDTO = customerService.findByCustomerId(order.getCustomerId());

    BigDecimal withDrawAmount = order.getPrice().multiply(
        BigDecimal.valueOf(order.getSize()));
    BigDecimal newUsableBalance = customerDTO.getUsableBalance().add(withDrawAmount);
    customerDTO.setUsableBalance(newUsableBalance);
    return customerService.update(customerDTO);
  }

  private Order checkMatchRules(Long orderId) {
    Order order = findById(orderId);
    if (Status.MATCHED.equals(order.getStatus())) {
      throw new OrderException("The order has already been matched before");
    } else if (Status.CANCELED.equals(order.getStatus())) {
      throw new OrderException("Canceled order can not be matched");
    }
    return order;
  }

  private AssetDTO updateAsset(AssetDTO assetDTO, Order order) {
    if (Side.BUY.equals(order.getSide())) {
      assetDTO.setSize(assetDTO.getSize() + order.getSize());
    } else if (Side.SELL.equals(order.getSide())) {
      if(order.getSize() > assetDTO.getSize()) {
        throw new OrderException("Customer asset size is not enough for SELL operation");
      }
      assetDTO.setSize(assetDTO.getSize() - order.getSize());
    } else {
      throw new OrderException("Unknown order-side " + order.getSide());
    }
    return assetService.update(assetDTO);
  }

  private AssetDTO createAsset(Order order) {
    if (Side.SELL.equals(order.getSide())) {
      throw new OrderException("Customer has no asset: " + order.getAssetName());
    }
    AssetDTO assetDTO = new AssetDTO();
    assetDTO.setSize(order.getSize());
    assetDTO.setCustomerId(order.getCustomerId());
    assetDTO.setName(order.getAssetName());
    return assetService.create(assetDTO);
  }

}
