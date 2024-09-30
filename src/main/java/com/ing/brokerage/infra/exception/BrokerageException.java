package com.ing.brokerage.infra.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrokerageException extends RuntimeException {

  private String errorMessage;
}
