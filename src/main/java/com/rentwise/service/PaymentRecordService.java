package com.rentwise.service;

import com.rentwise.dto.PaymentRecordDto;

import java.util.List;

public interface PaymentRecordService {
    PaymentRecordDto createPayment(PaymentRecordDto dto);
    List<PaymentRecordDto> getPaymentsByRent(Long rentId);
}
