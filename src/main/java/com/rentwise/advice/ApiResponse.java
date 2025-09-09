package com.rentwise.advice;

import com.rentwise.model.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.StringJoiner;

@Setter
@Getter
public class ApiResponse<T> {

  private LocalDateTime timestamp;
  private T data;
  private ApiError error;
  private ResponseStatus responseStatus;

  public ApiResponse() {
    this.timestamp = LocalDateTime.now();
  }

  public ApiResponse(T data) {
    this();
    this.data = data;
    this.responseStatus = ResponseStatus.SUCCESS;
  }

  public ApiResponse(ApiError error) {
    this();
    this.error = error;
    this.responseStatus = ResponseStatus.FAILURE;
  }

    @Override
  public String toString() {
    return new StringJoiner(", ", ApiResponse.class.getSimpleName() + "[", "]")
        .add("timestamp=" + timestamp)
        .add("data=" + data)
        .add("error=" + error)
        .toString();
  }
}
