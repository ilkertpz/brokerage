package com.ing.brokerage.domain.order.mapper;


import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.order.dto.OrderCreateRequest;
import com.ing.brokerage.domain.order.dto.OrderCreateResponse;
import com.ing.brokerage.domain.order.dto.OrderDTO;
import com.ing.brokerage.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(target = "customerDTO", source = "customerDTO")
  @Mapping(target = "id", source = "order.id")
  OrderCreateResponse toCreateResponse(Order order, CustomerDTO customerDTO);

  @Mapping(target = "status", constant = "PENDING")
  Order toEntity(OrderCreateRequest orderCreateRequest);

  OrderDTO toDTO(Order order);

  List<OrderDTO> toDTO(List<Order> orders);


  @AfterMapping
  default void setCreateDate(@MappingTarget Order order) {
    order.setCreateDate(LocalDateTime.now());
  }
}
