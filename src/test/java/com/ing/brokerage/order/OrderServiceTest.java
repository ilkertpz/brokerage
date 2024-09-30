package com.ing.brokerage.order;

import static com.ing.brokerage.generator.AssetDataGenerator.generateAssetDTO;
import static com.ing.brokerage.generator.AssetDataGenerator.generateAssetDTOWithSize;
import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomerDTO;
import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomerDTOWithBalance;
import static com.ing.brokerage.generator.OrderDataGenerator.generateCanceledOrder;
import static com.ing.brokerage.generator.OrderDataGenerator.generateMatchedOrder;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrder;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrderCreateRequestWithSideBuy;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrderCreateRequestWithSideSell;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrderCreateRequestWithoutSide;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrderList;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrderListRequest;
import static com.ing.brokerage.generator.OrderDataGenerator.generateOrderWithMatchedStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.ing.brokerage.domain.asset.dto.AssetDTO;
import com.ing.brokerage.domain.asset.service.IAssetService;
import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.service.ICustomerService;
import com.ing.brokerage.domain.order.constant.Status;
import com.ing.brokerage.domain.order.dto.OrderCancelResponse;
import com.ing.brokerage.domain.order.dto.OrderCreateRequest;
import com.ing.brokerage.domain.order.dto.OrderCreateResponse;
import com.ing.brokerage.domain.order.dto.OrderDTO;
import com.ing.brokerage.domain.order.dto.OrderListRequest;
import com.ing.brokerage.domain.order.dto.OrderMatchResponse;
import com.ing.brokerage.domain.order.entity.Order;
import com.ing.brokerage.domain.order.exception.OrderException;
import com.ing.brokerage.domain.order.mapper.OrderMapperImpl;
import com.ing.brokerage.domain.order.repository.OrderRepository;
import com.ing.brokerage.domain.order.service.OrderService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {


  @Mock
  private OrderRepository orderRepository;

  @Mock
  ICustomerService customerService;

  @Mock
  IAssetService assetService;

  @Mock
  OrderMapperImpl orderMapper;

  @InjectMocks
  OrderService orderService;


  @Test
  void givenOrderCreateRequestAndSideIsBuy_whenCreate_thenReturnOrderCreateResponse() {
    // Arrange

    OrderCreateRequest orderCreateRequest = generateOrderCreateRequestWithSideBuy();
    CustomerDTO customerDTO = generateCustomerDTO();
    Order order = generateOrder();
    BigDecimal exceptedUsableBalance = customerDTO.getUsableBalance().subtract(orderCreateRequest.getPrice().multiply(
        BigDecimal.valueOf(orderCreateRequest.getSize())));


    when(customerService.findByCustomerId(orderCreateRequest.getCustomerId())).thenReturn(customerDTO);
    when(customerService.update(customerDTO)).thenReturn(customerDTO);
    when(orderRepository.save(any())).thenReturn(order);
    when(orderMapper.toEntity(any())).thenCallRealMethod();
    when(orderMapper.toCreateResponse(any(), any())).thenCallRealMethod();


    // Act
    OrderCreateResponse orderCreateResponse = orderService.create(orderCreateRequest);

    // Assert
    assertThat(orderCreateResponse).isNotNull();
    assertThat(orderCreateResponse.getId()).isNotNull();
    assertThat(orderCreateResponse.getCreateDate()).isNotNull();
    assertThat(orderCreateResponse.getAssetName()).isNotNull();
    assertThat(orderCreateResponse.getSize()).isNotNull();
    assertThat(orderCreateResponse.getPrice()).isNotNull();
    assertThat(orderCreateResponse.getStatus()).isNotNull();
    assertThat(orderCreateResponse.getStatus()).isEqualTo(Status.PENDING);
    assertThat(orderCreateResponse.getCustomerDTO()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getId()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getUsableBalance()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getUsableBalance()).isEqualTo(exceptedUsableBalance);
    assertThat(orderCreateResponse.getCustomerDTO().getName()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getSurname()).isNotNull();

    verify(customerService, times(1)).findByCustomerId(orderCreateRequest.getCustomerId());
    verify(orderRepository, times(1)).save(any());
  }

  @Test
  void givenOrderCreateRequestAndSideIsBuy_whenCreate_thenThrowBalanceException() {
    // Arrange

    OrderCreateRequest orderCreateRequest = generateOrderCreateRequestWithSideBuy();
    CustomerDTO customerDTO = generateCustomerDTO();
    customerDTO.setUsableBalance(new BigDecimal("1"));

    when(customerService.findByCustomerId(orderCreateRequest.getCustomerId())).thenReturn(customerDTO);

    // Act - Assert
    assertThatThrownBy(() -> orderService.create(orderCreateRequest)).isInstanceOf(
        OrderException.class).hasMessage("The total price exceeds customer's total balance");

    verify(customerService, times(1)).findByCustomerId(orderCreateRequest.getCustomerId());
  }

  @Test
  void givenOrderCreateRequestAndSideIsSell_whenCreate_thenReturnOrderCreateResponse() {
    // Arrange

    OrderCreateRequest orderCreateRequest = generateOrderCreateRequestWithSideSell();
    CustomerDTO customerDTO = generateCustomerDTO();
    Order order = generateOrder();
    BigDecimal exceptedUsableBalance = customerDTO.getUsableBalance().add(orderCreateRequest.getPrice().multiply(
        BigDecimal.valueOf(orderCreateRequest.getSize())));


    when(customerService.findByCustomerId(orderCreateRequest.getCustomerId())).thenReturn(customerDTO);
    when(customerService.update(customerDTO)).thenReturn(customerDTO);
    when(orderRepository.save(any())).thenReturn(order);
    when(orderMapper.toEntity(any())).thenCallRealMethod();
    when(orderMapper.toCreateResponse(any(), any())).thenCallRealMethod();


    // Act
    OrderCreateResponse orderCreateResponse = orderService.create(orderCreateRequest);

    // Assert
    assertThat(orderCreateResponse).isNotNull();
    assertThat(orderCreateResponse.getId()).isNotNull();
    assertThat(orderCreateResponse.getCreateDate()).isNotNull();
    assertThat(orderCreateResponse.getAssetName()).isNotNull();
    assertThat(orderCreateResponse.getSize()).isNotNull();
    assertThat(orderCreateResponse.getPrice()).isNotNull();
    assertThat(orderCreateResponse.getStatus()).isNotNull();
    assertThat(orderCreateResponse.getStatus()).isEqualTo(Status.PENDING);
    assertThat(orderCreateResponse.getCustomerDTO()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getId()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getUsableBalance()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getUsableBalance()).isEqualTo(exceptedUsableBalance);
    assertThat(orderCreateResponse.getCustomerDTO().getName()).isNotNull();
    assertThat(orderCreateResponse.getCustomerDTO().getSurname()).isNotNull();

    verify(customerService, times(1)).findByCustomerId(orderCreateRequest.getCustomerId());
    verify(orderRepository, times(1)).save(any());
  }

  @Test
  void givenOrderCreateRequestAndSideIsNull_whenCreate_thenThrowUnknownSideException() {
    // Arrange

    OrderCreateRequest orderCreateRequest = generateOrderCreateRequestWithoutSide();
    CustomerDTO customerDTO = generateCustomerDTO();
    customerDTO.setUsableBalance(new BigDecimal("1"));

    when(customerService.findByCustomerId(orderCreateRequest.getCustomerId())).thenReturn(customerDTO);

    // Act - Assert
    assertThatThrownBy(() -> orderService.create(orderCreateRequest)).isInstanceOf(
        OrderException.class).hasMessage("Unknown order-side type");

    verify(customerService, times(1)).findByCustomerId(orderCreateRequest.getCustomerId());
  }


  @Test
  void givenOrderId_whenCancel_thenReturnOrderCancelResponse() {
    // Arrange
    Long orderId = 1L;
    OrderCreateRequest orderCreateRequest = generateOrderCreateRequestWithSideSell();
    CustomerDTO customerDTO = generateCustomerDTO();
    Order order = generateOrder();
    Order canceledOrder = generateCanceledOrder();
    BigDecimal exceptedUsableBalance = customerDTO.getUsableBalance().add(orderCreateRequest.getPrice().multiply(
        BigDecimal.valueOf(orderCreateRequest.getSize())));
    CustomerDTO updatedCustomerDTO = generateCustomerDTOWithBalance(exceptedUsableBalance);

    when(customerService.findByCustomerId(order.getCustomerId())).thenReturn(customerDTO);
    when(customerService.update(customerDTO)).thenReturn(updatedCustomerDTO);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(orderRepository.save(order)).thenReturn(canceledOrder);
    when(orderMapper.toDTO(any(Order.class))).thenCallRealMethod();


    // Act
    OrderCancelResponse orderCancelResponse = orderService.cancel(orderId);

    // Assert
    assertThat(orderCancelResponse).isNotNull();
    assertThat(orderCancelResponse.getOrder()).isNotNull();
    assertThat(orderCancelResponse.getOrder().getCreateDate()).isNotNull();
    assertThat(orderCancelResponse.getOrder().getAssetName()).isNotNull();
    assertThat(orderCancelResponse.getOrder().getSize()).isNotNull();
    assertThat(orderCancelResponse.getOrder().getPrice()).isNotNull();
    assertThat(orderCancelResponse.getOrder().getStatus()).isNotNull();
    assertThat(orderCancelResponse.getOrder().getStatus()).isEqualTo(Status.CANCELED);
    assertThat(orderCancelResponse.getCustomer()).isNotNull();
    assertThat(orderCancelResponse.getCustomer().getId()).isNotNull();
    assertThat(orderCancelResponse.getCustomer().getUsableBalance()).isNotNull();
    assertThat(orderCancelResponse.getCustomer().getUsableBalance()).isEqualTo(exceptedUsableBalance);
    assertThat(orderCancelResponse.getCustomer().getName()).isNotNull();
    assertThat(orderCancelResponse.getCustomer().getSurname()).isNotNull();

    verify(customerService, times(1)).findByCustomerId(orderCreateRequest.getCustomerId());
    verify(customerService, times(1)).update(customerDTO);
    verify(orderRepository, times(1)).save(order);
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderId_whenCancel_thenThrowAlreadyCanceledException() {
    // Arrange
    Long orderId = 1L;
    Order order = generateCanceledOrder();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // Act - Assert
    assertThatThrownBy(() -> orderService.cancel(orderId)).isInstanceOf(
        OrderException.class).hasMessage("The order has already been canceled before");

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderId_whenCancel_thenThrowOrderMatchedException() {
    // Arrange
    Long orderId = 1L;
    Order order = generateMatchedOrder();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // Act - Assert
    assertThatThrownBy(() -> orderService.cancel(orderId)).isInstanceOf(
        OrderException.class).hasMessage("Matched order can not be canceled");

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderIdAndWithSideSell_whenMatch_thenReturnOrderMatchResponse() {
    // Arrange
    Long orderId = 1L;
    Order order = generateOrder();
    AssetDTO assetDTO = generateAssetDTO();
    Order matchedOrder = generateOrderWithMatchedStatus();
    Long expectedAssetNewSize = assetDTO.getSize() - order.getSize();
    AssetDTO expectedAssetDTO = generateAssetDTOWithSize(expectedAssetNewSize);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(orderRepository.save(order)).thenReturn(matchedOrder);
    when(assetService.findByNameAndCustomerId(order.getAssetName(), order.getCustomerId())).thenReturn(
        Optional.of(assetDTO));
    when(assetService.update(any())).thenReturn(expectedAssetDTO);
    when(orderMapper.toDTO(any(Order.class))).thenCallRealMethod();


    // Act
    OrderMatchResponse orderMatchResponse = orderService.match(orderId);

    // Assert
    assertThat(orderMatchResponse).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getId()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getName()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getSize()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getSize()).isEqualTo(expectedAssetNewSize);
    assertThat(orderMatchResponse.getAssetDTO().getCustomerId()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getAssetName()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getSize()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getPrice()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getStatus()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getStatus()).isEqualTo(Status.MATCHED);

    verify(assetService, times(1))
        .findByNameAndCustomerId(order.getAssetName(), order.getCustomerId());
    verify(assetService, times(1)).update(any());
    verify(orderRepository, times(1)).save(order);
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderIdAndWithSideBuy_whenMatch_thenReturnOrderMatchResponse() {
    // Arrange
    Long orderId = 1L;
    Order order = generateOrder();
    AssetDTO assetDTO = generateAssetDTO();
    Order matchedOrder = generateOrderWithMatchedStatus();
    Long expectedAssetNewSize = assetDTO.getSize() - order.getSize();
    AssetDTO expectedAssetDTO = generateAssetDTOWithSize(expectedAssetNewSize);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(orderRepository.save(order)).thenReturn(matchedOrder);
    when(assetService.findByNameAndCustomerId(order.getAssetName(), order.getCustomerId())).thenReturn(
        Optional.of(assetDTO));
    when(assetService.update(any())).thenReturn(expectedAssetDTO);
    when(orderMapper.toDTO(any(Order.class))).thenCallRealMethod();


    // Act
    OrderMatchResponse orderMatchResponse = orderService.match(orderId);

    // Assert
    assertThat(orderMatchResponse).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getId()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getName()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getSize()).isNotNull();
    assertThat(orderMatchResponse.getAssetDTO().getSize()).isEqualTo(expectedAssetNewSize);
    assertThat(orderMatchResponse.getAssetDTO().getCustomerId()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getAssetName()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getSize()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getPrice()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getStatus()).isNotNull();
    assertThat(orderMatchResponse.getOrderDTO().getStatus()).isEqualTo(Status.MATCHED);

    verify(assetService, times(1))
        .findByNameAndCustomerId(order.getAssetName(), order.getCustomerId());
    verify(assetService, times(1)).update(any());
    verify(orderRepository, times(1)).save(order);
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderId_whenMatch_thenThrowAlreadyMatchedException() {
    // Arrange
    Long orderId = 1L;
    Order order = generateMatchedOrder();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // Act -Assert
    assertThatThrownBy(() -> orderService.match(orderId)).isInstanceOf(
        OrderException.class).hasMessage("The order has already been matched before");

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderId_whenMatch_thenThrowAlreadyCanceledException() {
    // Arrange
    Long orderId = 1L;
    Order order = generateCanceledOrder();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // Act -Assert
    assertThatThrownBy(() -> orderService.match(orderId)).isInstanceOf(
        OrderException.class).hasMessage("Canceled order can not be matched");

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void givenOrderId_whenMatch_thenThrowCustomerHasNoAssetException() {
    // Arrange
    Long orderId = 1L;
    Order order = generateOrder();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(assetService.findByNameAndCustomerId(order.getAssetName(), order.getCustomerId()))
        .thenReturn(Optional.empty());

    // Act -Assert
    assertThatThrownBy(() -> orderService.match(orderId)).isInstanceOf(
        OrderException.class).hasMessage("Customer has no asset: Test Asset");

    verify(orderRepository, times(1)).findById(orderId);
    verify(assetService, times(1))
        .findByNameAndCustomerId(order.getAssetName(), order.getCustomerId());
  }

  @Test
  void givenOrderId_whenMatch_thenThrowCustomerAssetSizeNotEnoughException() {
    // Arrange
    Long orderId = 1L;
    Order order = generateOrder();
    AssetDTO assetDTO = generateAssetDTO();
    assetDTO.setSize(3L);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(assetService.findByNameAndCustomerId(order.getAssetName(), order.getCustomerId()))
        .thenReturn(Optional.of(assetDTO));

    // Act -Assert
    assertThatThrownBy(() -> orderService.match(orderId)).isInstanceOf(
        OrderException.class).hasMessage("Customer asset size is not enough for SELL operation");

    verify(orderRepository, times(1)).findById(orderId);
    verify(assetService, times(1))
        .findByNameAndCustomerId(order.getAssetName(), order.getCustomerId());
  }

  @Test
  void givenOrderListRequest_whenGetOrders_thenReturnOrderList() {
    // Arrange
    List<Order> orderList = generateOrderList();
    OrderListRequest orderListRequest = generateOrderListRequest();

    when(orderRepository.findAllByCustomerIdAndCreateDateBetween(orderListRequest.getCustomerId(),
        orderListRequest.getStartDateTime(), orderListRequest.getEndDateTime())).thenReturn(orderList);
    when(orderMapper.toDTO(anyList())).thenCallRealMethod();

    // Act
    List<OrderDTO> orderDTOList = orderService.getOrders(orderListRequest);

    // Assert
    assertThat(orderDTOList).isNotNull();
    assertThat(orderDTOList).isNotEmpty();
    assertThat(orderDTOList.size()).isEqualTo(orderList.size());

    verify(orderRepository, times(1))
        .findAllByCustomerIdAndCreateDateBetween(orderListRequest.getCustomerId(), orderListRequest.getStartDateTime(),
            orderListRequest.getEndDateTime());

  }
}
