package com.ing.brokerage.domain.order.repository;

import com.ing.brokerage.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllByCustomerIdAndCreateDateBetween(Long customerId, LocalDateTime createDateStart,
                                                      LocalDateTime createDateEnd);

}
