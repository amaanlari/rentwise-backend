package com.rentwise.dto;

import com.rentwise.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecordDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMode;     // ✅ String for API
    private String referenceNumber;
    private Long rentId;
}