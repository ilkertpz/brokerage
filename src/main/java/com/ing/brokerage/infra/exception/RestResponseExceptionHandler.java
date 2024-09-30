package com.ing.brokerage.infra.exception;

import com.ing.brokerage.domain.asset.exception.AssetException;
import com.ing.brokerage.domain.customer.exception.CustomerException;
import com.ing.brokerage.domain.order.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AssetException.class)
  public ResponseEntity<BrokerageException> handleAssetException(AssetException ex, WebRequest request) {
    BrokerageException brokerageException = BrokerageException.builder()
        .errorMessage(ex.getMessage()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(new HttpHeaders()).body(brokerageException);
  }

  @ExceptionHandler(OrderException.class)
  public ResponseEntity<BrokerageException> handleOrderException(OrderException ex, WebRequest request) {
    BrokerageException brokerageException = BrokerageException.builder()
        .errorMessage(ex.getMessage()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(new HttpHeaders()).body(brokerageException);
  }

  @ExceptionHandler(CustomerException.class)
  public ResponseEntity<BrokerageException> handleCustomerException(CustomerException ex, WebRequest request) {
    BrokerageException brokerageException = BrokerageException.builder()
        .errorMessage(ex.getMessage()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(new HttpHeaders()).body(brokerageException);
  }

}
